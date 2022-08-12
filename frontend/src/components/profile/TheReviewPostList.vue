<template>
  <article v-for="review in userReviewPostData" :key="review.postId">
    <the-review-post-item :review="review"></the-review-post-item>
  </article>
</template>

<script setup lang="ts">
import TheReviewPostItem from '@/components/profile/TheReviewPostItem.vue';
import { computed, onMounted } from '@vue/runtime-core';
import { useRoute } from 'vue-router';
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const userReviewPostData = computed(() => 
  store.getters["profileDesc/getCurrentUserPostReview"]
)

onMounted(() => {
  store.dispatch("profileDesc/getCurrentUserPostReviewData", route.params.userId )
});
</script>

<style scoped lang="scss">
article {
  width: 100%;
  @include flex(column);
  gap: 15px;
}
</style>