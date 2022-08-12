import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
import { CarouselCocktail, HotKeywords } from "../../interface";

export interface CarouselState {
  // * 급상승 검색어
  hotKeywords: HotKeywords;
  hotKeywordDelta: number[];
  hotKeywordDate: string;
  // * 신규 커스텀 칵테일(carousel)
  latestCustomCocktails: CarouselCocktail[];
  allLatestCustomCocktails: CarouselCocktail[];
  allLatestCustomCocktailPage: number;
  // * 주류주아 HOT 칵테일(carousel)
  hotCocktails: CarouselCocktail[];
  allHotCocktails: CarouselCocktail[];
  allHotCocktailPage: number;
  // * 유저들의 이번 주 커스텀 칵테일(carousel)
  weeklyHotCocktails: CarouselCocktail[];
  allWeeklyHotCocktails: CarouselCocktail[];
  allWeeklyHotCocktailPage: number;
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
    hotKeywordDelta: [0, 0, 0, 0, 0],
    hotKeywordDate: "",
    // * 신규 커스텀 칵테일(carousel)
    latestCustomCocktails: [],
    allLatestCustomCocktails: [],
    allLatestCustomCocktailPage: 0,
    // * 주류주아 HOT 칵테일(carousel)
    hotCocktails: [],
    allHotCocktails: [],
    allHotCocktailPage: 0,
    // * 유저들의 이번 주 커스텀 칵테일(carousel)
    weeklyHotCocktails: [],
    allWeeklyHotCocktails: [],
    allWeeklyHotCocktailPage: 0,
  },

  getters: {
    // * 급상승 검색어
    getHotKeywords: (state) => state.hotKeywords,
    getHotKeywordDelta: (state) => state.hotKeywordDelta,
    getHotKeywordDate: (state) => state.hotKeywordDate,
    // * 신규 커스텀 칵테일(carousel)
    getLatestCustomCocktails: (state) => state.latestCustomCocktails,
    getAllLatestCustomCocktails: (state) => state.allLatestCustomCocktails,
    getAllLatestCustomCocktailPage: (state) =>
      state.allLatestCustomCocktailPage,
    // * 주류주아 HOT 칵테일(carousel)
    getHotCocktails: (state) => state.hotCocktails,
    getAllHotCocktails: (state) => state.allHotCocktails,
    getAllHotCocktailPage: (state) => state.allHotCocktailPage,
    // * 유저들의 이번 주 커스텀 칵테일(carousel)
    getWeeklyHotCocktails: (state) => state.weeklyHotCocktails,
    getAllWeeklyHotCocktails: (state) => state.allWeeklyHotCocktails,
    getAllWeeklyHotCocktailPage: (state) => state.allWeeklyHotCocktailPage,
  },

  mutations: {
    // * 급상승 검색어
    SET_HOT_KEYWORDS: (state, hotKeywords) => {
      state.hotKeywords = hotKeywords;
    },
    SET_HOT_KEYWORD_DELTA: (state, delta) => {
      state.hotKeywordDelta = delta;
    },
    SET_HOT_KEYWORD_DATE: (state, date) => {
      state.hotKeywordDate = date;
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
    // * 주류주아 HOT 칵테일(carousel)
    SET_HOT_COCKTAILS: (state, hotCocktails: CarouselCocktail[]) => {
      state.hotCocktails = hotCocktails;
    },
    SET_ALL_HOT_COCKTAILS: (state, newHotCocktails: CarouselCocktail[]) => {
      newHotCocktails.forEach((newHotCocktail) => {
        state.allHotCocktails.push(newHotCocktail);
      });
    },
    SET_ALL_HOT_COCKTAIL_PAGE: (state, value) => {
      state.allHotCocktailPage = value;
    },
    REMOVE_ALL_HOT_COCKTAILS: (state) => {
      state.allHotCocktails = [];
      state.allHotCocktailPage = 0;
    },
    // * 유저들의 이번 주 커스텀 칵테일(carousel)
    SET_WEEKLY_HOT_COCKTAILS: (
      state,
      weeklyHotCocktails: CarouselCocktail[]
    ) => {
      state.weeklyHotCocktails = weeklyHotCocktails;
    },
    SET_ALL_WEEKLY_HOT_COCKTAILS: (
      state,
      newWeeklyHotCocktails: CarouselCocktail[]
    ) => {
      newWeeklyHotCocktails.forEach((newWeeklyHotCocktail) => {
        state.allWeeklyHotCocktails.push(newWeeklyHotCocktail);
      });
    },
    SET_ALL_WEEKLY_HOT_COCKTAIL_PAGE: (state, value) => {
      state.allWeeklyHotCocktailPage = value;
    },
    REMOVE_ALL_WEEKLY_HOT_COCKTAILS: (state) => {
      state.allWeeklyHotCocktails = [];
      state.allWeeklyHotCocktailPage = 0;
    },
  },
  actions: {
    // * 급상승 검색어
    setHotKeywords: ({ commit, dispatch, getters }) => {
      axios({
        url: api.lookups.hotKeyword(),
        method: "GET",
      })
        .then((res) => {
          commit("SET_HOT_KEYWORDS", res.data);
        })
        .then(() => {
          // delta 계산 및 등록
          const rawDelta = getters["getHotKeywords"].delta;
          const delta = rawDelta.map((d: number) => {
            if (d > 0 || d === -5) {
              return 1;
            } else if (0 > d && d > -5) {
              return -1;
            }
            return 0;
          });
          commit("SET_HOT_KEYWORD_DELTA", delta);
        })
        .then(() => {
          // date 정리 및 등록
          const rawDate = getters["getHotKeywords"].to;
          const date = rawDate.split("-").splice(1, 2).join(".");
          commit("SET_HOT_KEYWORD_DATE", date);
        })
        .catch((err) => {
          console.error(err.response);
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
          router.push({
            name: "TheSuperCustomCocktailDescView",
            params: { feedId: item.cocktailId },
          });
          break;
        // 커스텀칵테일
        case 0:
          router.push({
            name: "TheCustomCocktailDescView",
            params: {
              cocktailId: item.baseCocktailId,
              feedId: item.cocktailId,
            },
          });
          break;
        case -1:
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
    removeAllLatestCustomCocktails: ({ commit }) => {
      commit("REMOVE_ALL_LATEST_CUSTOM_COCKTAILS");
    },

    // * 주류주아 HOT 칵테일(carousel)
    setHotCocktails: ({ commit, dispatch }) => {
      axios({
        url: api.homes.hotCocktail(),
        method: "GET",
      })
        .then((res) => {
          commit("SET_HOT_COCKTAILS", res.data);
        })
        .catch((err) => {
          dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          console.error(err.response);
        });
    },
    setAllHotCocktails: ({ commit, dispatch, getters }) => {
      const page = getters["getAllHotCocktailPage"];
      axios({
        url: api.homes.hotCocktailView(),
        method: "GET",
        params: {
          page,
        },
      })
        .then((res) => {
          commit("SET_ALL_HOT_COCKTAILS", res.data);
          commit("SET_ALL_HOT_COCKTAIL_PAGE", page + 1);
        })
        .catch((err) => {
          dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          console.error(err.response);
        });
    },
    removeHotCocktails: ({ commit }) => {
      commit("REMOVE_ALL_HOT_COCKTAILS");
    },

    // * 유저들의 이번 주 커스텀 칵테일(carousel)
    setWeeklyHotCocktails: ({ commit, dispatch }) => {
      axios({
        url: api.homes.weeklyHotCocktail(),
        method: "GET",
      })
        .then((res) => {
          commit("SET_WEEKLY_HOT_COCKTAILS", res.data);
        })
        .catch((err) => {
          dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          console.error(err.response);
        });
    },
    setAllWeeklyHotCocktails: ({ commit, dispatch, getters }) => {
      const page = getters["getAllWeeklyHotCocktailPage"];
      axios({
        url: api.homes.weeklyHotCocktailView(),
        method: "GET",
        params: {
          page,
        },
      })
        .then((res) => {
          commit("SET_ALL_WEEKLY_HOT_COCKTAILS", res.data);
          commit("SET_ALL_WEEKLY_HOT_COCKTAIL_PAGE", page + 1);
        })
        .catch((err) => {
          dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          console.error(err.response);
        });
    },
    removeAllWeeklyHotCocktails: ({ commit }) => {
      commit("REMOVE_ALL_WEEKLY_HOT_COCKTAILS");
    },
  },
};
