import axios from "axios";
import { Module } from "vuex";
import api from "../../api/api";
import { RootState } from "../index";

export interface ScrollState {
  debounce: any;
}

export const scroll: Module<ScrollState, RootState> = {
  namespaced: true,

  state: {
    debounce: 0,
  },

  getters: {
    // isLoggedIn: (state) => {
    //   return !!state.accessToken;
    // },
  },

  mutations: {
    // SET_ACCESS_TOKEN: (state, token) => {
    //   state.accessToken = token;
    // },
  },

  actions: {
    // * 내려오면 API 호출하여 아래에 더 추가, total값 최대면 호출X
    handleScroll: ({ state, dispatch }, data: any) => {
      if (state.debounce) {
        clearTimeout(state.debounce);
      }
      state.debounce = setTimeout(() => {
        const { scrollHeight, scrollTop, clientHeight } =
          data.event.target.scrollingElement;
        const isAtTheBottom =
          scrollHeight < scrollTop + clientHeight + 10 &&
          scrollHeight > scrollTop + clientHeight - 10;
        if (isAtTheBottom) {
          dispatch(data.action, {}, { root: true });
        }
      }, 100);
    },

    // * 스크롤을 맨 위로 올리고 싶을 때(부드럽게)
    goToTop: ({ commit }) => {
      window.scroll({ behavior: "smooth", top: 0 });
    },
  },
};
