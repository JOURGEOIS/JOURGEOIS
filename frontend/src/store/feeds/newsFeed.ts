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
    newsFeedListPage: 0,
    newsFeedListData: [],
    newsFeedScrollY: 0,
  },

  getters: {
    getNewsFeedListPage: (state) => state.newsFeedListPage,
    getNewsFeedListData: (state) => state.newsFeedListData,
    getNewsFeedScrollY: (state) => state.newsFeedScrollY,
  },

  mutations: {
    SET_NEWS_FEED_LIST_PAGE: (state, value: number) => {
      state.newsFeedListPage = value;
    },
    ADD_NEWS_FEED_LIST_DATA: (state, value: NewsFeed[]) => {
      state.newsFeedListData.push(...value);
    },
    SET_NEWS_FEED_LIST_DATA: (state, value: NewsFeed[]) => {
      state.newsFeedListData = value;
    },
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
  },
};
