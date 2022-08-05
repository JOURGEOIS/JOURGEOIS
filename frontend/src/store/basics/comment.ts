import axios from "axios";
import api from "../../api/api";
import { Module } from "vuex";
import { RootState } from "../index";

export interface Comment {}

export const comment: Module<Comment, RootState> = {
  namespaced: true,

  state: {},
  getters: {},
  mutations: {},
  actions: {
    // 댓글 작성
    createCustomCocktailComment: ({ rootGetters, dispatch }, data) => {
      axios({
        url: api.post.createComment(),
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
            console.error(error);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "comment/createCustomCocktailComment",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
