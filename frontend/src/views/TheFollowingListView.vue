<template>
  <div class="followee-user-list-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      팔로잉한 유저
    </header-basic>
    <div class="the-followee-container top-view-no-margin">
      <the-followee-item
        v-for="(followee, idx) in followees"
        :key="`followee-${idx}`"
        :followee="followee"
      ></the-followee-item>
    </div>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import TheFolloweeItem from '@/components/profile/TheFolloweeItem.vue';
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { computed, onBeforeMount, onUnmounted } from '@vue/runtime-core';
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
const router = useRouter();
const route = useRoute();
const store = useStore();

const followees = computed(() => store.getters["follow/getFolloweeUsers"])
const uid = computed(() => route.params.userId)

const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "follow/setFolloweeList",
    data: { userId: uid.value },
  };
  store.dispatch("scroll/handleScroll", data);
};

const setFolloweeList = (data: object) => {
  store.dispatch("follow/setFolloweeList", data)
}

onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  setFolloweeList({ userId: uid.value });
  setTimeout(() => {
    setFolloweeList({ userId: uid.value });
  }, 100);
});

// 리셋
onUnmounted(() => {
  store.dispatch("follow/resetFollowUserData");
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss">
.followee-user-list-view {
  @include flex(column);
  @include accountLayOut;
  justify-content: flex-start;
  align-items: center;
  .the-followee-container {
    @include flex(column);
    width: 100%;

    margin-top: 1rem;
  }
}
</style>