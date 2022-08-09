import axios from "axios";
import { Module } from "vuex";
import api from "../../api/api";
import { clickHome } from "../../functions/clickEvent";
import { RootState } from "../index";

export interface userInfo {
  [props: string]: string | number;
}

export interface PersonalInfoState {
  accessToken: string;
  refreshToken: string;
  userInfo: userInfo;
  refreshFailPopupStatus: boolean;
}

export const personalInfo: Module<PersonalInfoState, RootState> = {
  namespaced: true,

  state: {
    // 엑세스 토큰
    accessToken: localStorage.getItem("accessToken") || "",

    // 리프레스 토큰
    refreshToken: localStorage.getItem("refreshToken") || "",

    // 유저 정보
    userInfo: JSON.parse(localStorage.getItem("userInfo") || "{}"),

    // 리프레시 팝업
    refreshFailPopupStatus: false,
  },

  getters: {
    // 로그인 여부
    isLoggedIn: (state) => {
      return !!state.accessToken;
    },
    // 엑세스 토큰
    getAccessToken: (state) => {
      return state.accessToken;
    },
    // 리프레시 토큰
    getRefreshToken: (state) => {
      return state.refreshToken;
    },

    // 유저 정보
    getUserInfo: (state) => {
      return state.userInfo;
    },

    // 프로필 본인 이미지
    getProfileImage: (state) => {
      return state.userInfo.profileImg;
    },
    // uid
    getUserInfoUserId: (state) => {
      return state.userInfo.uid;
    },

    // 이메일
    getUserInfoId: (state) => {
      return state.userInfo.email;
    },

    // 리프레시 실패 팝업
    getRefreshFailPopupStatus: (state) => {
      return state.refreshFailPopupStatus;
    },
  },

  mutations: {
    // 엑세스 토큰
    SET_ACCESS_TOKEN: (state, token) => {
      state.accessToken = token;
    },

    // 리프레시 토큰
    SET_REFRESH_TOKEN: (state, token) => {
      state.refreshToken = token;
    },

    // 유저 정보
    SET_USER_INFO: (state, data) => {
      state.userInfo = data;
    },

    // 리프레시 실패 팝업
    SET_REFRESH_FAIL: (state, value) => {
      state.refreshFailPopupStatus = value;
    },
  },

  actions: {
    // 토큰 저장
    saveToken: ({ commit }, { accessToken, refreshToken }) => {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      commit("SET_ACCESS_TOKEN", accessToken);
      commit("SET_REFRESH_TOKEN", refreshToken);
    },

    // 토큰 제거
    removeToken: ({ commit }) => {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      commit("SET_ACCESS_TOKEN", "");
      commit("SET_REFRESH_TOKEN", "");
    },

    // 유저 정보 저장
    saveUserInfo: ({ commit }, data: object) => {
      console.log(data);
      const jsonUserInfo = JSON.stringify(data);
      localStorage.setItem("userInfo", jsonUserInfo);
      commit("SET_USER_INFO", data);
    },

    // 유저 정보 삭제
    removeUserInfo: ({ commit }) => {
      localStorage.removeItem("userInfo");
      commit("SET_USER_INFO", {});
    },

    // vuex 및 로컬스토리지 리셋 (초기화)
    resetUserInfo: ({ dispatch }) => {
      dispatch("removeToken");
      dispatch("removeUserInfo");
      dispatch("cocktailSearch/removeRecentSearchWords", {}, { root: true });
      dispatch("cocktailSearch/setSearchFilterData", {}, { root: true });
    },

    // 레프레시 실패 팝업
    toggleRefreshFailPopup: ({ commit }, value) => {
      commit("SET_REFRESH_FAIL", value);
    },
    // 모듈화된 리프레시 토큰 요청 함수
    requestRefreshToken: ({ getters, dispatch, commit }, obj) => {
      const { func, params } = obj;
      axios({
        url: api.token.token(),
        method: "post",
        data: {
          token: getters.getRefreshToken,
        },
      })
        .then((response) => {
          console.log("토큰 성공");
          const data = response.data;
          const token = {
            accessToken: data.accessToken,
            refreshToken: data.refreshToken,
          };
          dispatch("saveToken", token);
          dispatch(func, params, { root: true });
        })
        .catch(() => {
          dispatch("resetUserInfo");
          clickHome();
          commit("SET_REFRESH_FAIL", true);
        });
    },
    // 이미지 저장
    changeProfileImage: ({ getters, dispatch }, params) => {
      const { imageFile, profileImage } = params;
      axios({
        url: api.accounts.profile(),
        method: "post",
        headers: {
          Authorization: getters.getAccessToken,
          "Content-Type": "multipart/form-data",
        },
        data: {
          profileLink: imageFile,
        },
      })
        .then((response) => {
          profileImage.image = response.data.url;
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            // 실패 팝업
            dispatch("account/toggleUserInfoChangeError", true, { root: true });
            console.log(error.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "personalInfo/changeProfileImage",
              params,
            };
            dispatch("requestRefreshToken", obj);
          }
        });
    },
    // 프로필 변경
    submitChangeUserInfoForm: ({ dispatch, getters }, params) => {
      const { name, nickname, profileLink, introduce } = params;
      axios({
        url: api.accounts.profile(),
        method: "put",
        headers: {
          Authorization: getters.getAccessToken,
          "Content-Type": "multipart/form-data",
        },
        data: {
          name,
          nickname,
          profileLink,
          introduce,
        },
      })
        .then((response) => {
          // localStorage에 저장,vuex에 저장
          dispatch("saveUserInfo", response.data);

          //성공 팝업
          dispatch("account/toggleUserInfoChangeSuccess", true, { root: true });
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            // 실패 팝업
            dispatch("account/toggleUserInfoChangeError", true, { root: true });
            console.log(error.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "personalInfo/submitChangeUserInfoForm",
              params,
            };
            dispatch("requestRefreshToken", obj);
          }
        });
    },
  },
};
