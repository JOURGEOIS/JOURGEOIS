import { RootState } from "../index";
import { Module } from "vuex";
import { database } from "../../plugins/firebase.js";
import { where, onSnapshot } from "firebase/firestore";
import { collection, query } from "firebase/firestore";
import axios from "axios";
import api from "../../api/api";
import { ChatRoom, Chat } from "../../interface";

export interface ChatRoomState {
  // * 채팅 목록
  currentTab: number;
  chatRoomList: ChatRoom[];

  // * 채팅방 1:1 상세
  chatLogs: Chat[];
  currentChatUserId: number;
  currentChatRoom: ChatRoom;
}

export const chatRoom: Module<ChatRoomState, RootState> = {
  namespaced: true,

  state: {
    // * 채팅 목록
    currentTab: 0,
    chatRoomList: [],

    // * 채팅방 1:1 상세
    chatLogs: [],
    currentChatUserId: 0,
    currentChatRoom: {
      chatRoomId: "",
      opponent: {
        uid: 0,
        img: "",
        nickname: "",
      },
      lastMessage: {
        chatRoomId: "",
        sender: 0,
        receiver: 0,
        message: "",
        isRead: true,
        timestamp: {
          seconds: 0,
          nanos: 0,
        },
      },
      hasNewMessage: false,
    },
  },

  getters: {
    // * 현재 탭 번호
    getCurrentTab: (state) => state.currentTab,
    getChatRoomList: (state) => state.chatRoomList,

    // * 채팅방 1:1 상세
    getChatLogs: (state) => state.chatLogs,
    getCurrentChatUserId: (state) => state.currentChatUserId,
    getCurrentChatRoom: (state) => state.currentChatRoom,
  },

  mutations: {
    // * 현재 탭 번호 저장
    SET_CURRENT_TAB: (state, value: number) => {
      state.currentTab = value;
    },

    // * 채팅방 리스트 저장
    SET_CHAT_ROOM_LIST: (state, chatRoomList: ChatRoom[]) => {
      state.chatRoomList = chatRoomList;
    },

    // * 채팅방 1:1 메세지 로그 저장
    SET_CHAT_LOGS: (state, chatLogs: Chat[]) => {
      state.chatLogs = chatLogs;
    },

    // * 현재 채팅방에 새 메세지 추가
    ADD_NEW_CHAT: (state, newChat: Chat) => {
      state.chatLogs.unshift(newChat);
    },

    // * 현재 채팅하려는 유저 ID
    SET_CURRENT_CHAT_USER_ID: (state, userId: number) => {
      state.currentChatUserId = userId;
    },

    // * 채팅방 1:1 정보 저장
    SET_CURRENT_CHAT_ROOM: (state, chatRoom: ChatRoom) => {
      state.currentChatRoom = chatRoom;
    },

    // * 채팅방 id 저장
    SET_CURRENT_CHAT_ROOM_ID: (state, chatRoomId: string) => {
      state.currentChatRoom.chatRoomId = chatRoomId;
    },
  },

  actions: {
    // chats room 리셋 (현재 채팅 중인 유저, 채팅 로그)
    resetChatRoomLogs: ({ commit }) => {
      commit("SET_CURRENT_CHAT_USER_ID", 0);
      commit("SET_CHAT_LOGS", []);
    },

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

    // * chatRoomList 지속적으로 확인
    checkChatRoomList: async ({ rootGetters, dispatch }) => {
      // 처음 로그인 할 때 / 21001 $memberId로 변경필요
      // 채팅방 중에 변화(새로운 메세지 발생)가 있는지 체크
      // 채팅방 목록 보는 페이지 mounted 될 때 불러오기!
      const uid = rootGetters["personalInfo/getUserInfoUserId"];
      const q = query(
        collection(database, "juachat"),
        where("users", "array-contains", uid)
      );
      onSnapshot(q, (snapshot) => {
        if (snapshot.empty) {
          console.log("새로운 메세지가 없습니다!");
        }
        const arr = snapshot.docChanges();
        for (let i = 0; i < arr.length; i++) {
          if (arr[i].type === "modified" || arr[i].type === "added") {
            dispatch("setChatRoomList");
            console.log(
              "새로운 채팅방 개설 되었거나 기존 채팅방에 새로운 메세지가 발생한 채팅방 있음!! "
            );
            break;
          }
        }
      });
    },

    // * 현재 채팅방 저장
    setCurrentChatRoom: ({ commit }, chatRoom: ChatRoom) => {
      commit("SET_CURRENT_CHAT_ROOM", chatRoom);
    },

    // * 현재 채팅방 채팅 불러와서 저장
    setChatLogs: ({ dispatch, commit, getters, rootGetters }) => {
      const currentChatRoom = getters["getCurrentChatRoom"];
      const roomId = currentChatRoom.chatRoomId;
      const receiver = getters["getCurrentChatUserId"];
      axios({
        url: api.chats.chatLogs(),
        method: "GET",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          roomId,
          receiver,
        },
      })
        .then((res) => {
          console.log(res.data);
          commit("SET_CHAT_LOGS", res.data);
        })
        .catch((err) => {
          console.error(err);
          if (err.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "chatRoom/setChatLogs",
              params: {},
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // * chatDetail 지속적으로 확인
    checkChatDetail: async ({ commit, getters }) => {
      const currentChatRoom = getters["getCurrentChatRoom"];
      const roomId = currentChatRoom.chatRoomId;

      // 채팅방 클리시 getMessageList api 호출 전에 firstLoad를 false로 바꿔주세요.
      let firstLoad = false;
      const q = query(collection(database, `juachat/${roomId}/messages`));
      onSnapshot(q, (snapshot) => {
        if (snapshot.empty) {
          // 변화 없음
        }
        const arr = snapshot.docChanges();
        for (let i = 0; i < arr.length; i++) {
          // 채팅방에 새로운 메세지가 추가되면 true
          if (arr[i].type === "added") {
            if (firstLoad === false) {
              console.log("처음 로딩");
              firstLoad = true;
              return;
            }
            // 현재 채팅방에 새 메세지 추가
            commit("ADD_NEW_CHAT", arr[i].doc.data());
            console.log("새로운 메세지 있음!!");
            break;
          }
        }
      });
    },

    // * 현재 채팅하려는 유저 ID 저장
    setCurrentChatUserId: ({ commit }, userId: number) => {
      commit("SET_CURRENT_CHAT_USER_ID", userId);
    },

    // * 새 메세지 전송
    sendNewChat: (
      { dispatch, commit, getters, rootGetters },
      { receiver, message }
    ) => {
      const roomId = getters["getCurrentChatRoom"].chatRoomId;
      const data: any = { receiver, message };
      if (roomId) {
        data.chatRoomId = roomId;
      }
      axios({
        url: api.chats.chatLogs(),
        method: "POST",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        data,
      })
        .then((response) => {
          // 프로필 페이지에서 바로 메시지 보냈고 이전 채팅 로그가 없는 경우
          // res.data(새로 만들어진 chatRoomId)를 기반으로 chatRoomId에 저장
          // checkChatDetail 함수에서 chatRoomId를 기반으로 계속 chatLogs 누적
          if (!roomId) {
            commit("SET_CURRENT_CHAT_ROOM_ID", response.data);
          }
        })
        .catch((error) => {
          console.log(error);
          if (error.response.status !== 401) {
          } else {
            // refreshToken 재발급
            const obj = {
              func: "chatRoom/sendNewChat",
              params: { receiver, message },
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
