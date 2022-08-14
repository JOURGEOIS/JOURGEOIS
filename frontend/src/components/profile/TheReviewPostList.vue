<template>
  <article v-for="review in userReviewPostData" :key="review.postId">
    <the-review-post-item :review="review"></the-review-post-item>
  </article>
</template>

<script setup lang="ts">
import TheReviewPostItem from '@/components/profile/TheReviewPostItem.vue';
import { computed, onBeforeMount, onUnmounted } from '@vue/runtime-core';
import { useRoute } from 'vue-router';
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const userReviewPostData = computed(() => 
  store.getters["profileDesc/getCurrentUserPostReview"]
)

// 인피니티 스크롤
const handleScroll = (event: any) => {
  const data = {
    event,
    action: "profileDesc/getCurrentUserPostReviewData",
    data: route.params.userId,
  };
  store.dispatch("scroll/handleScroll", data);
};

// 인피니티 스크롤을 연동, 처음 데이터 가져오기
onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch(
    "profileDesc/getCurrentUserPostReviewData",
    route.params.userId
  );
});

// unmount될 때, 페이지와 리스트를 리셋한다.
onUnmounted(() => {
  store.dispatch("profileDesc/resetCurrentUserPost");
});
</script>

<style scoped lang="scss">
article {
  width: 100%;
  @include flex(column);
  gap: 15px;
}
</style>