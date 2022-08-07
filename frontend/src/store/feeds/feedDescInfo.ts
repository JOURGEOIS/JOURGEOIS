import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
import { CustomCocktail } from "../../interface";

// ! main State
export interface FeedDescState {
  // 일반게시글 상세정보
  communityDetail: CustomCocktail;
  // 팝업 알림
  alertStatus: boolean;
  // 오류 메시지
  errorMessage: string;
  // 성공 메시지
  successMessage: string;
}

export const feedDescInfo: Module<FeedDescState, RootState> = {
  namespaced: true,

  state: {
    //일반게시글 상세정보
    communityDetail: {
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
        reviewCount: 0,
      },
      followerDTO: {
        uid: 0,
        nickname: "",
        introduce: "",
        profileImg: "",
        isFollowed: -2,
      },
    },
    // 팝업 알림
    alertStatus: false,
    // 오류 메시지
    errorMessage: "",
    // 성공 메시지
    successMessage: "",
  },

  getters: {
    // 커스텀 칵테일 상세정보
    getCommunityDetail: (state) => state.communityDetail,
    // 알럿 팝업 상태
    getAlertStatus: (state) => state.alertStatus,
    // 에러 메시지
    getErrorMessage: (state) => state.errorMessage,
    // 성공 메시지
    getSuccessMessage: (state) => state.successMessage,
  },

  mutations: {
    // 커스텀 칵테일 상세정보
    SET_COMMUNITY_DETAIL: (state, value: CustomCocktail) => {
      state.communityDetail = value;
    },
    // 에러 팝업
    SET_ALERT_STATUS: (state, value: boolean) => {
      state.alertStatus = value;
    },
    // 에러 메시지
    SET_ERROR_MESSAGE: (state, value: string) => {
      state.errorMessage = value;
    },
    // 성공 메시지
    SET_SUCCESS_MESSAGE: (state, value: string) => {
      state.successMessage = value;
    },
  },

  actions: {
    // 커스텀 칵테일 상세정보 불러오기
    setCommunityDetail: (
      { commit, dispatch, rootGetters },
      params: { feedId: number }
    ) => {
      axios({
        url: api.post.postCocktail(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          postId: params.feedId,
        },
      })
        .then((res) => {
          commit("SET_COMMUNITY_DETAIL", res.data);
        })
        .catch((err) => {
          if (err.response.status !== 401) {
            console.error(err.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "feedInfo/setCommunityDetail",
              params,
            };
            dispatch("personalInfo/requestRefreshToken", obj, {
              root: true,
            });
          }
        });
    },
    // 알럿 팝업
    changeAlertStatus: ({ commit }, value: boolean) => {
      commit("SET_ALERT_STATUS", value);
    },
  },
};
