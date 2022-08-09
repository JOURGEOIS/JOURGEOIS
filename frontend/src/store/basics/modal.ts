import { Module } from "vuex";
import { RootState } from "../index";
// import axios from "axios";
// import api from "../../api/api";

export interface ModalState {
  failModalAppStatus: boolean;
}

export const modal: Module<ModalState, RootState> = {
  namespaced: true,

  state: {
    failModalAppStatus: false,
  },

  getters: {
    getFailModalAppStatus: (state) => {
      return state.failModalAppStatus;
    },
  },

  mutations: {
    SET_FAIL_MODAL_APP: (state, value: boolean) => {
      state.failModalAppStatus = value;
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
  },
};
