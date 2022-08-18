import { Module } from "vuex";
import { RootState } from "../index";
// import axios from "axios";
// import api from "../../api/api";

export interface ModalState {
  failModalAppStatus: boolean;
  errorModalAppStatus: boolean;
  errorModalAppMessage: string;
  loadingStatus: boolean;
  successModalAppStatus: boolean;
  successModalAppMessage: string;
}

export const modal: Module<ModalState, RootState> = {
  namespaced: true,

  state: {
    failModalAppStatus: false,
    errorModalAppStatus: false,
    errorModalAppMessage: "",
    loadingStatus: false,
    successModalAppStatus: false,
    successModalAppMessage: "",
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
    getSuccessModalAppStatus: (state) => {
      return state.successModalAppStatus;
    },
    getSuccessModalAppMessage: (state) => {
      return state.successModalAppMessage;
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
    SET_SUCCESS_MODAL_APP: (state, value: boolean) => {
      state.successModalAppStatus = value;
    },
    SET_SUCCESS_MODAL_MESSAGE: (state, value: string) => {
      state.successModalAppMessage = value;
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
    toggleSuccessModalAppStatus: ({ commit }, value: boolean) => {
      commit("SET_SUCCESS_MODAL_APP", value);
    },
    blinkSuccessModalAppStatus: ({ commit }) => {
      commit("SET_SUCCESS_MODAL_APP", true);
      setTimeout(() => {
        commit("SET_SUCCESS_MODAL_APP", false);
      }, 2000);
    },
    changeSuccessModalMessage: ({ commit }, value: string) => {
      commit("SET_SUCCESS_MODAL_MESSAGE", value);
    },
  },
};
