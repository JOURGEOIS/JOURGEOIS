import axios from "axios";
import api from "../../api/api";
import { Module } from "vuex";
import { RootState } from "../index";

export interface cocktailData {
  [key: string]: string | string[] | number[] | number;
}

interface CocktailBookMarkUserList {
  uid: number;
  nickname: string;
  profileImg: string;
  isFollowed: number;
  introduce: string;
}

export interface CocktailDescState {
  currentTab: number;
  currentCocktailData: cocktailData;
  failPopupStatus: boolean;
  successPopupStatus: boolean;
  cocktailBookMarkUserPage: number;
  cocktailBookMarkUserList: CocktailBookMarkUserList[];
}

export const cocktailDesc: Module<CocktailDescState, RootState> = {
  namespaced: true,

  state: {
    // 현재 탭
    currentTab: 0,

    // 칵테일 상세 정보
    currentCocktailData: {},

    // 실패 팝업
    failPopupStatus: false,

    // 성공 팝업
    successPopupStatus: false,

    // 칵테일 북마크 페이지
    cocktailBookMarkUserPage: 0,

    // 칵테일 북마크 유저 리스트
    cocktailBookMarkUserList: [],
  },

  getters: {
    // 현재 탭
    getCurrentTab: (state) => {
      return state.currentTab;
    },

    // 칵테일 상세 정보
    getCurrentCocktailData: (state) => {
      return state.currentCocktailData;
    },

    // 칵테일 상세 정보 (재료)
    getCurrentCocktailDataIngredients: (state) => {
      return state.currentCocktailData.materials;
    },

    // 칵테일 상세 정보 (레시피)
    getCurrentCocktailDataRecipe: (state) => {
      return state.currentCocktailData.recipe;
    },

    // 실패 팝업
    getFailPopupStatus: (state) => {
      return state.failPopupStatus;
    },

    // 성공 팝업
    getSuccessPopupStatus: (state) => {
      return state.successPopupStatus;
    },

    // 칵테일 북마크 페이지
    getCocktailBookMarkUSerPage: (state) => {
      return state.cocktailBookMarkUserPage;
    },

    // 칵테일 북마크 유저 리스트
    getCocktailBookMarkUserList: (state) => {
      return state.cocktailBookMarkUserList;
    },
  },

  mutations: {
    // 현재 탭 변경
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },

    // 칵테일 상세 정보 변경
    SET_CURRENT_COCKTAIL_DATA: (state, value: cocktailData) => {
      state.currentCocktailData = value;
    },

    // 칵테일 상세정보-북마크 숫자 변경
    SET_CURRENT_COCKTAIL_DATA_BOOKMARK_COUNT: (state, value: number) => {
      state.currentCocktailData.count = value;
    },

    // 칵테일 상세정보- 북마크 여부 변경
    SET_CURRENT_COCKTAIL_DATA_BOOKMARK_STATUS: (state, value: number) => {
      state.currentCocktailData.status = value;
    },

    //실패 팝업
    SET_FAIL_POPUP_STATUS: (state, value) => {
      state.failPopupStatus = value;
    },

    //성공 팝업
    SET_SUCCESS_POPUP_STATUS: (state, value) => {
      state.successPopupStatus = value;
    },

    // 칵테일 북마크 페이지 변경
    SET_COCKTAIL_BOOKMARK_USER_PAGE: (state, value: number) => {
      state.cocktailBookMarkUserPage = value;
    },

    // 칵테일 북마크 유저 리스트 추가
    ADD_COCKTAIL_BOOKMARK_USER_LIST: (
      state,
      value: CocktailBookMarkUserList[]
    ) => {
      state.cocktailBookMarkUserList.push(...value);
    },

    // 칵테일 북마크 유저 리스트 변경
    SET_COCKTAIL_BOOKMARK_USER_LIST: (
      state,
      value: CocktailBookMarkUserList[]
    ) => {
      state.cocktailBookMarkUserList = value;
    },
  },
  actions: {
    // 탭 변경
    changeCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },

    // db리셋
    resetCocktailDb: ({ commit }) => {
      commit("SET_CURRENT_COCKTAIL_DATA", {});
    },

    // 칵테일 정보 불러오기
    getCocktailDb: ({ rootGetters, commit }, id: number) => {
      const uid = rootGetters["personalInfo/getUserInfoUserId"];
      let headers = {};
      if (uid) {
        headers = {
          uid,
        };
      }
      axios({
        url: api.cocktail.getCocktailData(),
        method: "get",
        headers,
        params: {
          id,
        },
      })
        .then((response) => {
          commit("SET_CURRENT_COCKTAIL_DATA", response.data);
        })
        .catch((error) => {});
    },

    toggleFailPopupStatus: ({ commit }, value: boolean) => {
      commit("SET_FAIL_POPUP_STATUS", value);
    },

    toggleSuccessPopupStatus: ({ commit }, value: boolean) => {
      commit("SET_SUCCESS_POPUP_STATUS", value);
    },

    // 칵테일 북마크
    clickBookMark: ({ rootGetters, commit, dispatch, getters }) => {
      axios({
        url: api.cocktail.clickBookMark(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          cocktailId: getters["getCurrentCocktailData"].id,
        },
      })
        .then((response) => {
          const data = response.data;
          commit("SET_CURRENT_COCKTAIL_DATA_BOOKMARK_COUNT", data.count);
          commit("SET_CURRENT_COCKTAIL_DATA_BOOKMARK_STATUS", data.status);
        })
        .catch((error) => {
          if (error.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "cocktailDesc/clickBookMark",
              params: {},
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 칵테일 북마크한 유저 리스트
    fetchBookMarkUserList: (
      { rootGetters, getters, commit, dispatch },
      { cocktailId }
    ) => {
      const page = getters["getCocktailBookMarkUSerPage"];
      axios({
        url: api.cocktail.showBookMarkUser(),
        method: "get",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          cocktailId,
          page,
        },
      })
        .then((response) => {
          commit("ADD_COCKTAIL_BOOKMARK_USER_LIST", response.data);
          commit("SET_COCKTAIL_BOOKMARK_USER_PAGE", page + 1);
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            error;
          } else {
            // refreshToken 재발급
            const obj = {
              func: "cocktailDesc/fetchBookMarkUserList",
              params: { cocktailId },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 칵테일 북마크한 유저 리스트 리셋
    resetBookMarkUserList: ({ commit }) => {
      commit("SET_COCKTAIL_BOOKMARK_USER_LIST", []);
      commit("SET_COCKTAIL_BOOKMARK_USER_PAGE", 0);
    },
  },
};
