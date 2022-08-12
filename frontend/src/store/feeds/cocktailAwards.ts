import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
import { ContestCocktail } from "../../interface";
import { checkBadWord } from "../../functions/checkText";

export interface CocktailAwardsState {
  cocktailAwardsList: ContestCocktail[];
  cocktailAwardsDetail: ContestCocktail;
  currentTab: number;
}

export const cocktailAwards: Module<CocktailAwardsState, RootState> = {
  namespaced: true,

  state: {
    cocktailAwardsList: [],
    cocktailAwardsDetail: {
      postId: 0,
      description: "",
      imgLink: "",
      title: "",
      like: 0,
      percentage: "",
    },
    currentTab: 0,
  },

  getters: {
    getCocktailAwardsList: (state) => state.cocktailAwardsList,
    getCocktailAwardsDetail: (state) => state.cocktailAwardsDetail,
    getCurrentTab: (state) => state.currentTab,
  },

  mutations: {
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },
  },

  actions: {
    // 탭 변경
    changeCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },

    // 제출 시, 유효성 검사
    checkCocktailAwardsForm: (
      { dispatch },
      { title, description, img, isAgree }
    ) => {
      // 빈필드 확인
      const imgRequired = !(img instanceof File);
      const titleRequired = !title;

      if (imgRequired || titleRequired) {
        dispatch(
          "modal/changeErrorModalMessage",
          " 이름, 이미지 항목은 필수 입력입니다",
          { root: true }
        );
        dispatch("modal/blinkErrorModalAppStatus", {}, { root: true });
        return;
      }

      // 개인정보 수집
      const isAgreeRequired = !isAgree;
      if (isAgreeRequired) {
        dispatch(
          "modal/changeErrorModalMessage",
          "개인정보 수집에 동의해주세요",
          { root: true }
        );
        dispatch("modal/blinkErrorModalAppStatus", {}, { root: true });
        return;
      }

      // 비속어 검사
      const titleCheck = checkBadWord(title);
      const descriptionCheck = checkBadWord(description);
      if (titleCheck || descriptionCheck) {
        dispatch(
          "modal/changeErrorModalMessage",
          "부적절한 언어가 포함되었습니다",
          { root: true }
        );
        dispatch("modal/blinkErrorModalAppStatus", {}, { root: true });
        return;
      }

      dispatch("submitCocktailAwardsForm", {
        title,
        description,
        img,
        isAgree,
      });
    },

    // 제출
    submitCocktailAwardsForm: ({ rootGetters, dispatch }, data: object) => {
      axios({
        url: api.awards.joinContest(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
          "Content-Type": "multipart/form-data",
        },
        data,
      })
        .then((response) => {
          router.replace({
            name: "TheCocktailAwardsDescView",
            params: {
              feedId: response.data,
            },
          });
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            if (error.response.data.fail) {
              dispatch(
                "modal/changeErrorModalMessage",
                error.response.data.fail,
                { root: true }
              );
            } else {
              dispatch(
                "modal/changeErrorModalMessage",
                "잠시 후에 시도해주세요",
                { root: true }
              );
            }
            dispatch("modal/blinkErrorModalAppStatus", {}, { root: true });
          } else {
            // refreshToken 재발급
            const obj = {
              func: "contest/submitCocktailAwardsForm",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
