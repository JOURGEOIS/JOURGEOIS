import axios from 'axios'
import api from '../../api/api'
import { Module } from 'vuex'
import { RootState } from '../index'

export interface cocktailReviewData {
  [key: string]: string | number | number[] | null
}

export interface CocktailReviewState {
  currentCocktailReview: cocktailReviewData
  reviewCocktailPage: number
}

export const cocktailReview: Module<CocktailReviewState, RootState> = {
  namespaced: true,

  state: {
    currentCocktailReview: {},
    reviewCocktailPage: 0
  },
  getters: {
    getCurrentCocktailReview: (state) => {
      return state.currentCocktailReview
    },
    getReviewCocktailPage: (state) => state.reviewCocktailPage,

  },
  mutations: {
    SET_CURRENT_COCKTAIL_REVIEW: (state, value: cocktailReviewData) => {
      state.currentCocktailReview = value
    },
    SET_REVIEW_COCKTAIL_PAGE: (state, value) => {
      state.reviewCocktailPage = value;
    },
  },

  actions: {
    getCocktailReview: ({ commit, state }, cocktailId: number) => {
      axios({
        url: api.cocktail.cocktailReview(),
        method: "GET",
        params: {
          cocktailId,
          page: state.reviewCocktailPage,
        }
      })
        .then((res) => {
          commit("SET_REVIEW_COCKTAIL_PAGE", state.reviewCocktailPage + 1);
          // 최대 10개 리뷰 정보 리스트에 추가
          commit("SET_CURRENT_COCKTAIL_REVIEW", res.data);
          console.log('보냄')

        })
        .catch((err) => {
          console.log(err.response)
          console.log('에러')
        })
    },
    createCocktailReview: ({ commit, rootGetters }, { cocktailId, comment }) => {
      const userID = rootGetters["personalInfo/getUserInfoUserId"]
      const reviewData = { userID, cocktailId, comment }
      console.log(reviewData.userID)
      console.log(reviewData.cocktailId)
      console.log(reviewData.comment)
      axios({
        url: api.cocktail.cocktailReview(),
        method: "POST",
        headers: {

        },
        data: reviewData
      })
        .then((res) => {
          commit("SET_CURRENT_COCKTAIL_REVIEW", res.data)
          console.log(res.data)
          console.log('보냄')
        })
        .catch((err) => {
          console.log(err.res)
          console.log('에러')
        })
    },
    updateCocktailReview: ({ commit }, {commentId, comment}) => {
      axios({
        url: api.cocktail.cocktailReview(),
        method: "PUT",
        headers: {

        },
        data: {
          commentId,
          comment
        }
      })
        .then
    }
  }
}