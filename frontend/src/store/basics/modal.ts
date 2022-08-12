import { Module } from "vuex";
import { RootState } from "../index";
// import axios from "axios";
// import api from "../../api/api";

export interface ModalState {
  failModalAppStatus: boolean;
  errorModalAppStatus: boolean;
  errorModalAppMessage: string;
}

export const modal: Module<ModalState, RootState> = {
  namespaced: true,

  state: {
    failModalAppStatus: false,
    errorModalAppStatus: false,
    errorModalAppMessage: "",
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
  },

  actions: {
    toggleFailModalAppStatus: ({ commit }, value: boolean) => {
      commit("SET_FAIL_MODAL_APP", value);
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
