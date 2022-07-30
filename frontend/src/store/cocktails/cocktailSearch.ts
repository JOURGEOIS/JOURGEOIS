import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";

export interface autoCompleteWord {
  id: number;
  name: string;
  nameKr: string;
  type: string;
}

export interface autoCompleteWords {
  cocktails: autoCompleteWord[];
  ingredients: autoCompleteWord[];
  users: autoCompleteWord[];
}

export interface CocktailSearchState {
  searchInputValue: string;
  recentSearchWords: string[];
  autoCompleteSearchWords: autoCompleteWords;
  filterStatus: boolean;
}

export const cocktailSearch: Module<CocktailSearchState, RootState> = {
  namespaced: true,

  state: {
    // * 칵테일 검색
    searchInputValue: "",
    recentSearchWords:
      JSON.parse(localStorage.getItem("recentSearchWords") || "[]") || [],
    autoCompleteSearchWords: { cocktails: [], ingredients: [], users: [] },
    filterStatus: false,
  },

  getters: {
    // * 검색창 검색어 string
    getSearchInputValue: (state) => state.searchInputValue,

    // * 최근 검색어 string[]
    getRecentSearchWords: (state) => state.recentSearchWords,

    // * 검색 자동완성 autoCompleteWord[]
    getAutoCompleteSearchWords: (state) => state.autoCompleteSearchWords,

    // 필터 status
    // getFilterStatus: (state) => state.filterStatus,
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

    // * 검색 자동완성 state 저장
    SET_AUTO_COMPLETE_SEARCH_WORDS: (state, words) => {
      state.autoCompleteSearchWords = words;
    },

    // * 최근 검색어 state 삭제
    REMOVE_RECENT_SEARCH_WORDS: (state) => {
      state.recentSearchWords = [];
    },

    // 필터 토글
    SET_FILTER_STATUS: (state, value: boolean) => {
      // state.filterStatus = value;
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

    // * 검색 자동완성 list 모두 state 저장
    // 해당 단어를 포함하는 칵테일, 재료, 유저 목록 호출
    setAutoCompleteSearchWords: ({ commit }, keyword: string): void => {
      if (!keyword) return;
      axios({
        method: "get",
        url: api.lookups.autoComplete(),
        params: {
          keyword,
        },
      })
        .then((res) => {
          const cocktails: autoCompleteWord[] = [];
          const ingredients: autoCompleteWord[] = [];
          const users: autoCompleteWord[] = [];
          const autoCompleteWords = {
            cocktails,
            ingredients,
            users,
          };
          // 타입별로 구분
          res.data.forEach((word: autoCompleteWord) => {
            if (word.type === "칵테일") {
              autoCompleteWords.cocktails.push(word);
            } else if (word.type === "재료") {
              autoCompleteWords.ingredients.push(word);
            } else if (word.type === "계정") {
              autoCompleteWords.users.push(word);
            }
          });
          commit("SET_AUTO_COMPLETE_SEARCH_WORDS", autoCompleteWords);
        })
        .catch((err) => console.error(err.response));
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

    // 필터 토글
    toggleFilter: ({ commit }, value: boolean) => {
      commit("SET_FILTER_STATUS", value);
    },
  },
};
