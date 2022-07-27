import axios from "axios";
import { Module } from "vuex";
import drf from "../../api/drf";
import { clickHome } from "../../modules/clickEvent";
import { RootState } from "../index";

export interface userInfo {
  [props: string]: string;
}

export interface PersonalInfoState {
  accessToken: string;
  refreshToken: string;
  userInfo: userInfo;
  refreshFailPopupStatus: boolean;
}

export const personalInfo: Module<PersonalInfoState, RootState> = {
  namespaced: true,

  state: {
    accessToken: localStorage.getItem("accessToken") || "",
    refreshToken: localStorage.getItem("refreshToken") || "",
    userInfo: JSON.parse(localStorage.getItem("userInfo") || "{}") || {},
    refreshFailPopupStatus: false,
  },

  getters: {
    isLoggedIn: (state) => {
      return !!state.accessToken;
    },
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
    getRefreshFailPopupStatus: (state) => {
      return state.refreshFailPopupStatus;
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
    SET_REFRESH_FAIL: (state, value) => {
      state.refreshFailPopupStatus = value;
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
    saveUserInfo: ({ commit }, data: object) => {
      const jsonUserInfo = JSON.stringify(data);
      localStorage.setItem("userInfo", jsonUserInfo);
      commit("SET_USER_INFO", data);
    },
    removeUserInfo: ({ commit }) => {
      localStorage.setItem("userInfo", "");
      commit("SET_USER_INFO", {});
    },
    resetUserInfo: ({ dispatch }) => {
      dispatch("removeToken");
      dispatch("removeUserInfo");
    },
    toggleRefreshFailPopup: ({ commit }, value) => {
      commit("SET_REFRESH_FAIL", value);
    },
    requestRefreshToken: ({ getters, dispatch, commit }, obj) => {
      const { func, params } = obj;
      console.log(`보내는 정보 =>  ${func} ,  ${params}`);
      axios({
        url: drf.token.token(),
        method: "post",
        data: {
          token: getters.getRefreshToken,
        },
      })
        .then((response) => {
          const data = response.data;
          console.log(`토큰 정보 ${data}`);
          const token = {
            accessToken: data.accessToken,
            refreshToken: data.refreshToken,
          };
          dispatch("saveToken", token);
          dispatch(func, params, { root: true });
        })
        .catch(() => {
          dispatch("resetUserInfo");
          clickHome();
          commit("SET_REFRESH_FAIL", true);
        });
    },
  },
};
