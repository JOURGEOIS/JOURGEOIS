import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";

// 자동완성 단어 interface
export interface autoCompleteWord {
  id: number;
  name: string;
  nameKr: string;
  type: string;
}

// 자동완성 5개 리스트 interface
export interface autoCompleteWords {
  cocktails: autoCompleteWord[];
  ingredients: autoCompleteWord[];
  users: autoCompleteWord[];
}

// 인기 검색어 interface
export interface popularSearchWords {
  from: string;
  to: string;
  keywords: string[];
  delta: number[];
}

// 필터 interface
export interface filter {
  type: boolean;
  abv: number[];
  materials: number[];
}

// ! 기본 State Interface
export interface CocktailSearchState {
  searchInputValue: string;
  recentSearchWords: string[];
  popularSearchWords: popularSearchWords;
  autoCompleteSearchWords: autoCompleteWords;
  searchFilterData: filter;
  filterStatus: boolean;
  filterClass: string;
  filterResultCnt: number;
}

export const cocktailSearch: Module<CocktailSearchState, RootState> = {
  namespaced: true,

  state: {
    // * 칵테일 검색
    searchInputValue: "",
    recentSearchWords:
      JSON.parse(localStorage.getItem("recentSearchWords") || "[]") || [],
    popularSearchWords: { from: "", to: "", keywords: [""], delta: [0] },
    autoCompleteSearchWords: { cocktails: [], ingredients: [], users: [] },

    //========================
    // 칵테일 filter
    searchFilterData: JSON.parse(
      localStorage.getItem("searchFilter") ||
        '{"type": 1, "abv": [6, 15], "materials": []}'
    ),
    filterStatus: false,
    filterClass: "",
    filterResultCnt: 0,
  },

  getters: {
    // * 검색창 검색어 string
    getSearchInputValue: (state) => state.searchInputValue,

    // * 최근 검색어 string[]
    getRecentSearchWords: (state) => state.recentSearchWords,

    // * 인기 검색어 string[]
    getPopularSearchWords: (state) => state.popularSearchWords,

    // * 검색 자동완성 autoCompleteWord[]
    getAutoCompleteSearchWords: (state) => state.autoCompleteSearchWords,

    //========================
    // 필터 status
    getFilterStatus: (state) => state.filterStatus,

    // 필터 class
    getFilterClass: (state) => state.filterClass,

    // 필터 정보
    getSearchFilterData: (state) => state.searchFilterData,

    // 필터 재료 정보
    getSearchFilterIngredients: (state) => state.searchFilterData.materials,

    // 필터 알코올 정보
    getSearchFilterAlcohol: (state) => state.searchFilterData.type,

    // 필터 도수 정보
    getSearchFilterAlcoholStrength: (state) => state.searchFilterData.abv,

    // 필터 결과 (개수)
    getFilterResultCnt: (state) => state.filterResultCnt,
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

    // * 인기 검색어 state 저장
    SET_POPULAR_SEARCH_WORDS: (state, words) => {
      state.popularSearchWords = words;
    },

    // * 검색 자동완성 state 저장
    SET_AUTO_COMPLETE_SEARCH_WORDS: (state, words) => {
      state.autoCompleteSearchWords = words;
    },

    // * 최근 검색어 state 삭제
    REMOVE_RECENT_SEARCH_WORDS: (state) => {
      state.recentSearchWords = [];
    },

    //========================
    // 필터 토글
    SET_FILTER_STATUS: (state, value: boolean) => {
      state.filterStatus = value;
    },

    // 필터 클래스 설정
    SET_FILTER_CLASS: (state, value: string) => {
      state.filterClass = value;
    },
    // 필터 데이터 저장
    SET_SEARCH_FILTER_DATA: (state, value: filter) => {
      state.searchFilterData = value;
    },
    // 필터 개수 저장
    SET_SEARCH_RESULT_CNT: (state, value: number) => {
      state.filterResultCnt = value;
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
      // 새로 입력된 검색어가 비어있다면 return
      if (!newWord) return;
      // 새로 입력된 검색어가 최근 5개 중에 있다면 삭제
      let words = state.recentSearchWords;
      words = words.filter((word) => word !== newWord);
      // words에 newWord 추가
      words.push(newWord);
      // 최근 검색어가 6개라면 가장 오래된 거 하나 뺌
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

    // * 인기 검색어 불러오기
    setPopularSearchWords: ({ commit }) => {
      axios({
        url: api.lookups.weeklyHotKeyword(),
        method: "GET",
      })
        .then((res) => {
          commit("SET_POPULAR_SEARCH_WORDS", res.data);
        })
        .catch(() => {});
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
        .catch(() => {});
    },

    //========================
    // 필터 토글
    toggleFilter: ({ commit }, value: boolean) => {
      commit("SET_FILTER_STATUS", value);
    },

    // 필터 클래스 변경
    changeFilterClass: ({ commit }, value: string) => {
      commit("SET_FILTER_CLASS", value);
    },

    // 필터 삭제하기
    setSearchFilterData: ({ commit }) => {
      localStorage.removeItem("searchFilter");
      commit("SET_SEARCH_FILTER_DATA", {
        type: 1,
        abv: [6, 15],
        materials: [],
      });
    },

    // 필터 검색하기
    searchFilter: ({ commit }, data: filter) => {
      const searchFilter = JSON.stringify(data);
      localStorage.setItem("searchFilter", searchFilter);
      commit("SET_SEARCH_FILTER_DATA", data);
    },

    // 필터 갯수 찾기, 저장
    searchFilterResultCnt: ({ commit, getters }) => {
      const type = getters["getSearchFilterAlcohol"];
      let abv = getters["getSearchFilterAlcoholStrength"];
      const materials = getters["getSearchFilterIngredients"];
      if (!type) {
        abv = [0, 0];
      }
      axios({
        url: api.lookups.filterCnt(),
        method: "post",
        data: {
          type,
          abv,
          materials,
        },
      })
        .then((response) => {
          commit("SET_SEARCH_RESULT_CNT", response.data);
        })
        .catch(() => {});
    },

    // 필터 재료 추가
    addFilterIngredients: ({ commit, getters, dispatch }, id: number) => {
      const type = getters["getSearchFilterAlcohol"];
      const abv = getters["getSearchFilterAlcoholStrength"];
      const materials = getters["getSearchFilterIngredients"];
      if (!materials.includes(id)) {
        materials.push(id);
      } else {
        materials.forEach((item: number, index: number) => {
          if (item === id) {
            materials.splice(index, 1);
          }
        });
      }
      const data = {
        type,
        abv,
        materials,
      };
      commit("SET_SEARCH_FILTER_DATA", data);
      dispatch("searchFilterResultCnt");
    },

    // 필터 알코올 여부 변경
    changeFilterAlcohol: ({ commit, getters, dispatch }, value: number) => {
      const abv = getters["getSearchFilterAlcoholStrength"];
      const materials = getters["getSearchFilterIngredients"];

      const data = {
        type: value,
        abv,
        materials,
      };
      commit("SET_SEARCH_FILTER_DATA", data);
      dispatch("searchFilterResultCnt");
    },

    // 필터 도수 변경
    changeFilterAlcoholStrength: (
      { commit, getters, dispatch },
      value: number[]
    ) => {
      const type = getters["getSearchFilterAlcohol"];
      const materials = getters["getSearchFilterIngredients"];

      const data = {
        type,
        abv: value,
        materials,
      };
      commit("SET_SEARCH_FILTER_DATA", data);
      dispatch("searchFilterResultCnt");
    },

    // 필터 검색 결과 localStorage에 저장
    submitSearchFilter: ({ getters, commit }) => {
      const data = getters["getSearchFilterData"];
      const jsonData = JSON.stringify(data);
      localStorage.setItem("searchFilter", jsonData);
    },

    // 필터 재료 리셋
    resetSearchFilterIngredients: ({ getters, commit, dispatch }) => {
      const data = getters["getSearchFilterData"];
      data.materials = [];
      commit("SET_SEARCH_FILTER_DATA", data);
      dispatch("searchFilterResultCnt");
    },
  },
};
