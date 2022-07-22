import { Module } from "vuex";
import { RootState } from "../index";

// Signup의 State 타입을 설정한다.
export interface AccountState {
  logOutModalStatus: boolean;
}

export const account: Module<AccountState, RootState> = {
  namespaced: true,

  state: {
    logOutModalStatus: true,
  },

  getters: {
    getLogOutModalStatus: (state) => {
      return state.logOutModalStatus;
    },
  },

  mutations: {
    SET_LOGOUT_MODAL: (state, value) => {
      state.logOutModalStatus = value;
    },
  },

  actions: {
    toggleLogOutModal: ({ commit }, value) => {
      commit("SET_LOGOUT_MODAL", value);
    },
  },
};
