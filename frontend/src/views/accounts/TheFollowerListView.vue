<template>
  <div class="follower-user-list-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      팔로우한 유저
    </header-basic>
    <div class="the-follower-container top-view-no-margin">
      <!-- <div class="follower-post-none" v-if="isEmpty">
        <p>팔로우한 유저가 없습니다.</p>
        <p class="emoji">😥</p>
      </div> -->
      <the-follower-item
        v-for="(follower, idx) in followers"
        :key="`follower-${idx}`"
        :follower="follower"
      ></the-follower-item>
    </div>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import TheFollowerItem from "@/components/profile/TheFollowerItem.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { ref, computed, onBeforeMount, onUnmounted } from "@vue/runtime-core";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
const router = useRouter();
const route = useRoute();
const store = useStore();

const followers = computed(() => store.getters["follow/getFollowerUsers"]);
const uid = computed(() => route.params.userId);

// const isEmpty = ref(false);
// setTimeout(() => {
//   if (followers.value.length === 0) {
//     isEmpty.value = true;
//   }
// }, 200);

const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "follow/setFollowerList",
    data: { userId: uid.value },
  };
  store.dispatch("scroll/handleScroll", data);
};

const setFollowerList = (data: object) => {
  store.dispatch("follow/setFollowerList", data);
};

onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  setFollowerList({ userId: uid.value });
  setTimeout(() => {
    setFollowerList({ userId: uid.value });
  }, 500);
});

// 리셋
onUnmounted(() => {
  store.dispatch("follow/resetFollowUserData");
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss">
.follower-user-list-view {
  @include flex(column);
  @include accountLayOut;
  justify-content: flex-start;
  align-items: center;
  .the-follower-container {
    @include flex(column);
    @include flex-center;

    width: 100%;

    margin-top: 1rem;
    .follower-post-none {
      @include flex-center;
      width: 100%;
      margin-top: 120px;
      padding: 64px 16px;
      border-radius: 16px;
      background-color: $white200;
      @include font($fs-main, $fw-bold);
      text-align: center;

      .emoji {
        font-size: $fs-xl;
      }

      @media #{$tablet} {
        @include font($fs-lg, $fw-bold);
        width: 450px;
      }
    }
  }
}
</style>
