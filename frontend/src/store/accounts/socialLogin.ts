import { Module } from "vuex";
import { RootState } from "../index";
import { clickHome } from "../../functions/clickEvent";
import api from "../../api/api";
import axios from "axios";
import router from "../../router";
import route from "../../router";
import { GoogleUserInfo } from "../../interface";
import KakaoLoginVue from "../../views/accounts/KakaoLogin.vue";

export interface SocialLoginState {
  googleLoginApi: string;
  googleUserInfo: GoogleUserInfo;
}

export const socialLogin: Module<SocialLoginState, RootState> = {
  namespaced: true,

  state: {
    googleLoginApi: "",
    // 구글 유저 상세 정보
    googleUserInfo: {
      userInfo: {
        uid: 0,
        email: "",
        name: "",
        nickname: "",
        profileImg: "",
        profileLink: null,
        introduce: null,
      },
      token: {
        accessToken: "",
        refreshToken: "",
      },
    },
  },

  getters: {
    getGoogleLoginApi: (state) => state.googleLoginApi,

    // 구글 유저 상세 정보
    googleUserInfo: (state) => state.googleUserInfo,
  },

  mutations: {
    SET_GOOGLE_LOGIN_API: (state, value) => {
      state.googleLoginApi = value;
    },
    SET_GOOGLE_USER_INFO: (state, value: GoogleUserInfo) => {
      state.googleUserInfo = value;
    },
  },

  actions: {
    getGoogleLoginApi: ({ commit, dispatch }, value: string) => {
      axios({
        url: api.accounts.googleLogin(),
        method: "GET",
      })
        .then((res) => {
          const data = res.data;
          commit("SET_GOOGLE_LOGIN_API", data);
          dispatch("getUserInfo");
        })
        .catch((err) => {
          console.error(err.response);
        });
    },
    getUserInfo: ({ commit, getters }) => {
      const apiURL = getters.getGoogleLoginApi;
      axios({
        url: apiURL,
        method: "GET",
      })
        .then((res) => {
          window.location.href = apiURL;
          commit("SET_GOOGLE_USER_INFO", res.data);
        })
        .catch((err) => {
          console.error(err.data);
          console.error(err);
        });
    },
    // 카카오로그인
    kakaoLogin: () => {
      window.Kakao.Auth.login({
        scope: "profile, account_email",
        success: KakaoLoginVue.getKakaoAcount,
      });
    },
    getKakaoAcount: () => {
      window.Kakao.API.request({
        url: "/v2/user/me",
      });
    },
  },
};
