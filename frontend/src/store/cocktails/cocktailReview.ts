import axios from 'axios'
import api from '../../api/api'
import { Module } from 'vuex'
import { RootState } from '../index'

export interface cocktailReviewData {
  [key: string]: string | number | number[] | null
}

export interface CocktailReviewState {
  currentCocktailReview: cocktailReviewData[]
  reviewCocktailPage: number
}

export const cocktailReview: Module<CocktailReviewState, RootState> = {
  namespaced: true,

  state: {
    currentCocktailReview: [],
    reviewCocktailPage: 0,
  },
  getters: {
    getCurrentCocktailReview: (state) => {
      return state.currentCocktailReview
    },
    getReviewCocktailPage: (state) => state.reviewCocktailPage,
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
  },

  actions: {
    getCocktailReview: ({ commit, getters }, cocktailId: number) => {
      console.log('d')
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
          console.log('받음')
        })
        .catch((err) => {
          console.log(err.response)
          console.log('에러')
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
          console.log('보냄')
          commit('RESET_CURRENT_COCKTAIL_REVIEW')
          commit('SET_REVIEW_COCKTAIL_PAGE', 0)
          dispatch('getCocktailReview', cocktailId)
        })
        .catch((err) => {
          console.log(err.res)
          console.log('에러')
        })
    },
    updateCocktailReview: ({ commit }, { commentId, comment }) => {
      axios({
        url: api.cocktail.cocktailReview(),
        method: 'PUT',
        headers: {},
        data: {
          commentId,
          comment,
        },
      })
        .then((res) => {
          commit('SET_CURRENT_COCKTAIL_REVIEW', res.data)
          console.log(res.data)
          console.log('보냄')
        })
        .catch((err) => {
          console.log(err.res)
          console.log('에러')
        })
    },
    deleteCocktailReview: ({ commit }, { userId, commentId }) => {
      if (confirm('정말 삭제하시겠습니까?')) {
        axios({
          url: api.cocktail.cocktailReview(),
          method: 'DELETE',
          headers: {},
          data: {},
        })
          .then((res) => {
            commit('SET_CURRENT_COCKTAIL_REVIEW', res.data)
            console.log('삭제')
          })
          .catch((err) => {
            console.log(err.res)
            console.log('에러')
          })
      }
    },
  },
}
