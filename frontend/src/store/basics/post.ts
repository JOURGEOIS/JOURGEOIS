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
  },

  actions: {
    // * 좋아요 누른 유저리스트 추가
    setLikedUsers: ({ commit, getters, rootGetters }, data) => {
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
        .catch((err) => console.error(err));
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
          // uid: 41003,
        },
        data: { postId: params.postId, uid: 41003 },
      })
        .then((res) => {
          dispatch(params.func, params.data, { root: true });
        })
        .catch((err) => {
          console.error(err.response);
        });
    },
  },
};
