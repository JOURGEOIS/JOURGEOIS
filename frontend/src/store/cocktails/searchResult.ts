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
  img: string | null;
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
  searchCocktailAlls: Cocktail[];
  searchCocktailAllPage: number;

  // * 전체 검색 결과
  wholeCocktails: Cocktail[];
  wholeCocktailPage: number;

  // 필터 검색 결과
  searchFilter: Cocktail[];
  searchFilterPage: number;
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
    searchCocktailAlls: [],
    searchCocktailAllPage: 0,

    // * 전체 검색 결과
    wholeCocktails: [],
    wholeCocktailPage: 0,

    // 필터 검색 결과
    searchFilter: [],
    searchFilterPage: 0,
  },

  getters: {
    // * 현재 탭 번호
    getCurrentTab: (state) => state.currentTab,

    // * 검색어 유저 정보
    getSearchUsers: (state) => state.searchUsers,
    getSearchUserPage: (state) => state.searchUserPage,
    // * 검색어 자동완성 재료 칵테일 정보
    getSearchCocktails: (state) => state.searchCocktails,
    getSearchCocktailPage: (state) => state.searchCocktailPage,
    // * 검색어 칵테일 정보
    getSearchCocktailAlls: (state) => state.searchCocktailAlls,
    getSearchCocktailAllPage: (state) => state.searchCocktailAllPage,
    // * 전체 칵테일 정보
    getWholeCocktails: (state) => state.wholeCocktails,
    getWholeCocktailPage: (state) => state.wholeCocktailPage,

    // 필터 검색 결과
    getSearchFilter: (state) => state.searchFilter,
    getSearchFilterPage: (state) => state.searchFilterPage,
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
      searchCocktails.forEach((cocktail) => {
        state.searchCocktails.push(cocktail);
      });
    },
    SET_SEARCH_COCKTAIL_PAGE: (state, value) => {
      state.searchCocktailPage = value;
    },

    // * 검색어 칵테일 정보 저장
    SET_SEARCH_COCKTAIL_ALLS: (state, searchCocktailAlls: Cocktail[]) => {
      searchCocktailAlls.forEach((cocktail) => {
        state.searchCocktailAlls.push(cocktail);
      });
    },
    SET_SEARCH_COCKTAIL_ALL_PAGE: (state, value) => {
      state.searchCocktailAllPage = value;
    },

    // * 검색어 결과 삭제
    REMOVE_SEARCH_RESULT: (state) => {
      state.searchUsers = [];
      state.searchCocktailAlls = [];
    },

    // * 전체 칵테일 정보 저장
    SET_WHOLE_COCKTAILS: (state, newWholeCocktails: Cocktail[]) => {
      newWholeCocktails.forEach((newCocktail) => {
        state.wholeCocktails.push(newCocktail);
      });
    },
    SET_WHOLE_COCKTAIL_PAGE: (state, value) => {
      state.wholeCocktailPage = value;
    },

    // 필터 검색 결과
    REMOVE_SEARCH_FILTER: (state, data: Cocktail[]) => {
      state.searchFilter = data;
    },

    SET_SEARCH_FILTER: (state, data: Cocktail[]) => {
      data.forEach((data) => {
        state.searchFilter.push(data);
      });
    },
    SET_SEARCH_FILTER_PAGE: (state, value: number) => {
      state.searchFilterPage = value;
    },
  },

  actions: {
    // * 검색어 string 저장
    setCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },

    // * 검색어 User 검색결과
    setSearchUser: ({ dispatch, commit, getters, rootGetters }, data) => {
      axios({
        url: api.lookups.user(),
        method: "GET",
        headers: {
          uid: rootGetters["personalInfo/getUserInfoUserId"],
        },
        params: {
          keyword: data.keyword,
          page: getters.getSearchUserPage,
        },
      })
        .then((res) => {
          commit("SET_SEARCH_USER_PAGE", getters.getSearchUserPage + 1);
          // 최대 10개 유저 정보 리스트에 추가
          commit("SET_SEARCH_USERS", res.data);
        })
        .catch((err) => {
          // 실패 시, 홈으로 이동 후 에러 모달 on
          if (err.response.status !== 401) {
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
            console.error(err.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "searchResult/setSearchUser",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // * 검색어 자동완성 재료 Cocktail 검색결과
    setSearchCocktail: (
      { commit, state, rootGetters, getters },
      ingredientId: number
    ) => {
      // 오류 처리
      if (typeof ingredientId !== "number") return;
      axios({
        url: api.lookups.cocktail(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          id: ingredientId,
          page: getters.getSearchCocktailPage,
        },
      })
        .then((res) => {
          const newSearchCocktails = res.data;
          // 최대 10개 칵테일 정보 리스트에 추가
          commit("SET_SEARCH_COCKTAILS", newSearchCocktails);
          const page = getters.getSearchCocktailPage;
          commit("SET_SEARCH_COCKTAIL_PAGE", page + 1);
        })
        .catch((err) => {
          console.error(err.response);
        });
    },

    // * 검색어 Cocktail 검색결과
    setSearchCocktailAll: (
      { dispatch, commit, getters, rootGetters },
      data
    ) => {
      axios({
        url: api.lookups.cocktailall(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          keyword: data.keyword,
          page: getters.getSearchCocktailAllPage,
        },
      })
        .then((res) => {
          const newSearchCocktailAlls = res.data;
          // 최대 10개 칵테일 정보 리스트에 추가
          commit("SET_SEARCH_COCKTAIL_ALLS", newSearchCocktailAlls);
          const page = getters.getSearchCocktailAllPage;
          commit("SET_SEARCH_COCKTAIL_ALL_PAGE", page + 1);
        })
        .catch((err) => {
          // 실패 시, 홈으로 이동 후 에러 모달 on
          if (err.response.status !== 401) {
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
            console.error(err.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "post/setLikedUsers",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // * 검색 결과 제거
    removeSearchResult: ({ commit }) => {
      commit("REMOVE_SEARCH_RESULT");
      // 칵테일 탭으로 설정
      commit("SET_CURRENT_TAB", 0);

      // 페이지 번호 0으로 초기화
      commit("SET_SEARCH_USER_PAGE", 0);
      commit("SET_SEARCH_COCKTAIL_ALL_PAGE", 0);
    },

    // * 전체 칵테일 추가
    setWholeCocktail: ({ dispatch, commit, getters, rootGetters }) => {
      axios({
        url: api.lookups.wholeCocktail(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          page: getters["getWholeCocktailPage"],
        },
      })
        .then((res) => {
          const newWholeCocktails = res.data;
          commit("SET_WHOLE_COCKTAILS", newWholeCocktails);
          const page = getters.getWholeCocktailPage;
          commit("SET_WHOLE_COCKTAIL_PAGE", page + 1);
        })
        .catch((err) => {
          // 실패 시, 홈으로 이동 후 에러 모달 on
          if (err.response.status !== 401) {
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
            console.error(err.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "searchResult/setWholeCocktail",
              params: {},
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    setSearchFilter: ({ commit, getters, rootGetters }) => {
      const type = rootGetters["cocktailSearch/getSearchFilterAlcohol"];
      let abv = rootGetters["cocktailSearch/getSearchFilterAlcoholStrength"];
      const materials =
        rootGetters["cocktailSearch/getSearchFilterIngredients"];
      const page = getters["getSearchFilterPage"];
      if (!type) {
        abv = [0, 0];
      }
      axios({
        url: api.lookups.filterResult(),
        method: "post",
        data: {
          type,
          abv,
          materials,
          page,
        },
      })
        .then((response) => {
          commit("SET_SEARCH_FILTER", response.data);
          commit("SET_SEARCH_FILTER_PAGE", page + 1);
        })
        .catch((error) => console.error(error));
    },

    removeSearchFilter: ({ commit }) => {
      commit("REMOVE_SEARCH_FILTER", []);
      commit("SET_SEARCH_FILTER_PAGE", 0);
    },
  },
};
