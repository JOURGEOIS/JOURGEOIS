<template>
  <div class="liked-user-list-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      좋아요한 유저
    </header-basic>
    <div class="the-item-container top-view-no-margin">
      <div class="like-empty" v-if="isEmpty">
        <p>해당 게시물의 좋아요 유저가 없습니다</p>
        <p class="emoji">😥</p>
      </div>
      <the-list-item-user
        v-else
        v-for="(item, idx) in likedUsers"
        :key="idx"
        :data="item"
      ></the-list-item-user>
    </div>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import TheListItemUser from "@/components/cocktails/TheListItemUser.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { ref, computed, onBeforeMount, onUnmounted } from "vue";
import { User } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

const isEmpty = ref(false);
setTimeout(() => {
  if (likedUsers.value.length === 0) {
    isEmpty.value = true;
  }
}, 200);

// navbar 색깔 부여
store.dispatch("navbar/setNavIconStatus", 3);

const feedId = route.params.feedId;
const handleScroll = (event: Event) => {
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
  router.push({ name: "TheUserProfileView", params: { userId: item.uid } });
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

// 리셋
onUnmounted(() => {
  store.dispatch("post/resetLikedUserData");
  window.removeEventListener("scroll", handleScroll);
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

.like-empty {
  width: 100%;
  margin-top: 120px;
  padding: 64px 16px;
  border-radius: 16px;
  background-color: $white200;
  @include font($fs-main, $fw-bold);
  align-self: center;
  text-align: center;

  .emoji {
    font-size: $fs-xl;
  }

  @media #{$tablet} {
    @include font($fs-lg, $fw-bold);
    width: 450px;
  }
}
</style>
