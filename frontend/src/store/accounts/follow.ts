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
        state.followerUsers.push(newFollowerUsers)
      })
    },
    SET_FOLLOWER_USER_PAGE: (state, value) => {
      state.followerUserPage = value
    },
    SET_FOLLOWEE_USERS: (state, newFolloweeUsers: User[]) => {
      newFolloweeUsers.forEach((newFolloweeUsers) => {
        state.followeeUsers.push(newFolloweeUsers)
      })
    },
    SET_FOLLOWEE_USER_PAGE: (state, value) => {
      state.followeeUserPage = value
    },
    RESET_LIKED_USER_DATA: (state) => {
      state.followerUsers = [];
      state.followerUserPage = 0;
      state.followeeUsers = [];
      state.followeeUserPage = 0;
    },
  },

  actions: {
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
    followerList: ({ commit }, currentUserId) => {
      axios({})
    },

    // 팔로잉 목록
    followeeList: ({ commit }, currentUserId) => {
      axios({})
    }
  },
};
