import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
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
  // 일반 게시글
  currentUserCommunity: userCommunityPostData[];
  currentUserCommunityPage: number;
  // 칵테일 게시글
  currentUserCustom: userCustomPostData[];
  currentUserCustomPage: number;
  // 후기
  currentUserReview: userPostReviewData[];
  currentUserReviewPage: number;
  // 북마크
  currentUserBookmark: userBookmarkData[];
  currentUserBookmarkPage: number;
}

export const profileDesc: Module<ProfileDescState, RootState> = {
  namespaced: true,

  state: {
    currentTab: 0,
    currentUserData: {
      uid: 0,
      introduce: "",
      followerCnt: 0,
      followingCnt: 0,
      postCnt: 0,
      nickname: "",
      isPrivate: 0,
      profileImg: "",
      email: "",
      isFollowed: 0,
    },

    currentUserCommunity: [],
    currentUserCommunityPage: 0,

    currentUserCustom: [],
    currentUserCustomPage: 0,

    currentUserReview: [],
    currentUserReviewPage: 0,

    currentUserBookmark: [],
    currentUserBookmarkPage: 0,
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
    getCurrentUserPostCommunityPage: (state) => {
      return state.currentUserCommunityPage;
    },

    getCurrentUserPostCustom: (state) => {
      return state.currentUserCustom;
    },
    getCurrentUserPostCustomPage: (state) => {
      return state.currentUserCustomPage;
    },

    getCurrentUserPostReview: (state) => {
      return state.currentUserReview;
    },
    getCurrentUserPostReviewPage: (state) => {
      return state.currentUserReviewPage;
    },

    getCurrentUserPostBookmark: (state) => {
      return state.currentUserBookmark;
    },
    getCurrentUserPostBookmarkPage: (state) => {
      return state.currentUserBookmarkPage;
    },

    getPrivateModeSet: (state) => {
      return state.currentUserData.isPrivate;
    },
  },

  mutations: {
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },
    SET_CURRENT_USER_DATA: (state, value: userProfileData) => {
      state.currentUserData = value;
    },

    ADD_CURRENT_USER_POST_COMMUNITY: (
      state,
      value: userCommunityPostData[]
    ) => {
      state.currentUserCommunity.push(...value);
    },
    SET_CURRENT_USER_POST_COMMUNITY: (
      state,
      value: userCommunityPostData[]
    ) => {
      state.currentUserCommunity = value;
    },
    SET_CURRENT_USER_POST_COMMUNITY_PAGE: (state, value: number) => {
      state.currentUserCommunityPage = value;
    },

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

    ADD_CURRENT_USER_POST_REVIEW: (state, value: userPostReviewData[]) => {
      state.currentUserReview.push(...value);
    },
    SET_CURRENT_USER_POST_REVIEW: (state, value: userPostReviewData[]) => {
      state.currentUserReview = value;
    },
    SET_CURRENT_USER_POST_REVIEW_PAGE: (state, value: number) => {
      state.currentUserReviewPage = value;
    },

    ADD_CURRENT_USER_POST_BOOKMARK: (state, value: userBookmarkData[]) => {
      state.currentUserBookmark.push(...value);
    },
    SET_CURRENT_USER_POST_BOOKMARK: (state, value: userBookmarkData[]) => {
      state.currentUserBookmark = value;
    },
    SET_CURRENT_USER_POST_BOOKMARK_PAGE: (state, value: number) => {
      state.currentUserBookmarkPage = value;
    },

    SET_PRIVATE_MODE: (state, value: number) => {
      state.currentUserData.isPrivate = value;
    },
  },

  actions: {
    // 탭 변경
    changeCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },

    // 리스트와 페이지를 리셋하는 함수 (unmount될 때 호출한다. )
    resetCurrentUserPost: ({ commit }) => {
      commit("SET_CURRENT_USER_POST_COMMUNITY", []);
      commit("SET_CURRENT_USER_POST_COMMUNITY_PAGE", 0);
      commit("SET_CURRENT_USER_POST_CUSTOM", []);
      commit("SET_CURRENT_USER_POST_CUSTOM_PAGE", 0);
      commit("SET_CURRENT_USER_POST_REVIEW", []);
      commit("SET_CURRENT_USER_POST_REVIEW_PAGE", 0);
      commit("SET_CURRENT_USER_POST_BOOKMARK", []);
      commit("SET_CURRENT_USER_POST_BOOKMARK_PAGE", 0);
    },

    // 프로필 정보 가져오기
    getCurrentUserData: ({ commit, dispatch, rootGetters }, uid: number) => {
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
    getCurrentUserPostCommunityData: (
      { commit, dispatch, getters, rootGetters },
      uid: number
    ) => {
      const page = getters["getCurrentUserPostCommunityPage"];
      axios({
        url: api.accounts.profileCommunity(),
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
          commit("ADD_CURRENT_USER_POST_COMMUNITY", res.data);
          commit("SET_CURRENT_USER_POST_COMMUNITY_PAGE", page + 1);
        })
        .catch((err) => {
          console.error(err);
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserPostCommunityData",
              params: {
                uid,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

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
              func: "profileDesc/getCurrentUserPostCustomData",
              params: {
                uid,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 유저가 작성한 후기 리스트 보기
    getCurrentUserPostReviewData: (
      { commit, dispatch, rootGetters, getters },
      uid: number
    ) => {
      const page = getters["getCurrentUserPostReviewPage"];
      axios({
        url: api.accounts.profileReview(),
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
          commit("ADD_CURRENT_USER_POST_REVIEW", res.data);
          commit("SET_CURRENT_USER_POST_REVIEW_PAGE", page + 1);
        })
        .catch((err) => {
          console.error(err);
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserPostReviewData",
              params: {
                uid,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 유저가 북마크한 칵테일 리스트 보기
    getCurrentUserPostBookmarkData: (
      { commit, dispatch, rootGetters, getters },
      uid: number
    ) => {
      const page = getters["getCurrentUserPostBookmarkPage"];
      axios({
        url: api.accounts.profileBookmark(),
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
          commit("ADD_CURRENT_USER_POST_BOOKMARK", res.data);
          commit("SET_CURRENT_USER_POST_BOOKMARK_PAGE", page + 1);
        })
        .catch((err) => {
          console.error(err);
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserPostBookmarkData",
              params: {
                uid,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    changePrivateModeSet: ({ commit, dispatch, rootGetters }) => {
      const uid = rootGetters["personalInfo/getUserInfoUserId"];
      axios({
        url: api.accounts.profileModeSet(),
        method: "put",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
      })
        .then((res) => {
          const privateMode = res.data.isPrivate;
          commit("SET_PRIVATE_MODE", privateMode);
          // dispatch("personalInfo/savePrivateModeSet", privateMode)
        })
        .catch((err) => {
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/changePrivateModeSet",
              params: {},
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
