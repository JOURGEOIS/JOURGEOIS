import axios from 'axios'
import api from '../../api/api'
import { Module } from 'vuex'
import { RootState } from '../index'

export interface cocktailData {
  [key: string]: string | string[]
}

export interface CocktailDescState {
  currentTab: number
  currentCocktailData: cocktailData
  failPopupStatus: boolean
}

export const cocktailDesc: Module<CocktailDescState, RootState> = {
  namespaced: true,

  state: {
    currentTab: 0,
    currentCocktailData: {},
    failPopupStatus: false,
  },
  getters: {
    getCurrentTab: (state) => {
      return state.currentTab
    },
    getCurrentCocktailData: (state) => {
      return state.currentCocktailData
    },
    getCurrentCocktailDataIngredients: (state) => {
      return state.currentCocktailData.materials
    },
    getFailPopupStatus: (state) => {
      return state.failPopupStatus
    },
    getCurrentCocktailDataRecipe: (state) => {
      return state.currentCocktailData.recipe
    }

  },
  mutations: {
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value
    },
    SET_CURRENT_COCKTAIL_DATA: (state, value: cocktailData) => {
      state.currentCocktailData = value
    },
    SET_FAIL: (state, value) => {
      state.failPopupStatus = value
    },
  },
  actions: {
    changeCurrentTab: ({ commit }, value: number) => {
      commit('SET_CURRENT_TAB', value)
    },
    getCocktailDb: ({ commit }, id: number) => {
      axios({
        url: api.cocktail.getCocktailData(),
        method: 'get',
        params: {
          id,
        },
      })
        .then((response) => {
          console.log(response.data)
          commit('SET_CURRENT_COCKTAIL_DATA', response.data)
        })
        .catch((error) => {
          console.log(error.response)
        })
    },
    toggleFailPopup: ({ commit }, value: boolean) => {
      commit('SET_FAIL', value)
    },
  },
}
