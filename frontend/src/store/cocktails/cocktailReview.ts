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
  deleteReviewId: number;
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
    deleteReviewId: 0,

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
    getDeleteReviewId: (state) => state.deleteReviewId,
    
    // 모달
    getDeleteModalStatus: (state) => {
      return state.deleteModalStatus;
    },
    getReviewChangeSuccess: (state) => {
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
    SET_DELETE_REVIEW_ID: (state, value: number) => {
      state.deleteReviewId = value
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

    setDeleteReviewId: ({ commit }, value: number) => {
      commit("SET_DELETE_REVIEW_ID", value)
    },

    // 후기 불러오기
    getCocktailReview: ({ commit, dispatch, getters, rootGetters },
      cocktailId: number) => {
      axios({
        url: api.cocktail.cocktailReviewData(),
        method: "GET",
        params: {
          cocktailId,
          page: getters.getReviewCocktailPage,
        },
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
      })
        .then((res) => {
          console.log("data: ", res.data);
          commit("SET_CURRENT_COCKTAIL_REVIEW", res.data);
          const page = getters.getReviewCocktailPage;
          commit("SET_REVIEW_COCKTAIL_PAGE", page + 1);
        })
        .catch((err) => {
          console.error(err.response);
          // 실패 팝업
          dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
        });
    },
    // 후기 생성
    createCocktailReview: (
      { commit, dispatch, rootGetters },
      { cocktailId, comment }
    ) => {
      const userId = rootGetters["personalInfo/getUserInfoUserId"];
      const reviewData = { userId, cocktailId, comment };

      axios({
        url: api.cocktail.cocktailReview(),
        method: "POST",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: reviewData,
      })
        .then((res) => {
          dispatch("resetCocktailReview");
          dispatch("getCocktailReview", cocktailId);
        })
        .catch((err) => {
          if (err.response.status !== 401) {
            // 실패 팝업
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
            console.error(err.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "cocktailReview/createCocktailReview",
              params: { cocktailId, comment },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
    // 후기 수정
    updateCocktailReview: (
      { commit, dispatch, rootGetters },
      { cocktailId, commentId, comment }
    ) => {
      const editData = { commentId, comment };
      axios({
        url: api.cocktail.cocktailReview(),
        method: "PUT",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: editData,
      })
        .then((res) => {
          commit("SET_REVIEW_SUCCESS", true);
          dispatch("resetCocktailReview");
          dispatch("getCocktailReview", cocktailId);
        })
        .catch((err) => {
          if (err.response.status !== 401) {
            // 실패 팝업
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
            console.error(err.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "cocktailReview/updateCocktailReview",
              params: editData,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
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
      console.log(cocktailId)
      console.log(commentId);
      axios({
        url: api.cocktail.cocktailReview(),
        method: "DELETE",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: deleteData,
      })
        .then((res) => {
          console.log("삭제");
          dispatch("resetCocktailReview");
          dispatch("getCocktailReview", cocktailId);
        })
        .catch((err) => {
          console.error(err.response);
          console.log("에러");
          if (err.response.status !== 401) {
            // 실패 팝업
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
            console.error(err.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "cocktailReview/deleteCocktailReview",
              params: deleteData,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
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
