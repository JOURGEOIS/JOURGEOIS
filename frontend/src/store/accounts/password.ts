import { toRefs } from "vue";
import { Module } from "vuex";
import { RootState } from "../index";
import api from "../../api/api";
import axios from "axios";
import { clickHome, clickLogin } from "../../functions/clickEvent";

// Signup의 State 타입을 설정한다.
export interface PasswordState {
  forgotPwCurrentTab: number;
  forgotPwEmail: string;
  forgotPwErrorMsgStatus: boolean;
  changePwCurrentTab: number;
  changePwPopupStatus: boolean;
}

export const password: Module<PasswordState, RootState> = {
  namespaced: true,

  state: {
    // 비밀번호 찾기 컴포넌트 index
    forgotPwCurrentTab: 0,

    // 비밀번호 찾기에서 사용되는 이메일
    forgotPwEmail: "",

    // 비밀번호 찾기 에러 메시지 status
    forgotPwErrorMsgStatus: false,

    // 비밀번호 변경 컴포넌트 index
    changePwCurrentTab: 0,

    // 비밀번호 변경 성공 팝업
    changePwPopupStatus: false,
  },

  getters: {
    getForgotPwCurrentTab: (state) => {
      return state.forgotPwCurrentTab;
    },
    getForgotPwEmail: (state) => {
      return state.forgotPwEmail;
    },
    getForgotPwErrorMsgStatus: (state) => {
      return state.forgotPwErrorMsgStatus;
    },
    getChangePwCurrentTab: (state) => {
      return state.changePwCurrentTab;
    },
    getChangePwPopupStatus: (state) => {
      return state.changePwPopupStatus;
    },
  },

  mutations: {
    SET_FORGOT_PW_CURRENT_TAB: (state, value: number) => {
      state.forgotPwCurrentTab = value;
    },
    SET_FORGOT_PW_EMAIL: (state, value: string) => {
      state.forgotPwEmail = value;
    },
    SET_FORGOT_PW_ERROR_MSG: (state, value: boolean) => {
      state.forgotPwErrorMsgStatus = value;
    },
    SET_CHANGE_PW_CURRENT_TAB: (state, value: number) => {
      state.changePwCurrentTab = value;
    },
    SET_CHANGE_PW_POPUP: (state, value: boolean) => {
      state.changePwPopupStatus = value;
    },
  },

  actions: {
    changeForgotPwCurrentTab: ({ commit }, value: number) => {
      commit("SET_FORGOT_PW_CURRENT_TAB", value);
    },

    changeForgotPwEmail: ({ commit }, value: string) => {
      commit("SET_FORGOT_PW_EMAIL", value);
    },

    toggleForgotPwErrorMsg: ({ commit }, value: boolean) => {
      commit("SET_FORGOT_PW_ERROR_MSG", value);
    },

    changePwCurrentTab: ({ commit }, value: number) => {
      commit("SET_CHANGE_PW_CURRENT_TAB", value);
    },

    toggleChangePwPopup: ({ commit }, value: boolean) => {
      commit("SET_CHANGE_PW_POPUP", value);
    },

    // 비밀번호 찾기: 이름, 이메일을 입력하여 1. 서버에 존재하는 이메일과 이름인지 2. 둘이 매치가 되는지 확인한다.
    submitForgotPwForm: ({ commit }, { email, userName }) => {
      axios({
        url: api.accounts.forgotPassword(),
        method: "get",
        params: {
          email,
          userName,
        },
      })
        .then((response) => {
          // 다음 페이지로 이동, 이메일 정보 저장
          if (response.data.success) {
            commit("SET_FORGOT_PW_CURRENT_TAB", 1);
            commit("SET_FORGOT_PW_EMAIL", email);
          } else {
            // 에러 메시지 하단에 배치
            commit("SET_FORGOT_PW_ERROR_MSG", true);
          }
        })
        .catch(() => {
          // 에러 메시지 하단에 배치
          commit("SET_FORGOT_PW_ERROR_MSG", true);
        });
    },

    // 비밀번호 찾기: 본인확인을 위해 이메일을 발송한다.
    submitForgotPwAuthForm: ({ getters }, payload) => {
      const { btnStatus, loadingStatus, authFailError } = payload;
      axios({
        url: api.email.emailCert(),
        method: "post",
        data: {
          email: getters.getForgotPwEmail,
        },
      })
        .then((response) => {
          if (response.data.success) {
            // 버튼 섹션 생성, 로딩창 끄기
            btnStatus.value = true;
            loadingStatus.value = false;
          } else {
            // 에러메시지 하단에 배치
            authFailError.value = true;
            loadingStatus.value = false;
          }
        })
        .catch(() => {
          // 에러메시지 하단에 배치
          authFailError.value = true;
          loadingStatus.value = false;
        });
    },

    // 비밀번호 찾기: 이메일 인증이 되었는지 확인한다.
    confirmAuthEmail: ({ commit, getters }, errorStatus) => {
      axios({
        url: api.email.emailConfirmed(),
        method: "post",
        data: {
          email: getters.getForgotPwEmail,
        },
      })
        .then((response) => {
          // 다음 페이지로 이동
          if (response.data.success) {
            commit("SET_FORGOT_PW_CURRENT_TAB", 2);
          } else {
            // 에러 메시지 하단에 배치
            errorStatus.value = true;
          }
        })
        .catch(() => {
          // 에러 메시지 하단에 배치
          errorStatus.value = true;
        });
    },

    // 비밀번호 찾기 : 비밀번호 변경
    submitChangePwForm: (context, data) => {
      const { email, passwordNew, passwordConfirm, failStatus } = data;
      axios({
        url: api.accounts.forgotPassword(),
        method: "put",
        data: {
          email,
          passwordNew,
          passwordConfirm,
        },
      })
        .then((response) => {
          // 홈으로 이동
          if (response.data.success) {
            clickLogin();
          } else {
            // 에러 메시지 하단에 배치
            failStatus.value = true;
          }
        })
        .catch(() => {
          // 에러 메시지 하단에 배치
          failStatus.value = true;
        });
    },

    // 비밀번호 변경: 본인 인증
    submitChangePwAuthForm: ({ rootGetters, commit, dispatch }, params) => {
      const { pwInputValue, failStatus } = params;
      axios({
        url: api.accounts.changePassword(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          passwordOld: pwInputValue.value,
        },
      })
        .then((response) => {
          if (response.data.success) {
            commit("SET_CHANGE_PW_CURRENT_TAB", 1);
          } else {
            failStatus.value = true;
          }
        })
        .catch((error) => {
          console.error(error.response.status);
          if (error.response.status !== 401) {
            failStatus.value = true;
          } else {
            // refreshToken 재발급
            const obj = {
              func: "password/submitChangePwAuthForm",
              params,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
    // 비밀번호 변경: 비밀번호 변경
    submitPwForm: ({ rootGetters, commit, dispatch }, params) => {
      const { passwordNew, passwordConfirm, failStatus } = params;
      axios({
        url: api.accounts.changePassword(),
        method: "put",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          passwordNew,
          passwordConfirm,
        },
      })
        .then((response) => {
          if (response.data.success) {
            clickHome();
            commit("SET_CHANGE_PW_POPUP", true);
          } else {
            failStatus.value = true;
          }
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            failStatus.value = true;
          } else {
            // refreshToken 재발급
            const obj = {
              func: "password/submitPwForm",
              params,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
