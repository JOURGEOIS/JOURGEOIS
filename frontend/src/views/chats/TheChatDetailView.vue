<template>
  <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
    채팅</header-basic
  >
  <div class="chat-detail-view top-view-no-margin"></div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { computed, onBeforeMount } from "vue";
import { Chat } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

// navbar 색깔 부여
store.dispatch("navbar/setNavIconStatus", 0);

const currentChatUserId = route.params.userId;
// 현재 프로필 유저 ID를 '현재 채팅 유저 ID'로 저장
store.dispatch("chatRoom/setCurrentChatUserId", currentChatUserId);
// 현재 채팅방 채팅 불러오기
store.dispatch("chatRoom/setChatLogs");
</script>

<style scoped lang="scss">
.chat-detail-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
}
</style>
