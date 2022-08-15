import { RootState } from "../index";
import { Module } from "vuex";
import axios from "axios";
import api from "../../api/api";
import { ChatRoom } from "../../interface";

export interface ChatRoomState {
  currentTab: number;
  chatRoomList: ChatRoom[];
}

export const chatRoom: Module<ChatRoomState, RootState> = {
  namespaced: true,

  state: {
    currentTab: 0,
    chatRoomList: [],
    // followerList: [],
    // followerPage: 0,
  },

  getters: {
    // * 현재 탭 번호
    getCurrentTab: (state) => state.currentTab,
    getChatRoomList: (state) => state.chatRoomList,
  },

  mutations: {
    // * 현재 탭 번호 저장
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },

    SET_CHAT_ROOM_LIST: (state, chatRoomList: ChatRoom[]) => {
      state.chatRoomList = chatRoomList;
    },
  },

  actions: {
    // * 현재 탭 저장
    setCurrentTab: ({ commit }, value: number) => {
      commit("SET_CURRENT_TAB", value);
    },

    // * chatRoomList 가져오기
    setChatRoomList: ({ dispatch, commit, rootGetters }) => {
      axios({
        url: api.chats.chatRoomList(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
      })
        .then((res) => {
          commit("SET_CHAT_ROOM_LIST", res.data);
        })
        .catch((err) => {
          if (err.response.status !== 401) {
            // 실패 팝업
            dispatch("modal/blinkFailModalAppStatus", {}, { root: true });
            console.error(err.response);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "chatRoom/setChatRoomList",
              params: {},
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
