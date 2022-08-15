<template>
  <div class="follower-user-list-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      팔로우한 유저
    </header-basic>
    <div class="the-follower-container top-view-no-margin">
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
import TheFollowerItem from '@/components/profile/TheFollowerItem.vue';
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { computed, onBeforeMount, onUnmounted } from '@vue/runtime-core';
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
const router = useRouter();
const route = useRoute();
const store = useStore();

const followers = computed(() => store.getters["follow/getFollowerUsers"])
const userInfo = computed(() => store.getters['profileDesc/getCurrentUserData'])
const uid = computed(() => userInfo.value.uid)

const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "follow/setFollowerList",
    data: { userId: uid.value },
  };
  store.dispatch("scroll/handleScroll", data);
};

const setFollowerList = (data: object) => {
  store.dispatch("follow/setFollowerList", data)
}

onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  setFollowerList({ userId: uid.value });
  setTimeout(() => {
    setFollowerList({ userId: uid.value });
  }, 100);
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
    width: 100%;

    margin-top: 1rem;
  }
}
</style>