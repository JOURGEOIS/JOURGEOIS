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

export interface SearchResultState {
  currentTab: number;
}

export const searchResult: Module<SearchResultState, RootState> = {
  namespaced: true,

  state: {
    currentTab: 0,
  },

  getters: {
    // * 현재 탭 번호
    getCurrentTab: (state) => {
      return state.currentTab;
    },
  },

  mutations: {
    // * 현재 탭 번호 저장
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },
  },

  actions: {
    // * 검색어 string 저장
    setCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },
  },
};
