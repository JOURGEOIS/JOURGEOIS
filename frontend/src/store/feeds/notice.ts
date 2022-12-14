import { Module } from "vuex";
import { database } from "../../plugins/firebase.js";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
import { Notice } from "../../interface";
import {
  collection,
  query,
  where,
  onSnapshot,
  getDocs,
  limit,
} from "firebase/firestore";

export interface NoticeState {
  noticeStatus: boolean;
  noticeList: Notice[];
  noticeListPage: number;
}

export const notice: Module<NoticeState, RootState> = {
  namespaced: true,

  state: {
    // 알림 읽은 상태 여부
    noticeStatus: false,

    // 알림 리스트
    noticeList: [],

    // 알림 리스트 페이징
    noticeListPage: 0,
  },

  getters: {
    // 알림 읽은 상태 여부
    getNoticeStatus: (state) => state.noticeStatus,

    // 알림 리스트
    getNoticeList: (state) => state.noticeList,

    // 알림 리스트 페이징
    getNoticeListPage: (state) => state.noticeListPage,
  },

  mutations: {
    // 알림 상태 변경
    SET_NOTICE_STATUS: (state, value: boolean) => {
      state.noticeStatus = value;
    },

    // 알림 리스트 추가
    ADD_NOTICE_LIST: (state, value: Notice[]) => {
      state.noticeList.push(...value);
    },

    // 알림 리스트 변경
    SET_NOTICE_LIST: (state, value: Notice[]) => {
      state.noticeList = value;
    },

    // 알림 리스트 페이지 변경
    SET_NOTICE_LIST_PAGE: (state, value: number) => {
      state.noticeListPage = value;
    },

    // 알림 모두 읽기
    READ_NOTICE_LIST: (state) => {
      state.noticeList.forEach((item) => (item.notification.isRead = true));
    },
  },

  actions: {
    // 리셋
    resetNoticeList: ({ commit }) => {
      commit("SET_NOTICE_LIST", []);
      commit("SET_NOTICE_LIST_PAGE", 0);
    },

    // 알림 다 읽은척...
    readNoticeList: ({ commit }) => {
      commit("READ_NOTICE_LIST");
    },

    // 알림이 있는지 확인한다.
    checkNotice: ({ rootGetters, dispatch, commit }) => {
      const uid = rootGetters["personalInfo/getUserInfoUserId"];
      const halfDay = new Date(new Date().setDate(new Date().getDate() - 15)); // 15일
      const q = query(
        collection(database, `jourgeois/${uid}/notification`),
        where("timestamp", ">", halfDay)
      );
      onSnapshot(q, (snapshot) => {
        if (snapshot.empty) {
          dispatch("checkUnReadNotice");
          return;
        }

        const docs = snapshot.docChanges();
        for (let i = 0; i < docs.length; i++) {
          if (docs[i].type === "added") {
            dispatch("checkUnReadNotice");
            break;
          } else if (docs[i].type === "modified") {
            dispatch("checkUnReadNotice");
            break;
          }
        }
      });
    },

    // 15일 이내의 알림 중 읽지 않은 알림이 있는지 다시 확인
    checkUnReadNotice: async ({ commit, rootGetters }) => {
      const uid = rootGetters["personalInfo/getUserInfoUserId"];
      const halfDay = new Date(new Date().setDate(new Date().getDate() - 15)); // 15일
      const q = query(
        collection(database, `jourgeois/${uid}/notification`),
        where("timestamp", ">", halfDay),
        where("isRead", "==", false),
        limit(1)
      );
      const querySnapshot = await getDocs(q);

      // 읽지 않은 알림이 없으면
      if (querySnapshot.empty) {
        commit("SET_NOTICE_STATUS", false);
      }
      // 읽지 않은 알림이 있으면
      else {
        commit("SET_NOTICE_STATUS", true);
      }
    },

    // 알림 리스트를 가져온다.
    fetchNoticeList: ({ rootGetters, commit, getters, dispatch }) => {
      dispatch("modal/toggleLoadingStatus", true, { root: true });
      const page = getters["getNoticeListPage"];
      axios({
        url: api.notice.getNoticeList(),
        method: "get",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          page,
        },
      })
        .then((response) => {
          if (!response.data.list) {
            dispatch("modal/toggleLoadingStatus", false, { root: true });
            return;
          }
          // 데이터 추가
          commit("ADD_NOTICE_LIST", response.data.list);
          commit("SET_NOTICE_LIST_PAGE", page + 10);
          dispatch("modal/toggleLoadingStatus", false, { root: true });
        })
        .catch((error) => {
          dispatch("modal/toggleLoadingStatus", false, { root: true });
          if (error.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "notice/fetchNoticeList",
              params: {},
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 알림 읽음 처리 (1개)
    readNotice: ({ rootGetters, dispatch }, data) => {
      const { notiId, type, postId, uid, postType, baseCocktailId } = data;
      axios({
        url: api.notice.readNotice(),
        method: "put",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data: {
          notiId,
        },
      })
        .then(() => {
          // 팔로우 알림일 경우, 해당 유저의 프로필 페이지로 이동한다.
          if (type === "FOLLOW") {
            router.push({
              name: "TheUserProfileView",
              params: { userId: uid },
            });
          }
          // 댓글, 좋아요 알림일 경우 해당 글로 이동한다.
          else {
            if (postType === "Post") {
              // 일반 게시글로 이동
              router.push({
                name: "TheCommunityDescView",
                params: {
                  feedId: postId,
                },
              });
            } else {
              if (baseCocktailId) {
                // 커스텀 칵테일
                router.push({
                  name: "TheCustomCocktailDescView",
                  params: {
                    cocktailId: baseCocktailId,
                    feedId: postId,
                  },
                });
              } else {
                // 슈퍼 커스텀 칵테일
                router.push({
                  name: "TheSuperCustomCocktailDescView",
                  params: {
                    feedId: postId,
                  },
                });
              }
            }
          }
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            error;
          } else {
            // refreshToken 재발급
            const obj = {
              func: "notice/readNotice",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 알림 모두 읽음 처리
    readNoticeAll: ({ rootGetters, dispatch }) => {
      axios({
        url: api.notice.readNoticeAll(),
        method: "put",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
      })
        .then(() => {})
        .catch((error) => {
          if (error.response.status !== 401) {
            error;
          } else {
            // refreshToken 재발급
            const obj = {
              func: "notice/readNoticeAll",
              params: {},
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
