import { Module } from "vuex";
import { RootState } from "../index";
import drf from "../../api/drf";
import axios from "axios";

// Signup의 State 타입을 설정한다.
export interface AccountState {
  logOutModalStatus: boolean;
  loginErrorStatus: boolean;
}

export const account: Module<AccountState, RootState> = {
  namespaced: true,

  state: {
    logOutModalStatus: false,
    loginErrorStatus: false,
  },

  getters: {
    getLogOutModalStatus: (state) => {
      return state.logOutModalStatus;
    },
    getLoginErrorStatus: (state) => {
      return state.loginErrorStatus;
    },
  },

  mutations: {
    SET_LOGOUT_MODAL: (state, value) => {
      state.logOutModalStatus = value;
    },
    SET_LOGIN_ERROR_MSG: (state, value) => {
      state.loginErrorStatus = value;
    },
  },

  actions: {
    toggleLogOutModal: ({ commit }, value) => {
      commit("SET_LOGOUT_MODAL", value);
    },

    toggleLoginError: ({ commit }, value) => {
      commit("SET_LOGIN_ERROR_MSG", value);
    },
    login: ({ commit }, { email, password }) => {
      console.log(email, password);
      console.log(drf.accounts.login());
      axios
        .post(
          drf.accounts.login(),
          {},
          {
            params: {
              email: email,
              password: encodeURIComponent(password),
            },
          }
        )
        .then((response) => {
          console.log(response.data);
          console.log("성공");
        })
        .catch((error) => {
          console.log(error);
          console.log("실패");
          commit("SET_LOGIN_ERROR_MSG", true);
        });
    },
  },
};
