import { Module } from "vuex";
import { RootState } from "../index";
import { clickHome } from "../../modules/clickEvent";
import drf from "../../api/drf";
import axios from "axios";

// Signup의 State 타입을 설정한다.
export interface AccountState {
  logOutModalStatus: boolean;
  logOutPopupStatus: boolean;
  failModalStatus: boolean;
  loginErrorStatus: boolean;
}

export const account: Module<AccountState, RootState> = {
  namespaced: true,

  state: {
    logOutModalStatus: false,
    logOutPopupStatus: false,
    failModalStatus: false,
    loginErrorStatus: false,
  },

  getters: {
    getLogOutModalStatus: (state) => {
      return state.logOutModalStatus;
    },
    getLogOutPopupStatus: (state) => {
      return state.logOutPopupStatus;
    },
    getFailModalStatus: (state) => {
      return state.failModalStatus;
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
    SET_FAIL_MODAL: (state, value) => {
      state.failModalStatus = value;
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

    toggleFailModalStatus: ({ commit }, value) => {
      commit("SET_FAIL_MODAL", value);
    },

    toggleLoginError: ({ commit }, value) => {
      commit("SET_LOGIN_ERROR_MSG", value);
    },

    // 로그인
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

    // 로그아웃
    logout: ({ commit, dispatch, rootGetters }) => {
      console.log(rootGetters["personalInfo/getUserInfoId"]);
      axios({
        url: drf.accounts.logout(),
        method: "get",
        params: {
          email: rootGetters["personalInfo/getUserInfoId"],
        },
      })
        .then((response) => {
          // 성공시 1. 로그아웃 모달 off 2. 정보 지우기 3. 홈으로 이동 4.로그아웃 성공 팝업
          commit("SET_LOGOUT_MODAL", false);
          dispatch("personalInfo/resetUserInfo", {}, { root: true });
          clickHome();
          commit("SET_LOGOUT_POPUP", true);
        })
        .catch((error) => {
          // 실패 시, 홈으로 이동 후 에러 모달 on
          clickHome();
          commit("SET_FAIL_MODAL", true);
        });
    },
  },
};
