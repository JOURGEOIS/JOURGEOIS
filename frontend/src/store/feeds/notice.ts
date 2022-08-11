import { Module } from "vuex";
import { database } from "../../plugins/firebase.js";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import router from "../../router";
import { Notice } from "../../interface";
import { where, onSnapshot } from "firebase/firestore";
import {
  collection,
  query,
  orderBy,
  startAfter,
  limit,
  getDocs,
} from "firebase/firestore";

export interface NoticeState {
  startAfter: number;
  noticeStatus: boolean;
  noticeList: Notice[];
}

export const notice: Module<NoticeState, RootState> = {
  namespaced: true,

  state: {
    startAfter: 0,
    noticeStatus: false,
    noticeList: [],
  },

  getters: {
    getStartAfter: (state) => state.startAfter,
    getNoticeStatus: (state) => state.noticeStatus,
    getNoticeList: (state) => state.noticeList,
  },

  mutations: {
    SET_START_AFTER: (state, value: number) => {
      state.startAfter = value;
    },
    SET_NOTICE_STATUS: (state, value: boolean) => {
      state.noticeStatus = value;
    },
    ADD_NOTICE_LIST: (state, value: Notice[]) => {
      state.noticeList.push(...value);
    },
    SET_NOTICE_LIST: (state, value: Notice[]) => {
      state.noticeList = value;
    },
  },

  actions: {
    // 리셋
    resetNoticeList: ({ commit }) => {
      commit("SET_NOTICE_LIST", []);
      commit("SET_START_AFTER", 0);
    },

    // 알림이 있는지 확인한다.
    checkNotice: ({ rootGetters, commit }) => {
      const uid = rootGetters["personalInfo/getUserInfoUserId"];
      const q = query(
        collection(database, `jourgeois/${uid}/notification`),
        orderBy("timestamp", "desc")
      );
      onSnapshot(q, (snapshot) => {
        // 알림이 있는 경우,
        if (snapshot.empty) {
          console.log("비어있음ㅋ");
          commit("SET_NOTICE_STATUS", false);
          return;
        }

        // 알림이 없는 경우
        const docs = snapshot.docChanges();
        for (let i = 0; i < docs.length; i++) {
          if (docs[i].type === "added") {
            commit("SET_NOTICE_STATUS", true);
            break;
          }
        }
      });
    },

    // 알림 리스트를 가져온다.
    getNoticeList: async ({ commit, getters }) => {
      const noticeList: object[] = [];

      // 15일 제한
      const halfDay = new Date(new Date().setDate(new Date().getDate() - 15));

      const next = query(
        collection(database, "jourgeois/21001/notification"),
        where("timestamp", ">", halfDay),
        orderBy("timestamp"),
        startAfter(getters["getStartAfter"]),
        limit(10)
      );

      const querySnapshot = await getDocs(next);

      // 라우터 변수를 마지막 시작 지점으로 변경한다.
      commit(
        " SET_START_AFTER",
        querySnapshot.docs[querySnapshot.docs.length - 1]
      );

      querySnapshot.forEach((doc) => {
        const key = {
          key: doc.id,
        };
        const data = Object.assign(doc.data(), key);
        noticeList.push(data);
      });

      noticeList.reverse();
      commit("ADD_NOTICE_LIST", noticeList);
    },
  },
};
