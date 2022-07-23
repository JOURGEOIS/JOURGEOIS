import { Module } from "vuex";
import { RootState } from "../index";
import drf from "../../api/drf";
import axios from "axios";
import { clickHome } from "../../modules/clickEvent";

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
    login: ({ commit, dispatch }, { email, password }) => {
      axios({
        url: drf.accounts.login(),
        method: "post",
        data: {
          email,
          password,
        },
      })
        .then((response) => {
          const { userInfo, token } = response.data;
          const { accessToken, refreshToken } = token;
          dispatch("personalInfo/saveUserInfo", userInfo, { root: true });
          dispatch(
            "personalInfo/saveToken",
            { accessToken, refreshToken },
            { root: true }
          );
          clickHome();
        })
        .catch((error) => {
          console.log(error);
          commit("SET_LOGIN_ERROR_MSG", true);
        });
    },
    logout: ({ commit, dispatch, rootGetters }) => {
      axios({
        url: drf.accounts.signOut(),
        method: "post",
        data: {
          id: rootGetters["personalInfo/userInfoId"],
        },
      })
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
