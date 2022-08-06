import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";

export interface ShareState {
  shareModalStatus: boolean;
  shareModalClass: string;
}

export const share: Module<ShareState, RootState> = {
  namespaced: true,

  state: {
    // * 공유 모달
    shareModalStatus: false,
    shareModalClass: "",
  },

  getters: {
    // * 공유 모달
    getShareModalStatus: (state) => state.shareModalStatus,
    getShareModalClass: (state) => state.shareModalClass,
  },

  mutations: {
    // * 공유 모달
    SET_SHARE_MODAL_STATUS: (state, value: boolean) => {
      state.shareModalStatus = value;
    },
    SET_SHARE_MODAL_CLASS: (state, value: string) => {
      state.shareModalClass = value;
    },
  },

  actions: {
    // * 공유 모달
    toggleShareModal: ({ commit }, value: boolean) => {
      commit("SET_SHARE_MODAL_STATUS", value);
    },
    changeShareModalClass: ({ commit }, value: string) => {
      commit("SET_SHARE_MODAL_CLASS", value);
    },
  },
};
