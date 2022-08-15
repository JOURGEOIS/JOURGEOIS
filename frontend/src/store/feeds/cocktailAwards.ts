import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
import { ContestCocktail } from "../../interface";
import { checkBadWord } from "../../functions/checkText";

export interface CocktailAwardsState {
  cocktailAwardsVoteList: ContestCocktail[];
  cocktailAwardsVotePage: number;
  cocktailAwardsNowList: ContestCocktail[];
  cocktailAwardsNowPage: number;
  cocktailAwardsDetail: ContestCocktail;
  currentTab: number;
  cocktailAwardsErrorStatus: boolean;
  cocktailAwardsErrorMessage: string;
}

export const cocktailAwards: Module<CocktailAwardsState, RootState> = {
  namespaced: true,

  state: {
    // 칵테일 현황 탭 리스트
    cocktailAwardsNowList: [],
    cocktailAwardsNowPage: 0,

    // 칵테일 투표 탭 리스트
    cocktailAwardsVoteList: [],
    cocktailAwardsVotePage: 0,

    // 칵테일 상세 정보
    cocktailAwardsDetail: {
      postId: 0,
      description: "",
      imgLink: "",
      title: "",
      like: 0,
      percentage: "",
    },

    // 현재 탭 index
    currentTab: 0,

    // 칵테일 에러 메시지
    cocktailAwardsErrorStatus: false,
    cocktailAwardsErrorMessage: "",
  },

  getters: {
    getCocktailAwardsNowList: (state) => state.cocktailAwardsNowList,
    getCocktailAwardsNowPage: (state) => state.cocktailAwardsNowPage,
    getCocktailAwardsVoteList: (state) => state.cocktailAwardsVoteList,
    getCocktailAwardsVotePage: (state) => state.cocktailAwardsVotePage,
    getCocktailAwardsDetail: (state) => state.cocktailAwardsDetail,
    getCurrentTab: (state) => state.currentTab,
  },

  mutations: {
    // 칵테일 현황
    ADD_COCKTAIL_AWARDS_NOW_LIST: (state, value: ContestCocktail[]) => {
      state.cocktailAwardsNowList.push(...value);
    },
    SET_COCKTAIL_AWARDS_NOW_LIST: (state, value: ContestCocktail[]) => {
      state.cocktailAwardsNowList = value;
    },
    SET_COCKTAIL_AWARDS_NOW_PAGE: (state, value: number) => {
      state.cocktailAwardsNowPage = value;
    },

    // 칵테일 투표
    ADD_COCKTAIL_AWARDS_VOTE_LIST: (state, value: ContestCocktail[]) => {
      state.cocktailAwardsVoteList.push(...value);
    },
    SET_COCKTAIL_AWARDS_VOTE_LIST: (state, value: ContestCocktail[]) => {
      state.cocktailAwardsVoteList = value;
    },
    SET_COCKTAIL_AWARDS_VOTE_PAGE: (state, value: number) => {
      state.cocktailAwardsVotePage = value;
    },
    CHANGE_COCKTAIL_VOTE_STATUS: (state, { index, value }) => {
      state.cocktailAwardsVoteList[index].like = value;
    },

    // 탭 변경
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },

    // 상세 정보
    SET_COCKTAIL_AWARDS_DETAIL: (state, value: ContestCocktail) => {
      state.cocktailAwardsDetail = value;
    },

    CHANGE_COCKTAIL_DESC_STATUS: (state, value: number) => {
      state.cocktailAwardsDetail.like = value;
    },
  },

  actions: {
    // 탭 변경
    changeCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },

    // 리스트 리셋
    resetCocktailAwardsList: ({ commit }) => {
      commit("SET_COCKTAIL_AWARDS_NOW_LIST", []);
      commit("SET_COCKTAIL_AWARDS_NOW_PAGE", 0);
      commit("SET_COCKTAIL_AWARDS_VOTE_LIST", []);
      commit("SET_COCKTAIL_AWARDS_VOTE_PAGE", 0);
    },

    // desc 리셋
    resetCocktailAwardsDesc: ({ commit }) => {
      const data = {
        postId: 0,
        description: "",
        imgLink: "",
        title: "",
        like: 0,
        percentage: "",
      };
      commit("SET_COCKTAIL_AWARDS_DETAIL", data);
    },

    //======================== Create ========================
    // 제출 시, 유효성 검사
    checkCocktailAwardsForm: (
      { dispatch },
      { title, description, img, isAgree }
    ) => {
      // 빈필드 확인
      const imgRequired = !(img instanceof File);
      const titleRequired = !title;

      if (imgRequired || titleRequired) {
        dispatch(
          "modal/changeErrorModalMessage",
          " 이름, 이미지 항목은 필수 입력입니다",
          { root: true }
        );
        dispatch("modal/blinkErrorModalAppStatus", {}, { root: true });
        return;
      }

      // 개인정보 수집
      const isAgreeRequired = !isAgree;
      if (isAgreeRequired) {
        dispatch(
          "modal/changeErrorModalMessage",
          "개인정보 수집에 동의해주세요",
          { root: true }
        );
        dispatch("modal/blinkErrorModalAppStatus", {}, { root: true });
        return;
      }

      // 비속어 검사
      const titleCheck = checkBadWord(title);
      const descriptionCheck = checkBadWord(description);
      if (titleCheck || descriptionCheck) {
        dispatch(
          "modal/changeErrorModalMessage",
          "부적절한 언어가 포함되었습니다",
          { root: true }
        );
        dispatch("modal/blinkErrorModalAppStatus", {}, { root: true });
        return;
      }

      dispatch("submitCocktailAwardsForm", {
        title,
        description,
        img,
        isAgree,
      });
    },

    // 제출
    submitCocktailAwardsForm: ({ rootGetters, dispatch }, data: object) => {
      axios({
        url: api.awards.joinContest(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
          "Content-Type": "multipart/form-data",
        },
        data,
      })
        .then((response) => {
          router.replace({
            name: "TheCocktailAwardsDescView",
            params: {
              feedId: response.data,
            },
          });
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            if (error.response.data.fail) {
              dispatch(
                "modal/changeErrorModalMessage",
                error.response.data.fail,
                { root: true }
              );
            } else {
              dispatch(
                "modal/changeErrorModalMessage",
                "잠시 후에 시도해주세요",
                { root: true }
              );
            }
            dispatch("modal/blinkErrorModalAppStatus", {}, { root: true });
          } else {
            // refreshToken 재발급
            const obj = {
              func: "contest/submitCocktailAwardsForm",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    //======================== READ ========================
    fetchCocktailAwardsNowList: ({ commit, getters }) => {
      axios({
        url: api.awards.contestListNow(),
        method: "get",
        params: {
          page: getters["getCocktailAwardsNowPage"],
        },
      })
        .then((response) => {
          // 저장
          commit("ADD_COCKTAIL_AWARDS_NOW_LIST", response.data);

          //page+ 1
          const page = getters["getCocktailAwardsNowPage"];
          commit("SET_COCKTAIL_AWARDS_NOW_PAGE", page + 1);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    fetchCocktailAwardsVoteList: ({ commit, getters, rootGetters }) => {
      const uid = rootGetters["personalInfo/getUserInfoUserId"];
      let headers;
      if (!uid) {
        headers = {};
      } else {
        headers = { uid };
      }

      axios({
        url: api.awards.contestListVote(),
        method: "get",
        headers,
        params: {
          page: getters["getCocktailAwardsVotePage"],
        },
      })
        .then((response) => {
          // 저장
          commit("ADD_COCKTAIL_AWARDS_VOTE_LIST", response.data);

          //page+ 1
          const page = getters["getCocktailAwardsVotePage"];
          commit("SET_COCKTAIL_AWARDS_VOTE_PAGE", page + 1);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    //======================== Vote ========================
    // vuex 값 수정
    changeCocktailVoteStatus: (
      { commit },
      data: { index: number; value: number }
    ) => {
      commit("CHANGE_COCKTAIL_VOTE_STATUS", data);
    },

    // desc 값 수정
    changeCocktailVoteDesc: ({ commit }, value: number) => {
      commit("CHANGE_COCKTAIL_DESC_STATUS", value);
    },

    // 서버에 제출
    voteCocktail: ({ commit, rootGetters, dispatch }, { index, postId }) => {
      axios({
        url: api.awards.voteContest(),
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
          if (index === null) {
            dispatch("changeCocktailVoteDesc", data.status);
          } else {
            dispatch("changeCocktailVoteStatus", { index, value: data.status });
          }
        })
        .catch((error) => {
          console.log(error);
          if (error.response.status !== 401) {
            console.error(error);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "cocktailAwards/voteCocktail",
              params: {
                index,
                postId,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
    //======================== desc ========================
    fetchCocktailAwardsDesc: (
      { commit, rootGetters, dispatch },
      { postId }
    ) => {
      axios({
        url: api.awards.contestDesc(),
        method: "get",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          postId,
        },
      })
        .then((response) => {
          commit("SET_COCKTAIL_AWARDS_DETAIL", response.data);
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            console.error(error);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "cocktailAwards/fetchCocktailAwardsDesc",
              params: {
                postId,
              },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
