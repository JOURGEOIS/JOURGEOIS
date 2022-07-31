import axios from "axios";
import api from "../../api/api";
import { Module } from "vuex";
import { RootState } from "../index";

export interface cocktailData {
  [key: string]: string | string[];
}

export interface CocktailDescState {
  currentTab: number;
  currentCocktailData: cocktailData;
}

export const cocktailDesc: Module<CocktailDescState, RootState> = {
  namespaced: true,

  state: {
    currentTab: 0,
    currentCocktailData: {},
  },
  getters: {
    getCurrentTab: (state) => {
      return state.currentTab;
    },
    getCurrentCocktailData: (state) => {
      return state.currentCocktailData;
    },
    getCurrentCocktailDataIngredients: (state) => {
      return state.currentCocktailData.materials;
    },
    getCurrentCocktailDataRecipe: (state) => {
      return state.currentCocktailData.recipe
    }

  },
  mutations: {
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },
    SET_CURRENT_COCKTAIL_DATA: (state, value: cocktailData) => {
      state.currentCocktailData = value;
    },

  },
  actions: {
    changeCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },
    getCocktailDb: ({ commit }, id: number) => {
      axios({
        url: api.cocktail.getCocktailData(),
        method: "get",
        params: {
          id,
        },
      })
        .then((response) => {
          commit("SET_CURRENT_COCKTAIL_DATA", response.data);
        })
        .catch((error) => {
          console.error(error.response);
        });
    },
  },
};
