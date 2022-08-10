<template>
  <div class="liked-user-list-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      좋아요한 유저</header-basic
    >
    <div class="the-item-container top-view-no-margin">
      <the-list-item-user
        v-for="(item, idx) in likedUsers"
        :key="idx"
        :data="item"
        @click="clickUser(item)"
      ></the-list-item-user>
    </div>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import TheListItemUser from "@/components/cocktails/TheListItemUser.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import axios from "axios";
import api from "../api/api";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { computed, onBeforeMount } from "vue";
import { User } from "../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

const feedId = route.params.feedId;
const handleScroll = (event: any) => {
  const data = {
    event,
    action: "post/setLikedUsers",
    data: { postId: feedId },
  };
  store.dispatch("scroll/handleScroll", data);
};

// 좋아요한 유저들 리스트
const likedUsers = computed(() => store.getters["post/getLikedUsers"]);

// 유저 아이템을 누른 경우 유저 페이지로 이동
const clickUser = (item: User) => {
  // 유저 프로필 페이지로 이동시켜라
  alert("유저 프로필 페이지로 이동시켜라");
  // router.push({ name: "TheProfileView", params: { userId: item.uid } });
};

// 전체 좋아요 유저 추가 함수
const setLikedUsers = (data: object) => {
  store.dispatch("post/setLikedUsers", data);
};

onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  setLikedUsers({ postId: feedId });
  setTimeout(() => {
    setLikedUsers({ postId: feedId });
  }, 100);
});
</script>

<style scoped lang="scss">
.liked-user-list-view {
  @include flex(column);
  @include accountLayOut;
  justify-content: flex-start;
  align-items: center;
  .the-item-container {
    @include flex(column);
    width: 100%;

    margin-top: 1rem;
  }
}
</style>
