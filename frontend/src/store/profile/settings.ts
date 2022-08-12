import { Module } from 'vuex'
import { RootState } from '../index'
import axios from 'axios'
import api from '../../api/api'

export interface SettingsState {
  // * (+) 팝업 모달
  settingsModalStatus: boolean;
  settingsModalClass: string;
}

export const settings: Module<SettingsState, RootState> = {
  namespaced: true,

  state: {
    // * (+) 팝업 모달
    settingsModalStatus: false,
    settingsModalClass: "",
  },

  getters: {
    // * (+) 팝업 모달
    getSettingsModalStatus: (state) => state.settingsModalStatus,
    getSettingsModalClass: (state) => state.settingsModalClass,
    
  },

  mutations: {
    // * (+) 팝업 모달
    SET_SETTINGS_MODAL_STATUS: (state, value: boolean) => {
      state.settingsModalStatus = value;
    },
    SET_SETTINGS_MODAL_CLASS: (state, value: string) => {
      state.settingsModalClass = value;
    },
  },

  actions: {
    // * (+) 팝업 모달
    toggleSettingsModal: ({ commit }, value: boolean) => {
      commit("SET_SETTINGS_MODAL_STATUS", value);
    },
    changeSettingsModalClass: ({ commit }, value: string) => {
      commit("SET_SETTINGS_MODAL_CLASS", value);
    },
  }
}