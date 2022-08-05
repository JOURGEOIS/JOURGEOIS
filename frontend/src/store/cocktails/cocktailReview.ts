import axios from 'axios'
import api from '../../api/api'
import { Module } from 'vuex'
import { RootState } from '../index'
import { validate } from 'json-schema'

export interface cocktailReviewData {
  [key: string]: string | number | number[] | null
}

export interface CocktailReviewState {
  currentCocktailReview: cocktailReviewData[]
  reviewCocktailPage: number
  deleteModalStatus: boolean
  reviewChangeSuccess: boolean
}

export const cocktailReview: Module<CocktailReviewState, RootState> = {
  namespaced: true,

  state: {
    currentCocktailReview: [],
    reviewCocktailPage: 0,
    deleteModalStatus: false,
    reviewChangeSuccess: false,
  },
  getters: {
    getCurrentCocktailReview: (state) => {
      return state.currentCocktailReview
    },
    getReviewCocktailPage: (state) => state.reviewCocktailPage,
    getDeleteModalStatus: (state) => {
      return state.deleteModalStatus
    },
    getReviewChangeSuccess: (state) => {
      return state.reviewChangeSuccess
    },
  },
  mutations: {
    SET_CURRENT_COCKTAIL_REVIEW: (state, value: cocktailReviewData[]) => {
      state.currentCocktailReview.push(...value)
    },
    SET_REVIEW_COCKTAIL_PAGE: (state, value) => {
      state.reviewCocktailPage = value
    },
    RESET_CURRENT_COCKTAIL_REVIEW: (state) => {
      state.currentCocktailReview = []
    },
    SET_DELETE_MODAL: (state, value) => {
      state.deleteModalStatus = value
    },
    SET_REVIEW_SUCCESS: (state, value) => {
      state.reviewChangeSuccess = value
    },
  },

  actions: {
    getCocktailReview: ({ commit, getters }, cocktailId: number) => {
      axios({
        url: api.cocktail.cocktailReview(),
        method: 'GET',
        params: {
          cocktailId,
          page: getters.getReviewCocktailPage,
        },
      })
        .then((res) => {
          console.log('data: ', res.data)
          commit('SET_REVIEW_COCKTAIL_PAGE', getters.reviewCocktailPage + 1)
          // 최대 10개 리뷰 정보 리스트에 추가
          commit('SET_CURRENT_COCKTAIL_REVIEW', res.data)
        })
        .catch((err) => {
          console.log(err.response)
        })
    },
    createCocktailReview: (
      { commit, dispatch, rootGetters, getters },
      { cocktailId, comment },
    ) => {
      const userId = rootGetters['personalInfo/getUserInfoUserId']
      const reviewData = { userId, cocktailId, comment }
      console.log(reviewData.userId)
      console.log(reviewData.cocktailId)
      console.log(reviewData.comment)
      axios({
        url: api.cocktail.cocktailReview(),
        method: 'POST',
        headers: {},
        data: reviewData,
      })
        .then((res) => {
          console.log(res.data)
          commit('RESET_CURRENT_COCKTAIL_REVIEW')
          commit('SET_REVIEW_COCKTAIL_PAGE', 0)
          dispatch('getCocktailReview', cocktailId)
        })
        .catch((err) => {
          console.log(err.res)
        })
    },
    updateCocktailReview: (
      { commit, dispatch },
      { cocktailId, commentId, comment },
    ) => {
      const editData = { commentId, comment }
      console.log(editData.commentId)
      console.log(editData.comment)
      axios({
        url: api.cocktail.cocktailReview(),
        method: 'PUT',
        headers: {},
        data: editData,
      })
        .then((res) => {
          console.log('수정')
          console.log(res.data)
          commit('RESET_CURRENT_COCKTAIL_REVIEW')
          commit('SET_REVIEW_COCKTAIL_PAGE', 0)
          dispatch('getCocktailReview', cocktailId)
          commit('SET_REVIEW_SUCCESS', true)
        })
        .catch((err) => {
          console.log(err.res)
          console.log('에러')
        })
    },
    deleteCocktailReview: (
      { commit, dispatch, rootGetters },
      { cocktailId, commentId },
    ) => {
      const userId = rootGetters['personalInfo/getUserInfoUserId']
      const deleteData = { userId, commentId }
      console.log(deleteData.userId)
      console.log(deleteData.commentId)
      axios({
        url: api.cocktail.cocktailReview(),
        method: 'DELETE',
        headers: {},
        data: deleteData,
      })
        .then((res) => {
          console.log('삭제')
          commit('RESET_CURRENT_COCKTAIL_REVIEW')
          commit('SET_REVIEW_COCKTAIL_PAGE', 0)
          dispatch('getCocktailReview', cocktailId)
        })
        .catch((err) => {
          console.log(err.res)
          console.log(err.data)
          console.log('에러')
        })
    },
    toggleDeleteModal: ({ commit }, value: boolean) => {
      commit('SET_DELETE_MODAL', value)
    },
    toggleReviewChangeSuccess: ({ commit }, value) => {
      commit('SET_REVIEW_SUCCESS', value)
    },
  },
}
