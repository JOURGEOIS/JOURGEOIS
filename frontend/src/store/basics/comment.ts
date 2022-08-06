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
  deleteModalStatus: boolean;

  // 삭제 comment
  deleteCommentId: number;
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
    deleteModalStatus: false,

    // 삭제 comment
    deleteCommentId: 0,
  },
  getters: {
    // 댓글 리스트
    getCommentList: (state) => state.commentList,

    // 댓글 페이지
    getCommentPage: (state) => state.commentPage,

    // 댓글 정렬
    getCommentSort: (state) => state.commentSort,

    // 수정 성공 팝업
    getSuccessPopUpStatus: (state) => state.successPopUpStatus,

    // 삭제 Modal
    getDeleteModalStatus: (state) => state.deleteModalStatus,

    // 삭제 comment
    getDeleteCommentId: (state) => state.deleteCommentId,
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
    SET_DELETE_MODAL_STATUS: (state, value: boolean) => {
      state.deleteModalStatus = value;
    },

    // 삭제 코멘트 id
    SET_DELETE_COMMENT_ID: (state, value: number) => {
      state.deleteCommentId = value;
    },
  },
  actions: {
    // 수정 팝업 세팅
    toggleSuccessPopUpStatus: ({ commit }, value: boolean) => {
      commit("SET_SUCCESS_POPUP_STATUS", value);
    },

    // 삭제 팝업 세팅
    toggleDeleteModalStatus: ({ commit }, value: boolean) => {
      commit("SET_DELETE_MODAL_STATUS", value);
    },

    // 삭제 코멘트 id 세팅
    setDeleteCommentId: ({ commit }, value: number) => {
      commit("SET_DELETE_COMMENT_ID", value);
    },
    // 리스트와 페이지 리셋
    resetCommentData: ({ commit }) => {
      commit("REMOVE_COMMENT_LIST");
      commit("SET_COMMENT_PAGE", 0);
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
          dispatch("resetCommentData");
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
      console.log("데이터!");
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

    // 댓글 수정하기
    updateComment: ({ rootGetters, dispatch, commit }, data) => {
      const { pr_id, p_id, review } = data;
      axios({
        url: api.post.comment(),
        method: "put",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          pr_id,
          review,
        },
      })
        .then(() => {
          dispatch("resetCommentData");
          dispatch("saveCommentList", p_id);
          commit("SET_SUCCESS_POPUP_STATUS", true);
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            // 실패 팝업
            console.error(error);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "comment/updateComment",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 댓글 삭제
    commentDelete: ({ commit, getters, rootGetters, dispatch }, id) => {
      axios({
        url: api.post.comment(),
        method: "delete",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          pr_id: getters["getDeleteCommentId"],
        },
      })
        .then(() => {
          dispatch("resetCommentData");
          dispatch("saveCommentList", id);
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            // 실패 팝업
            console.error(error);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "comment/commentDelete",
              params: id,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
