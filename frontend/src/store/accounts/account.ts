import { Module } from "vuex";
import { RootState } from "../index";
import { clickHome } from "../../functions/clickEvent";
import drf from "../../api/drf";
import axios from "axios";

// Signup의 State 타입을 설정한다.
export interface AccountState {
  logOutModalStatus: boolean;
  logOutPopupStatus: boolean;
  failModalStatus: boolean;
  loginErrorStatus: boolean;
  signOutPopupStatus: boolean;
  signOutCurrentTab: number;
  userInfoChangeError: boolean;
  userInfoChangeSuccess: boolean;
}

export const account: Module<AccountState, RootState> = {
  namespaced: true,

  state: {
    logOutModalStatus: false,
    logOutPopupStatus: false,
    failModalStatus: false,
    loginErrorStatus: false,
    signOutPopupStatus: false,
    signOutCurrentTab: 0,
    userInfoChangeError: false,
    userInfoChangeSuccess: false,
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
    getSignOutPopupStatus: (state) => {
      return state.signOutPopupStatus;
    },
    getSignOutCurrentTab: (state) => {
      return state.signOutCurrentTab;
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
    SET_SIGN_OUT_POPUP: (state, value) => {
      state.signOutPopupStatus = value;
    },
    SET_SIGN_OUT_TAB: (state, value) => {
      state.signOutCurrentTab = value;
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
    toggleSignOutPopup: ({ commit }, value) => {
      commit("SET_SIGN_OUT_POPUP", value);
    },
    changeSignOutCurrentTab: ({ commit }, value) => {
      commit("SET_SIGN_OUT_TAB", value);
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
          console.log(response.data);
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
      axios({
        url: drf.accounts.logout(),
        method: "get",
        params: {
          email: rootGetters["personalInfo/getUserInfoId"],
        },
      })
        .then(() => {
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

    // 회원 탈퇴: 본인 인증
    submitSignOutAuth: ({ rootGetters, commit, dispatch }, params) => {
      console.log("몇번 시도?");
      const { pwInputValue, failStatus } = params;
      axios({
        url: drf.accounts.changePassword(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          email: rootGetters["personalInfo/getUserInfoId"],
          passwordOld: pwInputValue.value,
        },
      })
        .then(() => {
          commit("SET_SIGN_OUT_TAB", 1);
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            failStatus.value = true;
          } else {
            // refreshToken 재발급
            const obj = {
              func: "account/submitSignOutAuth",
              params,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 회원 탈퇴: 탈퇴
    signOut: ({ commit, dispatch, rootGetters }, failStatus) => {
      console.log("몇번 시도?");
      axios({
        url: drf.accounts.signOut(),
        method: "delete",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          email: rootGetters["personalInfo/getUserInfoId"],
        },
      })
        // 성공시, 로컬스토리지 정보 삭제후 홈으로 이동후 팝업을 보여준다.
        .then(() => {
          dispatch("personalInfo/resetUserInfo", {}, { root: true });
          clickHome();
          commit("SET_SIGN_OUT_POPUP", true);
        })
        // 실패 팝업
        .catch((error) => {
          if (error.response.status !== 401) {
            failStatus.value = true;
          } else {
            // refreshToken 재발급
            const obj = {
              func: "account/submitPwForm",
              params: failStatus,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
