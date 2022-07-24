import { Module } from "vuex";
import { RootState } from "../index";
import { clickHome } from "../../modules/clickEvent";
import drf from "../../api/drf";
import axios from "axios";

// Signup의 State 타입을 설정한다.
export interface AccountState {
  logOutModalStatus: boolean;
  logOutPopupStatus: boolean;
  loginErrorStatus: boolean;
}

export const account: Module<AccountState, RootState> = {
  namespaced: true,

  state: {
    logOutModalStatus: false,
    logOutPopupStatus: false,
    loginErrorStatus: false,
  },

  getters: {
    getLogOutModalStatus: (state) => {
      return state.logOutModalStatus;
    },
    getLogOutPopupStatus: (state) => {
      return state.logOutPopupStatus;
    },
    getLoginErrorStatus: (state) => {
      return state.loginErrorStatus;
    },
  },

  mutations: {
    SET_LOGOUT_MODAL: (state, value) => {
      state.logOutModalStatus = value;
    },
    SET_LOGOUT_POPUP: (state, value) => {
      state.logOutPopupStatus = value;
    },
    SET_LOGIN_ERROR_MSG: (state, value) => {
      state.loginErrorStatus = value;
    },
  },

  actions: {
    toggleLogOutModal: ({ commit }, value) => {
      commit("SET_LOGOUT_MODAL", value);
    },
    toggleLogOutPopup: ({ commit }, value) => {
      commit("SET_LOGOUT_POPUP", value);
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
          commit("SET_LOGIN_ERROR_MSG", true);
        });
    },
    logout: ({ commit, dispatch, rootGetters }) => {
      console.log(rootGetters["personalInfo/getUserInfoId"]);
      axios({
        url: drf.accounts.logout(),
        method: "post",
        data: {
          id: rootGetters["personalInfo/getUserInfoId"],
        },
      })
        .then((response) => {
          console.log(response.data);
          // 성공시 로그아웃 모달 off => 홈으로 이동 =>> 로그아웃 성공 팝업
          commit("SET_LOGOUT_MODAL", false);
          clickHome();
          commit("SET_LOGOUT_POPUP", true);
        })
        .catch((error) => {
          console.log(error.response);
        });
    },
  },
};
