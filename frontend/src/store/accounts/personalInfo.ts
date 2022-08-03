import axios from "axios";
import { Module } from "vuex";
import api from "../../api/api";
import { clickHome } from "../../functions/clickEvent";
import { RootState } from "../index";

export interface userInfo {
  [props: string]: string;
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
    accessToken: localStorage.getItem("accessToken") || "",
    refreshToken: localStorage.getItem("refreshToken") || "",
    userInfo: JSON.parse(localStorage.getItem("userInfo") || "{}"),
    refreshFailPopupStatus: false,
  },

  getters: {
    isLoggedIn: (state) => {
      return !!state.accessToken;
    },
    getAccessToken: (state) => {
      return state.accessToken;
    },
    getRefreshToken: (state) => {
      return state.refreshToken;
    },
    getUserInfo: (state) => {
      return state.userInfo;
    },
    getUserInfoId: (state) => {
      return state.userInfo.email;
    },
    getRefreshFailPopupStatus: (state) => {
      return state.refreshFailPopupStatus;
    },
  },

  mutations: {
    SET_ACCESS_TOKEN: (state, token) => {
      state.accessToken = token;
    },
    SET_REFRESH_TOKEN: (state, token) => {
      state.refreshToken = token;
    },
    SET_USER_INFO: (state, data) => {
      state.userInfo = data;
    },
    SET_REFRESH_FAIL: (state, value) => {
      state.refreshFailPopupStatus = value;
    },
  },

  actions: {
    saveToken: ({ commit }, { accessToken, refreshToken }) => {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      commit("SET_ACCESS_TOKEN", accessToken);
      commit("SET_REFRESH_TOKEN", refreshToken);
    },
    removeToken: ({ commit }) => {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      commit("SET_ACCESS_TOKEN", "");
      commit("SET_REFRESH_TOKEN", "");
    },
    saveUserInfo: ({ commit }, data: object) => {
      const jsonUserInfo = JSON.stringify(data);
      localStorage.setItem("userInfo", jsonUserInfo);
      commit("SET_USER_INFO", data);
    },
    removeUserInfo: ({ commit }) => {
      localStorage.removeItem("userInfo");
      commit("SET_USER_INFO", {});
    },
    resetUserInfo: ({ dispatch }) => {
      dispatch("removeToken");
      dispatch("removeUserInfo");
      dispatch("cocktailSearch/removeRecentSearchWords", {}, { root: true });
      dispatch("cocktailSearch/setSearchFilterData", {}, { root: true });
    },
    toggleRefreshFailPopup: ({ commit }, value) => {
      commit("SET_REFRESH_FAIL", value);
    },
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
          console.log(response.data);
          profileImage.image = response.data.url;
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            // 실패 팝업
            dispatch("account/toggleUserInfoChangeError", true, { root: true });
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
