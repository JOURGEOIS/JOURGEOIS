import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import {
  userProfileData,
  userCommunityPostData,
  userCustomPostData,
  userPostReviewData,
  userBookmarkData,
} from "../../interface";

export interface ProfileDescState {
  currentTab: number;
  currentUserData: userProfileData;
  currentUserCommunity: userCommunityPostData;
  //
  currentUserCustom: userCustomPostData[];
  currentUserCustomPage: number;
  //
  currentUserReview: userPostReviewData;
  currentUserBookmark: userBookmarkData;
}

export const profileDesc: Module<ProfileDescState, RootState> = {
  namespaced: true,

  state: {
    currentTab: 0,
    currentUserData: {
      uid: 0,
      email: "",
      name: null,
      nickname: "",
      profileImg: "",
      profileLink: null,
      introduce: "",
      followerCnt: 0,
      followingCnt: 0,
      postCnt: 0,
      isPublic: 0,
    },
    currentUserCommunity: {
      createTime: "",
      nickname: "",
      description: "",
      postId: 0,
      iLike: 0,
      profileImg: "",
      postImg: null,
      likes: 0,
    },

    //=======================재경
    // createTime: "",
    // baseCocktail: null,
    // nickname: "",
    // description: "",
    // postId: 0,
    // iLike: 0,
    // profileImg: "",
    // postImg: null,
    // likes: 0,
    currentUserCustom: [],
    currentUserCustomPage: 0,
    //===========================

    currentUserReview: {
      img: "",
      cocktailId: 0,
      comment: "",
      tag: "",
      category: "",
      nameKR: "",
    },

    currentUserBookmark: {
      img: "",
      cocktailId: 0,
      tag: "",
      category: "",
      nameKR: "",
    },
  },

  getters: {
    getCurrentTab: (state) => {
      return state.currentTab;
    },
    getCurrentUserData: (state) => {
      return state.currentUserData;
    },
    getCurrentUserPostCommunity: (state) => {
      return state.currentUserCommunity;
    },

    // ======================재경
    getCurrentUserPostCustom: (state) => {
      return state.currentUserCustom;
    },
    getCurrentUserPostCustomPage: (state) => {
      return state.currentUserCustomPage;
    },
    //===========================

    getCurrentUserPostReview: (state) => {
      return state.currentUserReview;
    },
    getCurrentUserPostBookmark: (state) => {
      return state.currentUserBookmark;
    },
  },

  mutations: {
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },
    SET_CURRENT_USER_DATA: (state, value: userProfileData) => {
      state.currentUserData = value;
    },
    SET_CURRENT_USER_POST_COMMUNITY: (state, value: userCommunityPostData) => {
      state.currentUserCommunity = value;
    },

    //========================재경
    // 서버에서 받은 리스트를 기존 리스트에추가한다.
    ADD_CURRENT_USER_POST_CUSTOM: (state, value: userCustomPostData[]) => {
      state.currentUserCustom.push(...value);
    },

    // 리스트를 value로 수정한다.
    SET_CURRENT_USER_POST_CUSTOM: (state, value: userCustomPostData[]) => {
      state.currentUserCustom = value;
    },

    // 페이지를 value로 수정한다
    SET_CURRENT_USER_POST_CUSTOM_PAGE: (state, value: number) => {
      state.currentUserCustomPage = value;
    },
    //==========================

    SET_CURRENT_USER_POST_REVIEW: (state, value: userPostReviewData) => {
      state.currentUserReview = value;
    },
    SET_CURRENT_USER_POST_BOOKMARK: (state, value: userBookmarkData) => {
      state.currentUserBookmark = value;
    },
  },

  actions: {
    // 탭 변경
    changeCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },

    //=========================재경
    // 리스트와 페이지를 리셋하는 함수 (unmount될 때 호출한다. )
    resetCurrentUserPost: ({ commit }) => {
      commit("SET_CURRENT_USER_POST_CUSTOM", []);
      commit("SET_CURRENT_USER_POST_CUSTOM_PAGE", 0);
    },
    //======================================

    // 프로필 정보 가져오기
    getCurrentUserData: ({ commit, dispatch, rootGetters }, uid: number) => {
      // console.log(uid, rootGetters["personalInfo/getAccessToken"])
      axios({
        url: api.accounts.profileUserInfo(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          uid,
        },
      })
        .then((res) => {
          console.log(res.data);
          commit("SET_CURRENT_USER_DATA", res.data);
        })
        .catch((err) => {
          console.error(err);
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserData",
              params: {
                uid,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 유저가 작성한 일반게시물 리스트 보기
    getCurrentUserPostCommunity: (
      { commit, dispatch, rootGetters },
      uid: number
    ) => {
      axios({
        url: api.accounts.profileCommunity(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          uid,
        },
      })
        .then((res) => {
          console.log(res.data);
          commit("SET_CURRENT_USER_POST_COMMUNITY", res.data);
        })
        .catch((err) => {
          console.error(err);
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserPostCommunity",
              params: {
                uid,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    //=================================== 재경
    // 유저가 작성한 커칵/슈커칵 리스트 보기
    getCurrentUserPostCustomData: (
      { commit, dispatch, rootGetters, getters },
      uid: number
    ) => {
      const page = getters["getCurrentUserPostCustomPage"];
      axios({
        url: api.accounts.profileCustom(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          uid,
          page,
        },
      })
        .then((res) => {
          // 받은 데이터를 리스트에 추가하고 page를 늘린다.
          commit("ADD_CURRENT_USER_POST_CUSTOM", res.data);
          commit("SET_CURRENT_USER_POST_CUSTOM_PAGE", page + 1);
        })
        .catch((err) => {
          console.error(err);
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserPostCustom",
              params: {
                uid,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 유저가 작성한 후기 리스트 보기
    getCurrentUserPostReview: (
      { commit, dispatch, rootGetters },
      uid: number
    ) => {
      axios({
        url: api.accounts.profileReview(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          uid,
        },
      })
        .then((res) => {
          console.log(res.data);
          commit("SET_CURRENT_USER_POST_REVIEW", res.data);
        })
        .catch((err) => {
          console.error(err);
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserPostReview",
              params: {
                uid,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 유저가 북마크한 칵테일 리스트 보기
    getCurrentUserPostBookmark: (
      { commit, dispatch, rootGetters },
      uid: number
    ) => {
      axios({
        url: api.accounts.profileBookmark(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          uid,
        },
      })
        .then((res) => {
          console.log(res.data);
          commit("SET_CURRENT_USER_POST_BOOKMARK", res.data);
        })
        .catch((err) => {
          console.error(err);
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserPostBookmark",
              params: {
                uid,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
