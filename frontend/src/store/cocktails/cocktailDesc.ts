import { Module } from "vuex";
import { RootState } from "../index";

export interface CocktailDescState {
  currentTab: number;
}

export const cocktailDesc: Module<CocktailDescState, RootState> = {
  namespaced: true,

  state: {
    currentTab: 0,
  },
  getters: {
    getCurrentTab: (state) => {
      return state.currentTab;
    },
  },
  mutations: {
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },
  },
  actions: {
    changeCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },
  },
};
