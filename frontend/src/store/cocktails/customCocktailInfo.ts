import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
import { CustomCocktail } from "../../interface";

// ! main State
export interface CustomCocktailInfoState {
  customCocktails: CustomCocktail[];
  customCocktailPage: number;
  customCocktailDetail: CustomCocktail;
  customCocktailDetailDefault: CustomCocktail;
}

export const customCocktailInfo: Module<CustomCocktailInfoState, RootState> = {
  namespaced: true,

  state: {
    // * 특정 칵테일의 커스텀 칵테일 목록
    customCocktails: [],
    customCocktailPage: 0,
    customCocktailDetail: {
      customCocktail: {
        uid: null,
        img: null,
        ilike: false,
        type: null,
        postId: 0,
        imgLink: "",
        description: "",
        createTime: [0],
        lastUpdateTime: [0],
        isUpdated: 0,
        like: 0,
        title: "",
        baseCocktail: 0,
        baseCocktailName: "",
        ingredients: [],
        recipe: "",
      },
      followerDTO: {
        uid: 0,
        nickname: "",
        introduce: "",
        profileImg: "",
        isFollowed: -2,
      },
    },
    customCocktailDetailDefault: {
      customCocktail: {
        uid: null,
        img: null,
        ilike: false,
        type: null,
        postId: 0,
        imgLink: "",
        description: "",
        createTime: [0],
        lastUpdateTime: [0],
        isUpdated: 0,
        like: 0,
        title: "",
        baseCocktail: 0,
        baseCocktailName: "",
        ingredients: [],
        recipe: "",
      },
      followerDTO: {
        uid: 0,
        nickname: "",
        introduce: "",
        profileImg: "",
        isFollowed: -2,
      },
    },
  },

  getters: {
    // * 특정 칵테일의 커스텀 칵테일 목록
    getCustomCocktails: (state) => state.customCocktails,
    getCustomCocktailPage: (state) => state.customCocktailPage,
    getCustomCocktailDetail: (state) => state.customCocktailDetail,
    getCustomCocktailDetailDefault: (state) =>
      state.customCocktailDetailDefault,
  },

  mutations: {
    // * 특정 칵테일의 커스텀 칵테일 목록
    SET_CUSTOM_COCKTAILS: (state, newCustomCocktails: CustomCocktail[]) => {
      newCustomCocktails.forEach((newCustomCocktail) => {
        state.customCocktails.push(newCustomCocktail);
      });
    },
    SET_CUSTOM_COCKTAIL_PAGE: (state, value) => {
      state.customCocktailPage = value;
    },
    REMOVE_CUSTOM_COCKTAILS: (state) => {
      state.customCocktails = [];
    },
    SET_CUSTOM_COCKTAIL_DETAIL: (state, newCustomCocktail: CustomCocktail) => {
      state.customCocktailDetail = newCustomCocktail;
    },
    // * state에 커스텀칵테일 정보 제거
    REMOVE_CUSTOM_COCKTAIL_DETAIL: (state) => {
      state.customCocktailDetail = state.customCocktailDetailDefault;
    },
  },

  actions: {
    // * 특정 칵테일의 커스텀 칵테일 목록
    setCustomCocktails: ({ commit, getters }, data) => {
      const { originalCocktailId, asc } = data;

      axios({
        url: api.cocktail.customCocktailList(),
        method: "GET",
        params: {
          id: originalCocktailId,
          asc,
          page: getters["getCustomCocktailPage"],
        },
      })
        .then((res) => {
          const newCustomCocktails = res.data;
          commit("SET_CUSTOM_COCKTAILS", newCustomCocktails);
          const page = getters.getCustomCocktailPage;
          commit("SET_CUSTOM_COCKTAIL_PAGE", page + 1);
        })
        .catch((err) => console.error(err));
    },
    removeCustomCocktails: ({ commit }) => {
      commit("REMOVE_CUSTOM_COCKTAILS");
      commit("SET_CUSTOM_COCKTAIL_PAGE", 0);
    },

    // * 커스텀 칵테일 세부 페이지 정보
    setCustomCocktailDetail: (
      { commit, rootGetters, dispatch },
      params: { feedId: number }
    ) => {
      axios({
        url: api.post.postCocktail(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          p_id: params.feedId,
        },
      })
        .then((res) => {
          commit("SET_CUSTOM_COCKTAIL_DETAIL", res.data);
        })
        .catch((err) => {
          if (err.response.status !== 401) {
            console.error(err.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "customCocktailInfo/setCustomCocktailDetail",
              params,
            };
            dispatch("personalInfo/requestRefreshToken", obj, {
              root: true,
            });
          }
        });
    },
    // * state에 커스텀칵테일 정보 제거
    removeCustomCocktailDetail: ({ commit }) => {
      commit("REMOVE_CUSTOM_COCKTAIL_DETAIL");
    },

    // * 커스텀칵테일 게시물 제거
    removeCustomCocktailPost: ({ dispatch, rootGetters }, params) => {
      const { postId } = params;
      axios({
        url: api.post.postCocktail(),
        method: "DELETE",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          postId,
        },
      })
        .then((res) => {
          // 삭제 성공
          if (res.data.success) {
            alert("삭제 성공");
          }
        })
        .catch((err) => {
          if (err.response.status !== 401) {
            alert("에러 떴다!");
            console.error(err.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "customCocktailInfo/removeCustomCocktailPost",
              params,
            };
            dispatch("personalInfo/requestRefreshToken", obj, {
              root: true,
            });
          }
        });
    },
  },
};
