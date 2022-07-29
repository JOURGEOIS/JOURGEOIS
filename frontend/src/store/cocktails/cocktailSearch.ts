import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";

export interface CocktailSearchState {
  searchInputValue: string;
  recentSearchWords: string[];
}

export const cocktailSearch: Module<CocktailSearchState, RootState> = {
  namespaced: true,

  state: {
    // * 칵테일 검색
    searchInputValue: "",
    recentSearchWords:
      JSON.parse(localStorage.getItem("recentSearchWords") || "[]") || [],
  },

  getters: {
    // * 검색창 검색어 string
    getSearchInputValue: (state) => state.searchInputValue,

    // * 최근 검색어 string[]
    getRecentSearchWords: (state) => state.recentSearchWords,
  },

  mutations: {
    // * 검색어 string 저장
    SET_SEARCH_INPUT_VALUE: (state, value) => {
      state.searchInputValue = value;
    },

    // * 최근 검색어 state 저장
    SET_RECENT_SEARCH_WORDS: (state, words) => {
      state.recentSearchWords = words;
    },

    // * 최근 검색어 state 삭제
    REMOVE_RECENT_SEARCH_WORDS: (state) => {
      state.recentSearchWords = [];
    },
  },

  actions: {
    // * 검색어 string 저장
    setSearchInputValue: ({ commit }, value) => {
      commit("SET_SEARCH_INPUT_VALUE", value);
    },

    // * 최근 검색어 5개 로컬스토리지, state 저장
    setRecentSearchWords: ({ state, commit, getters }) => {
      const newWord = getters.getSearchInputValue;
      if (!newWord) return;
      const words = state.recentSearchWords;
      words.push(newWord);
      if (words.length > 5) {
        words.shift();
      }
      localStorage.setItem("recentSearchWords", JSON.stringify(words));
      commit("SET_RECENT_SEARCH_WORDS", words);
    },

    // * 최근 검색어 모두 로컬스토리지, state 삭제
    removeRecentSearchWords: ({ commit }) => {
      localStorage.removeItem("recentSearchWords");
      commit("REMOVE_RECENT_SEARCH_WORDS");
    },

    // * [검색 결과] 칵테일
    searchKeywordCocktail: ({ commit }, data) => {
      console.log(data);
      axios({
        url: api.lookups.cocktail(),
        method: "GET",
        params: data,
      })
        .then((res) => console.log(res.data))
        .catch((err) => console.error(err.response));
    },
  },
};
