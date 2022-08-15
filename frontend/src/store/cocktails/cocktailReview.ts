import axios from "axios";
import api from "../../api/api";
import { Module } from "vuex";
import { RootState } from "../index";

export interface cocktailReviewData {
  [key: string]: string | number | number[] | null;
}

export interface CocktailReviewState {
  // 현재 칵테일에 달린 리뷰
  currentCocktailReview: cocktailReviewData[];
  reviewCocktailPage: number;
  // 모달
  deleteModalStatus: boolean;
  reviewChangeSuccess: boolean;
}

export const cocktailReview: Module<CocktailReviewState, RootState> = {
  namespaced: true,

  state: {
    // 현재 칵테일에 달린 리뷰
    currentCocktailReview: [],
    reviewCocktailPage: 0,
    // 모달
    deleteModalStatus: false,
    reviewChangeSuccess: false,
  },

  getters: {
    // 현재 칵테일에 달린 리뷰 정보
    getCurrentCocktailReview: (state) => {
      return state.currentCocktailReview;
    },
    getReviewCocktailPage: (state) => state.reviewCocktailPage,
    // 모달
    getDeleteModalStatus: (state) => {
      return state.deleteModalStatus;
    },
    getReviewChangeSuccess: (state) => {
      console.log(state.reviewChangeSuccess);
      return state.reviewChangeSuccess;
    },
  },

  mutations: {
    // 현재 칵테일에 달린 리뷰 저장
    SET_CURRENT_COCKTAIL_REVIEW: (state, newReviews: cocktailReviewData[]) => {
      newReviews.forEach((newReview) => {
        state.currentCocktailReview.push(newReview);
      });
    },
    SET_REVIEW_COCKTAIL_PAGE: (state, value) => {
      state.reviewCocktailPage = value;
    },
    // 리뷰 리스트 리셋
    RESET_CURRENT_COCKTAIL_REVIEW: (state) => {
      state.currentCocktailReview = [];
    },
    // 모달
    SET_DELETE_MODAL: (state, value) => {
      state.deleteModalStatus = value;
    },
    SET_REVIEW_SUCCESS: (state, value) => {
      state.reviewChangeSuccess = value;
    },
  },

  actions: {
    // 후기 리셋
    resetCocktailReview: ({ commit }) => {
      commit("SET_REVIEW_COCKTAIL_PAGE", 0);
      commit("RESET_CURRENT_COCKTAIL_REVIEW");
    },

    // 후기 불러오기
    getCocktailReview: ({ commit, getters }, cocktailId: number) => {
      axios({
        url: api.cocktail.cocktailReview(),
        method: "GET",
        params: {
          cocktailId,
          page: getters.getReviewCocktailPage,
        },
      })
        .then((res) => {
          console.log("data: ", res.data);
          const newCocktailReview = res.data;
          commit("SET_CURRENT_COCKTAIL_REVIEW", newCocktailReview);
          const page = getters.getReviewCocktailPage;
          commit("SET_REVIEW_COCKTAIL_PAGE", page + 1);
        })
        .catch((err) => {
          console.error(err.response);
        });
    },
    // 후기 생성
    createCocktailReview: (
      { commit, dispatch, rootGetters, getters },
      { cocktailId, comment }
    ) => {
      const userId = rootGetters["personalInfo/getUserInfoUserId"];
      const reviewData = { userId, cocktailId, comment };

      axios({
        url: api.cocktail.cocktailReview(),
        method: "POST",
        headers: {},
        data: reviewData,
      })
        .then((res) => {
          console.log(res.data);
          commit("RESET_CURRENT_COCKTAIL_REVIEW");
          commit("SET_REVIEW_COCKTAIL_PAGE", 0);
          dispatch("getCocktailReview", cocktailId);
        })
        .catch((err) => {
          console.error(err.res);
        });
    },
    // 후기 수정
    updateCocktailReview: (
      { commit, dispatch },
      { cocktailId, commentId, comment }
    ) => {
      const editData = { commentId, comment };
      console.log(editData.commentId);
      console.log(editData.comment);
      axios({
        url: api.cocktail.cocktailReview(),
        method: "PUT",
        headers: {},
        data: editData,
      })
        .then((res) => {
          console.log("수정");
          console.log(res.data);
          commit("SET_REVIEW_SUCCESS", true);
          commit("RESET_CURRENT_COCKTAIL_REVIEW");
          commit("SET_REVIEW_COCKTAIL_PAGE", 0);
          dispatch("getCocktailReview", cocktailId);
          console.log("3");
          console.log("4");
        })
        .catch((err) => {
          console.error(err.res);
          console.log("에러");
        });
    },
    // 후기 삭제
    deleteCocktailReview: (
      { commit, dispatch, rootGetters },
      { cocktailId, commentId }
    ) => {
      const userId = rootGetters["personalInfo/getUserInfoUserId"];
      const deleteData = { userId, commentId };
      console.log(deleteData.userId);
      console.log(deleteData.commentId);
      axios({
        url: api.cocktail.cocktailReview(),
        method: "DELETE",
        headers: {},
        data: deleteData,
      })
        .then((res) => {
          console.log("삭제");
          commit("RESET_CURRENT_COCKTAIL_REVIEW");
          commit("SET_REVIEW_COCKTAIL_PAGE", 0);
          dispatch("getCocktailReview", cocktailId);
        })
        .catch((err) => {
          console.error(err.res);
          console.error(err.data);
          console.log("에러");
        });
    },
    toggleDeleteModal: ({ commit }, value: boolean) => {
      commit("SET_DELETE_MODAL", value);
    },
    toggleReviewChangeSuccess: ({ commit }, value) => {
      commit("SET_REVIEW_SUCCESS", value);
    },
  },
};
