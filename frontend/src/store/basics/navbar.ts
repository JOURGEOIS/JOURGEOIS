import axios from "axios";
import { Module } from "vuex";
import api from "../../api/api";
import { RootState } from "../index";

export interface NavbarState {
  navIconStatus: boolean[];
}

export const navbar: Module<NavbarState, RootState> = {
  namespaced: true,

  state: {
    navIconStatus: [false, false, false, false, false],
  },

  getters: {
    getNavIconStatus: (state) => {
      return state.navIconStatus;
    },
  },

  mutations: {
    SET_NAV_ICON_STATUS: (state, selected: boolean[]) => {
      state.navIconStatus = selected;
    },
  },

  actions: {
    setNavIconStatus: ({ commit }, index: number) => {
      const selected = [false, false, false, false, false];
      selected[index] = true;
      commit("SET_NAV_ICON_STATUS", selected);
    },
  },
};
