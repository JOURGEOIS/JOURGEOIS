import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import { User } from "../../interface";

export interface FollowState {
  // * 해당 유저들을 팔로우하는 유저리스트
  followerUsers: User[];
  followerUserPage: number;
  // * 팔로우 누른 유저리스트
  followeeUsers: User[];
  followeeUserPage: number;
}

export const follow: Module<FollowState, RootState> = {
  namespaced: true,

  state: {
    // * 해당 유저들을 팔로우하는 유저리스트
    followerUsers: [],
    followerUserPage: 0,
    // * 팔로우 누른 유저리스트
    followeeUsers: [],
    followeeUserPage: 0,
  },

  getters: {
    // * 해당 유저들을 팔로우하는 유저리스트
    getFollowerUsers: (state) => state.followerUsers,
    getFollowerUserPage: (state) => state.followerUserPage,
    // * 팔로우 누른 유저리스트
    getFolloweeUsers: (state) => state.followeeUsers,
    getFolloweeUserPage: (state) => state.followeeUserPage,
  },

  mutations: {
    SET_FOLLOWER_USERS: (state, newFollowerUsers: User[]) => {
      newFollowerUsers.forEach((newFollowerUsers) => {
        state.followerUsers.push(newFollowerUsers);
      });
    },
    SET_FOLLOWER_USER_PAGE: (state, value) => {
      state.followerUserPage = value;
    },
    SET_FOLLOWEE_USERS: (state, newFolloweeUsers: User[]) => {
      newFolloweeUsers.forEach((newFolloweeUsers) => {
        state.followeeUsers.push(newFolloweeUsers);
      });
    },
    SET_FOLLOWEE_USER_PAGE: (state, value) => {
      state.followeeUserPage = value;
    },
    RESET_FOLLOW_USER_DATA: (state) => {
      state.followerUsers = [];
      state.followerUserPage = 0;
      state.followeeUsers = [];
      state.followeeUserPage = 0;
    },
  },

  actions: {
    resetFollowUserData: ({ commit }) => {
      commit("RESET_FOLLOW_USER_DATA");
    },

    // * 팔로우하기
    follow: ({ dispatch, rootGetters }, data: { uid: number }) => {
      axios({
        url: api.accounts.follow(),
        method: "POST",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          to: data.uid,
        },
      })
        .then((res) => {
          if (res.data.success) {
          } else {
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          }
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          } else {
            // refreshToken 재발급
            const obj = {
              func: "follow/follow",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // * 언팔로우하기
    unfollow: ({ dispatch, rootGetters }, data) => {
      axios({
        url: api.accounts.follow(),
        method: "DELETE",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          to: data.uid,
        },
      })
        .then((res) => {
          if (res.data.success) {
          } else {
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          }
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          } else {
            // refreshToken 재발급
            const obj = {
              func: "follow/unfollow",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 팔로워 목록
    setFollowerList: ({ commit, dispatch, rootGetters, getters }, data) => {
      axios({
        url: api.accounts.profileFollower(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          uid: data.userId,
          page: getters.getFollowerUserPage,
        },
      })
        .then((res) => {
          commit("SET_FOLLOWER_USERS", res.data);
          const page = getters.getFollowerUserPage;
          commit("SET_FOLLOWER_USER_PAGE", page + 1);
        })
        .catch((err) => {
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/setFollowerList",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 팔로잉 목록
    setFolloweeList: ({ commit, dispatch, rootGetters, getters }, data) => {
      axios({
        url: api.accounts.profileFollowee(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          uid: data.userId,
          page: getters.getFolloweeUserPage,
        },
      })
        .then((res) => {
          commit("SET_FOLLOWEE_USERS", res.data);
          const page = getters.getFollowerUserPage;
          commit("SET_FOLLOWEE_USER_PAGE", page + 1);
        })
        .catch((err) => {
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/setFolloweeList",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
