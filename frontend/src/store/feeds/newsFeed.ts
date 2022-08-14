import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
import { NewsFeed } from "../../interface";

export interface NewsFeedState {
  newsFeedListPage: number;
  newsFeedListData: NewsFeed[];
  newsFeedScrollY: number;
}

export const newsFeed: Module<NewsFeedState, RootState> = {
  namespaced: true,

  state: {
    // 뉴스피드 페이지
    newsFeedListPage: 0,

    // 뉴스피드 데이터
    newsFeedListData: [],

    // 뉴스피드 스크롤
    newsFeedScrollY: 0,
  },

  getters: {
    // 뉴스피드 리스트 페이지
    getNewsFeedListPage: (state) => state.newsFeedListPage,

    // 뉴스피드 리스트 데이터
    getNewsFeedListData: (state) => state.newsFeedListData,

    // 뉴스피드 스크롤
    getNewsFeedScrollY: (state) => state.newsFeedScrollY,
  },

  mutations: {
    // 뉴스피드 리스트 페이지 수정
    SET_NEWS_FEED_LIST_PAGE: (state, value: number) => {
      state.newsFeedListPage = value;
    },

    // 뉴스피드 리스트 데이터 추가
    ADD_NEWS_FEED_LIST_DATA: (state, value: NewsFeed[]) => {
      state.newsFeedListData.push(...value);
    },

    // 뉴스피드 리스트 데이터 수정
    SET_NEWS_FEED_LIST_DATA: (state, value: NewsFeed[]) => {
      state.newsFeedListData = value;
    },

    // 뉴스피드 리스트 데이터 - 좋아요 개수, 상태 수정
    SET_NEWS_FEED_LIST_DATA_LIKE: (state, { index, count, status }) => {
      state.newsFeedListData[index].isLiked = status;
      state.newsFeedListData[index].likeCount = count;
    },

    // 뉴스피드 리스트 스크롤 기억
    SET_NEWS_FEED_SCROLL_Y: (state, value: number) => {
      state.newsFeedScrollY = value;
    },
  },

  actions: {
    // 뉴스피드 리셋
    removeNewsFeedListData: ({ commit }) => {
      commit("SET_NEWS_FEED_LIST_PAGE", 0);
      commit("SET_NEWS_FEED_LIST_DATA", []);
      commit("SET_NEWS_FEED_SCROLL_Y", 0);
    },
    // 뉴스피드 스크롤 기억
    setNewsFeedScrollY: ({ commit }, value: number) => {
      commit("SET_NEWS_FEED_SCROLL_Y", value);
    },

    // 뉴스피드 정보 받아 오기
    getNewsFeedListData: ({ rootGetters, dispatch, getters, commit }) => {
      axios({
        url: api.post.listFeed(),
        method: "get",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          page: getters["getNewsFeedListPage"],
        },
      })
        .then((response) => {
          const page = getters["getNewsFeedListPage"];
          commit("ADD_NEWS_FEED_LIST_DATA", response.data);
          commit("SET_NEWS_FEED_LIST_PAGE", page + 1);
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
          } else {
            // refreshToken 재발급
            const obj = {
              func: "newsFeed/getNewsFeedListData",
              params: {},
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 뉴스피드 좋아요
    likeNewsFeedData: (
      { commit, rootGetters, dispatch },
      { index, postId }
    ) => {
      console.log(index, postId);
      axios({
        url: api.post.toggleLike(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          postId,
        },
      })
        .then((response) => {
          const data = response.data;
          commit("SET_NEWS_FEED_LIST_DATA_LIKE", {
            index,
            count: data.count,
            status: data.status,
          });
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            console.error(error);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "newsFeed/likeNewsFeedData",
              params: {
                index,
                postId,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
