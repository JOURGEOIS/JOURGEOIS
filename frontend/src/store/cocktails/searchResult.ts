import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";

export interface User {
  email: string | null;
  name: string | null;
  nickname: string;
  profileImg: string;
  profileLink: string | null;
  introduce: string | null;
}

export interface Cocktail {
  id: number;
  name: string;
  cocktailImg: string | null;
  alcohol: number | null;
  baseLiquor: string;
}

// ! main State
export interface SearchResultState {
  currentTab: number;

  // * 검색 결과
  searchUsers: User[];
  searchUserPage: number;
  searchCocktails: Cocktail[];
  searchCocktailPage: number;
}

export const searchResult: Module<SearchResultState, RootState> = {
  namespaced: true,

  state: {
    currentTab: 0,

    // * 검색 결과
    searchUsers: [],
    searchUserPage: 0,
    searchCocktails: [],
    searchCocktailPage: 0,
  },

  getters: {
    // * 현재 탭 번호
    getCurrentTab: (state) => state.currentTab,

    // * 검색어 유저 정보
    getSearchUsers: (state) => state.searchUsers,
    getSearchUserPage: (state) => state.searchUserPage,
    // * 검색어 칵테일 정보
    getSearchCocktails: (state) => state.searchCocktails,
    getSearchCocktailPage: (state) => state.searchCocktailPage,
  },

  mutations: {
    // * 현재 탭 번호 저장
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },

    // * 검색어 유저 정보 저장
    SET_SEARCH_USERS: (state, searchUsers: User[]) => {
      searchUsers.forEach((user) => {
        state.searchUsers.push(user);
      });
    },
    SET_SEARCH_USER_PAGE: (state, value) => {
      state.searchUserPage = value;
    },

    // * 검색어 칵테일 정보 저장
    SET_SEARCH_COCKTAILS: (state, searchCocktails: Cocktail[]) => {
      searchCocktails.forEach((user) => {
        state.searchCocktails.push(user);
      });
    },
    SET_SEARCH_COCKTAIL_PAGE: (state, value) => {
      state.searchCocktailPage = value;
    },

    // * 검색어 결과 삭제
    REMOVE_SEARCH_RESULT: (state) => {
      state.searchUsers = [];
      state.searchCocktails = [];
    },
  },

  actions: {
    // * 검색어 string 저장
    setCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },

    // * 검색어 User 검색결과
    setSearchUser: ({ commit, state }, keyword: string) => {
      axios({
        url: api.lookups.user(),
        method: "GET",
        headers: {
          email: "tmdgns1126@naver.com",
        },
        params: {
          keyword,
          page: state.searchUserPage,
        },
      })
        .then((res) => {
          commit("SET_SEARCH_USER_PAGE", state.searchUserPage + 1);
          // 최대 10개 유저 정보 리스트에 추가
          commit("SET_SEARCH_USERS", res.data);
        })
        .catch((err) => {
          console.error(err.response);
        });
    },

    // * 검색어 Cocktail 검색결과
    setSearchCocktail: ({ commit, state }, keyword: string) => {
      axios({
        url: api.lookups.cocktailall(),
        method: "GET",
        headers: { email: "tmdgns1126@naver.com" },
        params: {
          keyword,
          page: state.searchCocktailPage,
        },
      })
        .then((res) => {
          commit("SET_SEARCH_COCKTAIL_PAGE", state.searchCocktailPage + 1);
          // 최대 10개 칵테일 정보 리스트에 추가
          commit("SET_SEARCH_COCKTAILS", res.data);
        })
        .catch((err) => {
          console.error(err.response);
        });
    },

    // * 검색 결과 제거
    removeSearchResult: ({ commit }) => {
      commit("REMOVE_SEARCH_RESULT");
      // 칵테일 탭으로 설정
      commit("SET_CURRENT_TAB", 0);

      // 페이지 번호 0으로 초기화
      commit("SET_SEARCH_USER_PAGE", 0);
      commit("SET_SEARCH_COCKTAIL_PAGE", 0);
    },
  },
};
