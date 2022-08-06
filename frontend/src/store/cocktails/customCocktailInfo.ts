import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
import { CustomCocktail } from "../../interface";

// ! main State
export interface CustomCocktailInfoState {
  customCocktails: CustomCocktail[];
  customCocktailPage: number;
}

export const customCocktailInfo: Module<CustomCocktailInfoState, RootState> = {
  namespaced: true,

  state: {
    // * 특정 칵테일의 커스텀 칵테일 목록
    customCocktails: [],
    customCocktailPage: 0,
  },

  getters: {
    // * 특정 칵테일의 커스텀 칵테일 목록
    getCustomCocktails: (state) => state.customCocktails,
    getCustomCocktailPage: (state) => state.customCocktailPage,
  },

  mutations: {
    // * 특정 칵테일의 커스텀 칵테일 목록
    SET_CUSTOM_COCKTAILS: (state, newCustomCocktails: CustomCocktail[]) => {
      newCustomCocktails.forEach((newCustomCocktail) => {
        state.customCocktails.push(newCustomCocktail);
      });
    },
    SET_CUSTOM_COCKTAIL_PAGE: (state, value) => {
      state.customCocktailPage = value;
    },
    REMOVE_CUSTOM_COCKTAILS: (state) => {
      state.customCocktails = [];
    },
  },

  actions: {
    // * 특정 칵테일의 커스텀 칵테일 목록
    setCustomCocktails: ({ commit, getters }, data) => {
      const { originalCocktailId, asc } = data;

      axios({
        url: api.cocktail.customCocktailList(),
        method: "GET",
        params: {
          id: originalCocktailId,
          asc,
          page: getters["getCustomCocktailPage"],
        },
      })
        .then((res) => {
          const newCustomCocktails = res.data;
          commit("SET_CUSTOM_COCKTAILS", newCustomCocktails);
          const page = getters.getCustomCocktailPage;
          commit("SET_CUSTOM_COCKTAIL_PAGE", page + 1);
        })
        .catch((err) => console.error(err));
    },
    removeCustomCocktails: ({ commit }) => {
      commit("REMOVE_CUSTOM_COCKTAILS");
      commit("SET_CUSTOM_COCKTAIL_PAGE", 0);
    },
  },
};
