import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
import { CarouselCocktail, HotKeywords } from "../../interface";

export interface CarouselState {
  // * 급상승 검색어
  hotKeywords: HotKeywords;
  // * 신규 커스텀 칵테일(carousel)
  latestCustomCocktails: CarouselCocktail[];
  allLatestCustomCocktails: CarouselCocktail[];
  allLatestCustomCocktailPage: number;
}

export const carousel: Module<CarouselState, RootState> = {
  namespaced: true,

  state: {
    // * 급상승 검색어
    hotKeywords: {
      from: "",
      to: "",
      keywords: [{ keyword: "", hits: 0 }],
      delta: [0],
    },
    // * 신규 커스텀 칵테일(carousel)
    latestCustomCocktails: [],
    allLatestCustomCocktails: [],
    allLatestCustomCocktailPage: 0,
  },

  getters: {
    // * 급상승 검색어
    getHotKeywords: (state) => state.hotKeywords,
    // * 신규 커스텀 칵테일(carousel)
    getLatestCustomCocktails: (state) => state.latestCustomCocktails,
    getAllLatestCustomCocktails: (state) => state.allLatestCustomCocktails,
    getAllLatestCustomCocktailPage: (state) =>
      state.allLatestCustomCocktailPage,
  },

  mutations: {
    // * 급상승 검색어
    SET_HOT_KEYWORDS: (state, hotKeywords) => {
      state.hotKeywords = hotKeywords;
    },
    // * 신규 커스텀 칵테일(carousel)
    SET_LATEST_CUSTOM_COCKTAILS: (
      state,
      latestCustomCocktails: CarouselCocktail[]
    ) => {
      state.latestCustomCocktails = latestCustomCocktails;
    },
    SET_ALL_LATEST_CUSTOM_COCKTAILS: (
      state,
      newLatestCustomCocktails: CarouselCocktail[]
    ) => {
      newLatestCustomCocktails.forEach((newLatestCustomCocktail) => {
        state.allLatestCustomCocktails.push(newLatestCustomCocktail);
      });
    },
    SET_ALL_LATEST_CUSTOM_COCKTAIL_PAGE: (state, value) => {
      state.allLatestCustomCocktailPage = value;
    },
    REMOVE_ALL_LATEST_CUSTOM_COCKTAILS: (state) => {
      state.allLatestCustomCocktails = [];
      state.allLatestCustomCocktailPage = 0;
    },
  },
  actions: {
    // * 급상승 검색어
    setHotKeywords: ({ commit, dispatch }) => {
      axios({
        url: api.lookups.hotKeyword(),
        method: "GET",
      })
        .then((res) => {
          console.log(res.data);
          commit("SET_HOT_KEYWORDS", res.data);
        })
        .catch((err) => {
          console.log(err.response);
          dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
        });
    },

    // * 전체보기 리스트 클릭
    clickShowMoreItem: ({}, item: CarouselCocktail) => {
      const { cocktailId, baseCocktailId, type } = item;
      // [type] 1 슈커칵 / 0 커칵 / -1 기본칵
      switch (item.type) {
        // 슈퍼커스텀칵테일
        case 1:
          console.log(1);
          router.push({
            name: "TheSuperCustomCocktailDescView",
            params: { feedId: item.cocktailId },
          });
          break;
        // 커스텀칵테일
        case 0:
          console.log(0);
          router.push({
            name: "TheCustomCocktailDescView",
            params: {
              cocktailId: item.baseCocktailId,
              feedId: item.cocktailId,
            },
          });
          break;
        case -1:
          console.log(-1);
          router.push({
            name: "TheCocktailDescView",
            params: { cocktailId: item.cocktailId },
          });
          break;
      }
    },

    // * 신규 커스텀 칵테일(carousel)
    setLatestCustomCocktails: ({ commit, dispatch }) => {
      axios({
        url: api.homes.latestCustomCocktail(),
        method: "GET",
      })
        .then((res) => {
          console.log(res.data);
          commit("SET_LATEST_CUSTOM_COCKTAILS", res.data);
        })
        .catch((err) => {
          dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          console.error(err.response);
        });
    },
    setAllLatestCustomCocktails: ({ commit, dispatch, getters }) => {
      const page = getters["getAllLatestCustomCocktailPage"];
      axios({
        url: api.homes.latestCustomCocktailView(),
        method: "GET",
        params: {
          page,
        },
      })
        .then((res) => {
          commit("SET_ALL_LATEST_CUSTOM_COCKTAILS", res.data);
          commit("SET_ALL_LATEST_CUSTOM_COCKTAIL_PAGE", page + 1);
        })
        .catch((err) => {
          dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          console.error(err.response);
        });
    },
    removeAllLatestCocktails: ({ commit }) => {
      commit("REMOVE_ALL_LATEST_CUSTOM_COCKTAILS");
    },
  },
};
