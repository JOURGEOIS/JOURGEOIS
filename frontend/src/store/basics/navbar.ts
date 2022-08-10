import axios from "axios";
import { Module } from "vuex";
import api from "../../api/api";
import { RootState } from "../index";

export interface NavbarState {
  deviceType: string;
  navIconStatus: boolean[];
}

export const navbar: Module<NavbarState, RootState> = {
  namespaced: true,

  state: {
    deviceType: "",
    navIconStatus: [false, false, false, false, false],
  },

  getters: {
    getDeviceType: (state) => {
      return state.deviceType;
    },

    getNavIconStatus: (state) => {
      return state.navIconStatus;
    },
  },

  mutations: {
    SET_DEVICE_TYPE: (state, deviceType: string) => {
      state.deviceType = deviceType;
    },

    SET_NAV_ICON_STATUS: (state, selected: boolean[]) => {
      state.navIconStatus = selected;
    },
  },

  actions: {
    setDeviceType: ({ commit }) => {
      const varUA = window.navigator.userAgent.toLowerCase();
      let deviceType = "";
      const arr: string[] = ["android", "iphone", "ipad"];
      arr.forEach((type) => {
        if (varUA.indexOf(type) > -1) {
          deviceType = type;
        }
      });
      if (!deviceType) {
        deviceType = "other";
      }
      commit("SET_DEVICE_TYPE", deviceType);
    },

    setNavIconStatus: ({ commit }, index: number) => {
      const selected = [false, false, false, false, false];
      selected[index] = true;
      commit("SET_NAV_ICON_STATUS", selected);
    },
  },
};
