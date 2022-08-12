import { Module } from 'vuex'
import { RootState } from '../index'
import axios from 'axios'
import api from '../../api/api'
import { userProfileData, userCommunityPostData, userCustomPostData, userPostReviewData, userBookmarkData } from '../../interface'


export interface ProfileDescState {
  currentTab: number;
  currentUserData: userProfileData;
  currentUserCommunity: userCommunityPostData;
  currentUserCustom: userCustomPostData;
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
      iLike:0,
      profileImg: "",
      postImg: null,
      likes:0
    },
    
    currentUserCustom: {
      createTime: "",
      baseCocktail: null,
      nickname: "",
      description: "",
      postId: 0,
      iLike:0,
      profileImg: "",
      postImg: null,
      likes:0
    },

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
    }
  },

  getters: {
    getCurrentTab: (state) => {
      return state.currentTab;
    },
    getCurrentUserData: (state) => {
      return state.currentUserData;
    },
    getCurrentUserPostCommunity: (state) => {
      return state.currentUserCommunity
    },
    getCurrentUserPostCustom: (state) => {
      return state.currentUserCustom
    },
    getCurrentUserPostReview: (state) => {
      return state.currentUserReview
    },
    getCurrentUserPostBookmark: (state) => {
      return state.currentUserBookmark
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
      state.currentUserCommunity = value
    },
    SET_CURRENT_USER_POST_CUSTOM: (state, value: userCustomPostData) => {
      state.currentUserCustom = value
    },
    SET_CURRENT_USER_POST_REVIEW: (state, value: userPostReviewData) => {
      state.currentUserReview = value
    },
    SET_CURRENT_USER_POST_BOOKMARK: (state, value: userBookmarkData) => {
      state.currentUserBookmark = value
    },
  },

  actions: {
    // 탭 변경
    changeCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },

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
        }
      })
        .then((res) => {
          console.log(res.data)
          commit("SET_CURRENT_USER_DATA", res.data)
        })
        .catch((err) => {
          console.error(err)
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserData",
              params: {
                uid
              }
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        })
    },

    // 유저가 작성한 일반게시물 리스트 보기
    getCurrentUserPostCommunity: ({ commit, dispatch, rootGetters }, uid: number) => {
      axios({
        url: api.accounts.profileCommunity(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          uid,
        }
      })
        .then((res) => {
          console.log(res.data)
          commit("SET_CURRENT_USER_POST_COMMUNITY", res.data)
        })
        .catch((err) => {
          console.error(err)
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserPostCommunity",
              params: {
                uid
              }
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        })
    },

    // 유저가 작성한 커칵/슈커칵 리스트 보기
    getCurrentUserPostCustom: ({ commit, dispatch, rootGetters }, uid: number) => {
      axios({
        url: api.accounts.profileCustom(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          uid,
        }
      })
        .then((res) => {
          console.log(res.data)
          commit("SET_CURRENT_USER_POST_CUSTOM", res.data)
        })
        .catch((err) => {
          console.error(err)
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserPostCustom",
              params: {
                uid
              }
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        })
    },

    // 유저가 작성한 후기 리스트 보기
    getCurrentUserPostReview: ({ commit, dispatch, rootGetters }, uid: number) => {
      axios({
        url: api.accounts.profileReview(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          uid,
        }
      })
        .then((res) => {
          console.log(res.data)
          commit("SET_CURRENT_USER_POST_REVIEW", res.data)
        })
        .catch((err) => {
          console.error(err)
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserPostReview",
              params: {
                uid
              }
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        })
    },

    // 유저가 북마크한 칵테일 리스트 보기
    getCurrentUserPostBookmark: ({ commit, dispatch, rootGetters }, uid: number) => {
      axios({
        url: api.accounts.profileBookmark(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          uid,
        }
      })
        .then((res) => {
          console.log(res.data)
          commit("SET_CURRENT_USER_POST_BOOKMARK", res.data)
        })
        .catch((err) => {
          console.error(err)
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "profileDesc/getCurrentUserPostBookmark",
              params: {
                uid
              }
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        })
    },
  }
}