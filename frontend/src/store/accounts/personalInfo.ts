import { Module } from "vuex";
import { RootState } from "../index";

export interface PersonalInfo {
  accessToken: string;
  refreshToken: string;
}

export const personalInfo: Module<PersonalInfo, RootState> = {
  namespaced: true,

  state: {
    accessToken: localStorage.getItem("accessToken") || "",
    refreshToken: localStorage.getItem("refreshToken") || "",
  },

  getters: {
    getAccessToken: (state) => {
      return state.accessToken;
    },
    refreshToken: (state) => {
      return state.refreshToken;
    },
  },

  mutations: {
    SET_ACCESS_TOKEN: (state, token) => {
      state.accessToken = token;
    },
    SET_REFRESH_TOKEN: (state, token) => {
      state.refreshToken = token;
    },
  },

  actions: {},
};
