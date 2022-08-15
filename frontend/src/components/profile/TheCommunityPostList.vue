<template>
  <article v-for="community in userCommunityPostData" :key="community.postId">
    <the-community-post-item :community="community"></the-community-post-item>
  </article>
</template>

<script setup lang="ts">
import TheCommunityPostItem from "@/components/profile/TheCommunityPostItem.vue";
import { computed, onBeforeMount, onUnmounted } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const userCommunityPostData = computed(
  () => store.getters["profileDesc/getCurrentUserPostCommunity"]
);

// 인피니티 스크롤
const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "profileDesc/getCurrentUserPostCommunityData",
    data: route.params.userId,
  };
  store.dispatch("scroll/handleScroll", data);
};

// 인피니티 스크롤을 연동, 처음 데이터 가져오기
onBeforeMount(() => {
  // store.dispatch("profileDesc/getCurrentUserData", route.params.userId);
  window.addEventListener("scroll", handleScroll);
  store.dispatch(
    "profileDesc/getCurrentUserPostCommunityData",
    route.params.userId
  );
});

// unmount될 때, 페이지와 리스트를 리셋한다.
onUnmounted(() => {
  store.dispatch("profileDesc/resetCurrentUserPost");
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss">
article {
  width: 100%;
  @include flex(column);
  gap: 15px;
}
</style>
