import { Module } from "vuex";
import { RootState } from "../index";

export interface userInfo {
  [props: string]: string;
}

export interface PersonalInfoState {
  accessToken: string;
  refreshToken: string;
  userInfo: userInfo;
}

export const personalInfo: Module<PersonalInfoState, RootState> = {
  namespaced: true,

  state: {
    accessToken: localStorage.getItem("accessToken") || "",
    refreshToken: localStorage.getItem("refreshToken") || "",
    userInfo: JSON.parse(localStorage.getItem("userInfo") || "{}") || {},
  },

  getters: {
    getAccessToken: (state) => {
      return state.accessToken;
    },
    getRefreshToken: (state) => {
      return state.refreshToken;
    },
    getUserInfo: (state) => {
      return state.userInfo;
    },
    getUserInfoId: (state) => {
      return state.userInfo.email;
    },
  },

  mutations: {
    SET_ACCESS_TOKEN: (state, token) => {
      state.accessToken = token;
    },
    SET_REFRESH_TOKEN: (state, token) => {
      state.refreshToken = token;
    },
    SET_USER_INFO: (state, data) => {
      state.userInfo = data;
    },
  },

  actions: {
    saveToken: ({ commit }, { accessToken, refreshToken }) => {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      commit("SET_ACCESS_TOKEN", accessToken);
      commit("SET_REFRESH_TOKEN", refreshToken);
    },
    removeToken: ({ commit }) => {
      localStorage.setItem("accessToken", "");
      localStorage.setItem("refreshToken", "");
      commit("SET_ACCESS_TOKEN", "");
      commit("SET_REFRESH_TOKEN", "");
    },
    saveUserInfo: ({ commit }, data) => {
      const jsonUserInfo = JSON.stringify(data);
      localStorage.setItem("userInfo", jsonUserInfo);
      commit("SET_USER_INFO", data);
    },
    removeUserInfo: ({ commit }) => {
      localStorage.setItem("userInfo", "");
      commit("SET_USER_INFO", {});
    },
    resetUserInfo: ({ commit, dispatch }) => {
      dispatch("removeToken");
      dispatch("removeUserInfo");
    },
  },
};
