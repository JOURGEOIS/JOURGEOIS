import { Module } from "vuex";
import { RootState } from "../index";
import drf from "../../api/drf";
import axios from "axios";
import { clickHome } from "../../modules/clickEvent";

// Signup의 State 타입을 설정한다.
export interface PasswordState {
  forgotPwCurrentTab: number;
  forgotPwEmail: string;
  forgotPwErrorMsgStatus: boolean;
}

export const password: Module<PasswordState, RootState> = {
  namespaced: true,

  state: {
    forgotPwCurrentTab: 0,
    forgotPwEmail: "",
    forgotPwErrorMsgStatus: false,
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
    submitForgotPwForm: ({ commit }, { userId, userName }) => {
      console.log(userId, userName);
      axios({
        url: drf.accounts.forgotPassword(),
        method: "get",
        params: {
          userId,
          userName,
        },
      })
        .then((response) => {
          console.log(response.data);
          commit("SET_FORGOT_PW_CURRENT_TAB", 1);
          commit("SET_FORGOT_PW_EMAIL", userId);
        })
        .catch((error) => {
          console.log(error);
          commit("SET_FORGOT_PW_ERROR_MSG", true); // 406에러일때
        });
    },
    submitChangePwForm: ({ commit }, data) => {
      console.log(data);
      axios({
        url: drf.accounts.forgotPassword(),
        method: "put",
        data,
      })
        .then((response) => {
          if (response.data.success === true) {
            clickHome();
          }
        })
        .catch((error) => console.log(error));
    },
  },
};
