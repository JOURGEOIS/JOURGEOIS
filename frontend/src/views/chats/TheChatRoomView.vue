<!-- chat-room 페이지 -->
<template>
  <div class="chat-room-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      채팅
    </header-basic>
    <section
      class="chat-room-section top-view"
      :class="{ active: emojiStatus }"
    >
      <the-chat-room-chat-list></the-chat-room-chat-list>
      <the-chat-room-chat-input
        @click-emoji="clickEmoji"
      ></the-chat-room-chat-input>
    </section>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import TheChatRoomChatList from "@/components/chats/TheChatRoomChatList.vue";
import TheChatRoomChatInput from "@/components/chats/TheChatRoomChatInput.vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { onUnmounted, ref, onMounted, computed, watch } from "vue";
const router = useRouter();
const route = useRoute();
const store = useStore();

// navbar 색깔 부여
store.dispatch("navbar/setNavIconStatus", 0);

// 이모지 on, off
const emojiStatus = ref(false);

const clickEmoji = () => {
  emojiStatus.value = !emojiStatus.value;
};

// 채팅 상대방 id
const currentChatUserId = route.params.userId;

// 현재 채팅 중인 유저 ID를 '현재 채팅 유저 ID'로 저장
store.dispatch("chatRoom/setCurrentChatUserId", currentChatUserId);

// 현재 채팅방 채팅 불러오기
store.dispatch("chatRoom/setChatLogs");

// 리셋 (유저 id, 챗룸 정보)
const resetCurrentChatRoom = {
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
};

onUnmounted(() => {
  store.dispatch("chatRoom/checkChatDetail");
  store.dispatch("chatRoom/resetChatRoomLogs");
  store.dispatch("chatRoom/setCurrentChatRoom", resetCurrentChatRoom);
});

onMounted(() => {
  setTimeout(() => {
    window.scrollTo({ left: 0, top: 100 });
  }, 0);
});
</script>

<style scoped lang="scss">
.chat-room-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  height: 100vh;
  @include accountLayOut;

  .chat-room-section {
    @include flex(column);
    width: 100%;
    height: calc(100% - 184px);
    justify-content: flex-end;
    align-items: center;

    @media #{$tablet} {
      width: 80%;
      height: calc(100% - 216px);
    }

    @media #{$pc} {
      width: 70%;
    }
  }

  .active {
    height: calc(100% - 392px);

    @media #{$tablet} {
      height: calc(100% - 424px);
    }
  }
}
</style>
