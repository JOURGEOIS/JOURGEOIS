import axios from "axios";
import { Module } from "vuex";
import api from "../../api/api";
import { RootState } from "../index";

export interface ScrollState {
  debounce: any;
  homeScrollY: number;
}

export const scroll: Module<ScrollState, RootState> = {
  namespaced: true,

  state: {
    debounce: 0,
    homeScrollY: 0,
  },

  getters: {
    getHomeScrollY: (state) => state.homeScrollY,
  },

  mutations: {
    SET_HOME_SCROLL_Y: (state, value: number) => {
      state.homeScrollY = value;
    },
  },

  actions: {
    // * 끝까지 내려오면 API 호출하여 아래에 더 추가, total값 최대면 호출X
    // data: {action:string - 스크롤을 통해 API 호출 및 페이지 수 올리는 함수 전역경로}
    // action 함수는 page 수를 올리는 vuex mutation이 있어야 함
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
          dispatch(data.action, data.data, { root: true });
        }
      }, 100);
    },

    // * 스크롤을 맨 위로 올리고 싶을 때(부드럽게)
    goToTop: ({ commit }) => {
      window.scroll({ behavior: "smooth", top: 0 });
    },

    setHomeScrollY: ({ commit }, value: number) => {
      commit("SET_HOME_SCROLL_Y", value);
    },
  },
};
