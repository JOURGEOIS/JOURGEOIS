import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import { User } from "../../interface";

export interface PostState {
  // * 좋아요 누른 유저리스트
  likedUsers: User[];
  likedUserPage: number;
}

export const post: Module<PostState, RootState> = {
  namespaced: true,

  state: {
    // * 좋아요 누른 유저리스트
    likedUsers: [],
    likedUserPage: 0,
  },

  getters: {
    // * 좋아요 누른 유저리스트
    getLikedUsers: (state) => state.likedUsers,
    getLikedUserPage: (state) => state.likedUserPage,
  },

  mutations: {
    // * 좋아요 누른 유저리스트
    SET_LIKED_USERS: (state, newLikedUsers: User[]) => {
      newLikedUsers.forEach((newLikedUser) => {
        state.likedUsers.push(newLikedUser);
      });
    },
    SET_LIKED_USER_PAGE: (state, value) => {
      state.likedUserPage = value;
    },
    RESET_LIKED_USER_DATA: (state) => {
      state.likedUsers = [];
      state.likedUserPage = 0;
    },
  },

  actions: {
    // 리셋
    resetLikedUserData: ({ commit }) => {
      commit("RESET_LIKED_USER_DATA");
    },

    // * 좋아요 누른 유저리스트 추가
    setLikedUsers: ({ commit, dispatch, getters, rootGetters }, data) => {
      axios({
        url: api.post.likedUsers(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          page: getters["getLikedUserPage"],
          postId: data.postId,
        },
      })
        .then((res) => {
          const newLikedUsers = res.data;
          commit("SET_LIKED_USERS", newLikedUsers);
          const page = getters.getLikedUserPage;
          commit("SET_LIKED_USER_PAGE", page + 1);
        })
        .catch((err) => {
          // 실패 시, 홈으로 이동 후 에러 모달 on
          if (err.response.status !== 401) {
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
            console.error(err.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "post/setLikedUsers",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // * 좋아요 / 좋아요 취소
    // params에는 postId(feedId), 좋아요 후 새로 불러오는 set actions
    // data에는 set actions에 인자 객체를 넣는다.
    toggleLike: ({ rootGetters, dispatch }, params) => {
      axios({
        url: api.post.toggleLike(),
        method: "POST",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: { postId: params.postId, uid: 41003 },
      })
        .then((res) => {
          dispatch(params.func, params.data, { root: true });
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
            console.error(error.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "post/toggleLike",
              params,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
