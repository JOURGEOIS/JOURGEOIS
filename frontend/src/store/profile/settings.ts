import { Module } from 'vuex'
import { RootState } from '../index'
import axios from 'axios'
import api from '../../api/api'

export interface SettingsState {
  // * (+) 팝업 모달
  settingsModalStatus: boolean;
  settingsModalClass: string;
  // 계정 정보 수정
  privateModeChangeError: boolean,
  privateModeChangeSuccess: boolean,
}

export const settings: Module<SettingsState, RootState> = {
  namespaced: true,

  state: {
    // * (+) 팝업 모달
    settingsModalStatus: false,
    settingsModalClass: "",
    // 계정 정보 수정
    privateModeChangeError: false,
    privateModeChangeSuccess: false,
  },

  getters: {
    // * (+) 팝업 모달
    getSettingsModalStatus: (state) => state.settingsModalStatus,
    getSettingsModalClass: (state) => state.settingsModalClass,
    // 계정 정보 수정
    getPrivateModeChangeError: (state) => state.privateModeChangeError,
    getPrivateModeChangeSuccess: (state) => state.privateModeChangeSuccess,
  },

  mutations: {
    // * (+) 팝업 모달
    SET_SETTINGS_MODAL_STATUS: (state, value: boolean) => {
      state.settingsModalStatus = value;
    },
    SET_SETTINGS_MODAL_CLASS: (state, value: string) => {
      state.settingsModalClass = value;
    },
    // 계정 정보 수정
    SET_PRIVATE_MODE_CHANGE_ERROR: (state, value: boolean) => {
      state.privateModeChangeError = value
    },
    SET_PRIVATE_MODE_CHANGE_SUCCESS: (state, value: boolean) => {
      state.privateModeChangeSuccess = value
    }
  },

  actions: {
    // * (+) 팝업 모달
    toggleSettingsModal: ({ commit }, value: boolean) => {
      commit("SET_SETTINGS_MODAL_STATUS", value);
    },
    changeSettingsModalClass: ({ commit }, value: string) => {
      commit("SET_SETTINGS_MODAL_CLASS", value);
    },
    privateModeChangeError: ({ commit }, value: boolean) => {
      commit("SET_PRIVATE_MODE_CHANGE_ERROR", value)
    },
    privateModeChangeSuccess: ({ commit }, value: boolean) => {
      commit("SET_PRIVATE_MODE_CHANGE_SUCCESS", value)
    }
  }
}