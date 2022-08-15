import { Module } from "vuex";
import { RootState } from "../index";
// import axios from "axios";
// import api from "../../api/api";

export interface ModalState {
  failModalAppStatus: boolean;
  errorModalAppStatus: boolean;
  errorModalAppMessage: string;
  loadingStatus: boolean;
}

export const modal: Module<ModalState, RootState> = {
  namespaced: true,

  state: {
    failModalAppStatus: false,
    errorModalAppStatus: false,
    errorModalAppMessage: "",
    loadingStatus: false,
  },

  getters: {
    getFailModalAppStatus: (state) => {
      return state.failModalAppStatus;
    },
    getErrorModalAppStatus: (state) => {
      return state.errorModalAppStatus;
    },
    getErrorModalAppMessage: (state) => {
      return state.errorModalAppMessage;
    },
    getLoadingStatus: (state) => {
      return state.loadingStatus;
    },
  },

  mutations: {
    SET_FAIL_MODAL_APP: (state, value: boolean) => {
      state.failModalAppStatus = value;
    },
    SET_ERROR_MODAL_APP: (state, value: boolean) => {
      state.errorModalAppStatus = value;
    },
    SET_ERROR_MODAL_MESSAGE: (state, value: string) => {
      state.errorModalAppMessage = value;
    },
    SET_LOADING_STATUS: (state, value: boolean) => {
      state.loadingStatus = value;
    },
  },

  actions: {
    toggleFailModalAppStatus: ({ commit }, value: boolean) => {
      commit("SET_FAIL_MODAL_APP", value);
    },
    toggleLoadingStatus: ({ commit }, value: boolean) => {
      commit("SET_LOADING_STATUS", value);
    },
    blinkFailModalAppStatus: ({ commit }) => {
      commit("SET_FAIL_MODAL_APP", true);
      setTimeout(() => {
        commit("SET_FAIL_MODAL_APP", false);
      }, 2000);
    },
    toggleErrorModalAppStatus: ({ commit }, value: boolean) => {
      commit("SET_ERROR_MODAL_APP", value);
    },
    changeErrorModalMessage: ({ commit }, value: string) => {
      commit("SET_ERROR_MODAL_MESSAGE", value);
    },
    blinkErrorModalAppStatus: ({ commit }) => {
      commit("SET_ERROR_MODAL_APP", true);
      setTimeout(() => {
        commit("SET_ERROR_MODAL_APP", false);
      }, 2000);
    },
  },
};
