<template>
  <article v-for="review in userReviewPostData" :key="review.postId">
    <the-review-post-item :review="review"></the-review-post-item>
  </article>
  <section v-if="!isMine">
    <div class="review-post-none" v-if="isPrivate">
      <p><span class="material-icons-outlined">lock</span>비공개 계정입니다.</p>
    </div>
  </section>
</template>

<script setup lang="ts">
import TheReviewPostItem from "@/components/profile/TheReviewPostItem.vue";
import {
  computed,
  onBeforeMount,
  onUnmounted,
  watch,
} from "@vue/runtime-core";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const userReviewPostData = computed(
  () => store.getters["profileDesc/getCurrentUserPostReview"]
);
const userId = computed(() => store.getters["personalInfo/getUserInfoUserId"]);
const userInfo = computed(
  () => store.getters["profileDesc/getCurrentUserData"]
);

const uid = computed(() => userInfo.value.uid)

const isPrivate = computed(() => userInfo.value.isPrivate);

const isMine = computed(() => userInfo.value.userId === userId.value)

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

const paramsUserId = computed(() => route.params.userId);
watch(paramsUserId, () => {
  store.dispatch(
    "profileDesc/getCurrentUserPostReviewData",
    paramsUserId.value
  );
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
  padding: 64px 70px;
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
