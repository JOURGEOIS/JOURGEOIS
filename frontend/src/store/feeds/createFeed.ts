import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";

export interface CreateFeedState {
  // * (+) 팝업 모달
  createFeedModalStatus: boolean;
  createFeedModalClass: string;
}

export const createFeed: Module<CreateFeedState, RootState> = {
  namespaced: true,

  state: {
    // * (+) 팝업 모달
    createFeedModalStatus: false,
    createFeedModalClass: "",
  },

  getters: {
    // * (+) 팝업 모달
    getCreateFeedModalStatus: (state) => state.createFeedModalStatus,
    getCreateFeedModalClass: (state) => state.createFeedModalClass,
  },

  mutations: {
    // * (+) 팝업 모달
    SET_CREATE_FEED_MODAL_STATUS: (state, value: boolean) => {
      state.createFeedModalStatus = value;
    },
    SET_CREATE_FEED_MODAL_CLASS: (state, value: string) => {
      state.createFeedModalClass = value;
    },
  },

  actions: {
    // * (+) 팝업 모달
    toggleCreateFeedModal: ({ commit }, value: boolean) => {
      commit("SET_CREATE_FEED_MODAL_STATUS", value);
    },
    changeCreateFeedModalClass: ({ commit }, value: string) => {
      commit("SET_CREATE_FEED_MODAL_CLASS", value);
    },
  },
};
