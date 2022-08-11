import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";

export interface FollowState {}

export const follow: Module<FollowState, RootState> = {
  namespaced: true,

  state: {},

  getters: {},

  mutations: {},

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
  },
};
