import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
import { CarouselCocktail } from "../../interface";

export interface CarouselState {
  latestCustomCocktails: CarouselCocktail[];
  allLatestCustomCocktails: CarouselCocktail[];
  allLatestCustomCocktailPage: number;
}

export const carousel: Module<CarouselState, RootState> = {
  namespaced: true,

  state: {
    latestCustomCocktails: [],
    allLatestCustomCocktails: [],
    allLatestCustomCocktailPage: 0,
  },

  getters: {
    // * 신규 커스텀 칵테일(carousel)
    getLatestCustomCocktails: (state) => state.latestCustomCocktails,
    getAllLatestCustomCocktails: (state) => state.allLatestCustomCocktails,
    getAllLatestCustomCocktailPage: (state) =>
      state.allLatestCustomCocktailPage,
  },

  mutations: {
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
    // * 신규 커스텀 칵테일(carousel)
    setLatestCustomCocktails: ({ commit, dispatch }) => {
      axios({
        url: api.custom.latestCustomCocktail(),
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
        url: api.custom.latestCustomCocktailView(),
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
