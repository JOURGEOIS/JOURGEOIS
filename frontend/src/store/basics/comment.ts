import axios from "axios";
import api from "../../api/api";
import { Module } from "vuex";
import { RootState } from "../index";

export interface Comment {
  // 댓글 리스트
  commentList: object[];

  // 댓글 페이지
  commentPage: number;

  // 댓글 정렬: false는 최신순, true는 오래된 순
  commentSort: boolean;

  // 수정 팝업
  successPopUpStatus: boolean;

  // 삭제 팝업
  deletePopUpStatus: boolean;
}

export const comment: Module<Comment, RootState> = {
  namespaced: true,

  state: {
    // 댓글 리스트
    commentList: [],

    // 댓글 페이지
    commentPage: 0,

    // 댓글 정렬
    commentSort: false,

    // 수정 성공 팝업
    successPopUpStatus: false,

    // 삭제 팝업
    deletePopUpStatus: false,
  },
  getters: {
    // 댓글 리스트
    getCommentList: (state) => state.commentList,

    // 댓글 페이지
    getCommentPage: (state) => state.commentPage,

    // 댓글 정렬
    getCommentSort: (state) => state.commentSort,

    // 수정 팝업
    getSuccessPopUpStatus: (state) => state.successPopUpStatus,

    // 삭제 팝업
    getDeletePopUpStatus: (state) => state.deletePopUpStatus,
  },
  mutations: {
    // 댓글 리스트 리셋
    REMOVE_COMMENT_LIST: (state) => {
      state.commentList = [];
    },

    // 댓글을 리스트에 추가
    ADD_COMMENT_LIST: (state, value: object[]) => {
      state.commentList.push(...value);
    },

    // 댓글 페이지
    SET_COMMENT_PAGE: (state, value: number) => {
      state.commentPage = value;
    },

    // 댓글 정렬 변경
    SET_COMMENT_SORT: (state, value: boolean) => {
      state.commentSort = value;
    },

    // 수정 팝업 세팅
    SET_SUCCESS_POPUP_STATUS: (state, value: boolean) => {
      state.successPopUpStatus = value;
    },

    // 삭제 팝업 세팅
    SET_DELETE_POPUP_STATUS: (state, value: boolean) => {
      state.deletePopUpStatus = value;
    },
  },
  actions: {
    // 수정 팝업 세팅
    changeSuccessPopUpStatus: ({ commit }, value: boolean) => {
      commit("SET_SUCCESS_POPUP_STATUS", value);
    },
    // 삭제 팝업 세팅
    changeDeletePopUpStatus: ({ commit }, value: boolean) => {
      commit("SET_DELETE_POPUP_STATUS", value);
    },
    // 댓글 작성
    createComment: ({ rootGetters, dispatch, commit }, data) => {
      const { p_id } = data;
      axios({
        url: api.post.comment(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data,
      })
        .then(() => {
          commit("REMOVE_COMMENT_LIST");
          commit("SET_COMMENT_PAGE", 0);
          dispatch("saveCommentList", p_id);
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
    saveCommentList: ({ getters, dispatch, rootGetters, commit }, id) => {
      axios({
        url: api.post.comment(),
        method: "get",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          p_id: id,
          asc: getters["getCommentSort"],
          page: getters["getCommentPage"],
        },
      })
        .then((response) => {
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
              func: "comment/saveCommentList",
              params: id,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
