import axios from "axios";
import api from "../../api/api";
import { Module } from "vuex";
import { RootState } from "../index";

export interface Comment {
  commentList: object[];
  commentPage: number;
}

export const comment: Module<Comment, RootState> = {
  namespaced: true,

  state: {
    commentList: [],
    commentPage: 0,
  },
  getters: {
    getCommentList: (state) => state.commentList,
    getCommentPage: (state) => state.commentPage,
  },
  mutations: {
    REMOVE_COMMENT_LIST: (state) => {
      state.commentList = [];
    },
    ADD_COMMENT_LIST: (state, value: object[]) => {
      state.commentList.concat(value);
    },
    SET_COMMENT_PAGE: (state, value: number) => {
      state.commentPage = value;
    },
  },
  actions: {
    // 댓글 작성
    createComment: ({ rootGetters, dispatch }, data) => {
      axios({
        url: api.post.comment(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data,
      })
        .then((response) => {
          response.data;
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            // 실패 팝업
            console.error(error);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "comment/createComment",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 리스트 받아오기
    saveCommentList: ({ getters, dispatch, rootGetters, commit }, data) => {
      const { p_id, asc } = data;
      axios({
        url: api.post.comment(),
        method: "get",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          p_id,
          asc,
          page: getters["getCommentPage"],
        },
      })
        .then((response) => {
          console.log(response.data);
          const page = getters["getCommentPage"];
          commit("ADD_COMMENT_LIST", response.data);
          commit("SET_COMMENT_PAGE", page + 1);
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            // 실패 팝업
            console.error(error);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "comment/getCommentList",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
