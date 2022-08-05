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
    setLikedUser: ({ commit, getters, rootGetters }) => {
      const email = rootGetters["personalInfo/getUserInfoId"];
      axios({
        url: api.post.likedUsers(),
        method: "GET",
        headers: {
          email,
        },
        params: {
          page: getters["getLikedUserPage"],
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
  },
};
