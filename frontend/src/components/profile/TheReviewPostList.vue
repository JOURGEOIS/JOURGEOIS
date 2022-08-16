<template>
  <section>
    <div class="review-post-none" v-if="isEmpty">
      <p><span class="material-icons-outlined">lock</span>비공개 계정입니다.</p>
    </div>
    <article v-for="review in userReviewPostData" :key="review.postId">
      <the-review-post-item :review="review"></the-review-post-item>
    </article>
  </section>
</template>

<script setup lang="ts">
import TheReviewPostItem from "@/components/profile/TheReviewPostItem.vue";
import { ref, computed, onBeforeMount, onUnmounted } from "@vue/runtime-core";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const userReviewPostData = computed(
  () => store.getters["profileDesc/getCurrentUserPostReview"]
);

const isEmpty = ref(false);
setTimeout(() => {
  if (userReviewPostData.value.length === 0) {
    isEmpty.value = true;
  }
}, 200);

// 인피니티 스크롤
const handleScroll = (event: Event) => {
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
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss">
article {
  width: 100%;
  @include flex(column);
  gap: 15px;
}
.review-post-none {
  width: 100%;
  padding: 64px 16px;
  border-radius: 16px;
  background-color: $white200;
  @include font($fs-main, $fw-bold);
  text-align: center;
  p {
      @include flex-center;
    }

  @media #{$tablet} {
    @include font($fs-lg, $fw-bold);
    width: 450px;
  }
}
</style>
