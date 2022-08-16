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
  communityDetailDefault: CustomCocktail;
  // 삭제 팝업
  deleteModalStatus: boolean;
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
    communityDetailDefault: {
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
    // 삭제 팝업
    deleteModalStatus: false,
    // 팝업 알림
    alertStatus: false,
    // 오류 메시지
    errorMessage: "",
    // 성공 메시지
    successMessage: "",
  },

  getters: {
    // 일반게시물 상세정보
    getCommunityDetail: (state) => state.communityDetail,
    getCommunityDetailDefault: (state) => state.communityDetailDefault,
    // 알럿 팝업 상태
    getAlertStatus: (state) => state.alertStatus,
    // 에러 메시지
    getErrorMessage: (state) => state.errorMessage,
    // 성공 메시지
    getSuccessMessage: (state) => state.successMessage,
    // 삭제 Modal
    getDeleteModalStatus: (state) => state.deleteModalStatus,
    // 일반 게시글 세팅
    getImgLink: (state) => state.communityDetail.customCocktail.imgLink,
    getDescription: (state) => state.communityDetail.customCocktail.description,
  },

  mutations: {
    // 일반게시글 상세정보
    SET_COMMUNITY_DETAIL: (state, value: CustomCocktail) => {
      state.communityDetail = value;
    },
    // * state에 일반게시글 정보 제거
    REMOVE_COMMUNITY_DETAIL: (state) => {
      state.communityDetail = state.communityDetailDefault;
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

    // 삭제 팝업 세팅
    SET_DELETE_MODAL_STATUS: (state, value: boolean) => {
      state.deleteModalStatus = value;
    },

    // 일반 게시글 정보
    SET_IMG_LINK: (state, value: string) =>
      (state.communityDetail.customCocktail.imgLink = value),
    SET_DESCRIPTION: (state, value: string) => {
      state.communityDetail.customCocktail.description = value;
    },
  },

  actions: {
    // vuex 리셋
    resetCommunityData: ({ commit }) => {
      commit("SET_ALERT_STATUS", false);
      commit("SET_ERROR_MESSAGE", "");
      commit("SET_SUCCESS_MESSAGE", "");
      commit("SET_IMG_LINK", "");
      commit("SET_DESCRIPTION", "");
    },

    // img_Link
    setImgLink: ({ commit }, value: string) => {
      commit("SET_IMG_LINK", value);
    },

    // description
    setDescription: ({ commit }, value: string) => {
      commit("SET_DESCRIPTION", value);
    },

    // 사진 업로드 임시 저장
    uploadImage: ({ rootGetters, dispatch, commit }, data) => {
      axios({
        url: api.post.uploadImage(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
          "Content-Type": "multipart/form-data",
        },
        data,
      })
        .then((response) => {
          commit("SET_IMG_LINK", response.data.url);
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            commit("SET_ERROR_MESSAGE", "잠시 후에 시도해주세요");
            commit("SET_ALERT_STATUS", true);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "feedDescInfo/uploadImage",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 일반게시글 상세정보 불러오기
    getCommunityDetail: (
      { commit, dispatch, rootGetters },
      params: { feedId: number }
    ) => {
      axios({
        url: api.post.postCocktail(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: { postId: params.feedId },
      })
        .then((res) => {
          const reviewCount = res.data.customCocktail.reviewCount;
          dispatch(
            "comment/setCommentCount",
            { count: reviewCount },
            { root: true }
          );
          commit("SET_COMMUNITY_DETAIL", res.data);
        })
        .catch((err) => {
          if (err.response.status !== 401) {
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          } else {
            // refreshToken 재발급
            const obj = {
              func: "feedDescInfo/getCommunityDetail",
              params,
            };
            dispatch("personalInfo/requestRefreshToken", obj, {
              root: true,
            });
          }
        });
    },

    // * state에 일반게시글 정보 제거
    removeCommunityDetail: ({ commit }) => {
      commit("REMOVE_COMMUNITY_DETAIL");
    },
    // 일반게시글 삭제
    deleteCommunityPost: ({ commit, dispatch, rootGetters }, postId) => {
      axios({
        url: api.post.postCocktail(),
        method: "DELETE",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: { postId: postId },
      })
        .then((res) => {
          router.push({
            name: "TheNewsFeedView",
          });
          // 성공 알림
          commit("SET_SUCCESS_MESSAGE", "성공적으로 삭제되었습니다");
          commit("SET_ALERT_STATUS", true);
          dispatch("newsFeed/removeNewsFeedListData", {}, { root: true });
        })
        .catch((err) => {
          if (err.res.status !== 401) {
            // 실패 팝업
            commit("SET_ERROR_MESSAGE", "잠시 후에 시도해주세요");
            commit("SET_ALERT_STATUS", true);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "createFeed/deleteCommunityPost",
              params: postId,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 삭제 팝업 세팅
    toggleDeleteModal: ({commit}, value: boolean) => {
      commit("SET_DELETE_MODAL_STATUS", value)
    },

    // 알럿 팝업
    changeAlertStatus: ({ commit }, value: boolean) => {
      commit("SET_ALERT_STATUS", value);
    },
  },
};
