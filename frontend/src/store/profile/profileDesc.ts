import { Module } from 'vuex'
import { RootState } from '../index'
import axios from 'axios'
import api from '../../api/api'
import router from '../../router'

export interface userProfileData {
  uid: number
  email: string
  name: string | null
  nickname: string
  profileImg: string
  profileLink: null
  introduce: string
  followerCnt: number
  followingCnt: number
  postCnt: number
  isPublic: number
}

export interface ProfileDescState {
  currentTab: number;
  currentUserData: userProfileData;
  
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
  },

  getters: {
    getCurrentTab: (state) => {
      return state.currentTab;
    },
    getCurrentUserData: (state) => {
      return state.currentUserData;
    },
  },

  mutations: {
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },
    SET_CURRENT_USER_DATA: (state, value: userProfileData) => {
      state.currentUserData = value;
    },
  },

  actions: {
    // 탭 변경
    changeCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
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
        }
      })
        .then((res) => {
          console.log(res.data)
          commit("SET_CURRENT_USER_DATA", res.data)
        })
        .catch((err) => {
          console.log(err)
          if (err.response.status !== 401) {
            console.log(err)
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
    }
  }
}