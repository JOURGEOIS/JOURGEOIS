<template>
  <div class="the-custom-cocktail-desc-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      커스텀 칵테일
    </header-basic>
    <!-- 제목, 사진, 제조법, 설명 등 커스텀 칵테일 내용 -->
    <the-custom-cocktail-desc-body></the-custom-cocktail-desc-body>
    <!-- 댓글 좋아요 공유 부분 -->
    <like-comment-share @clickLike="clickLike" :data="{ isLiked: isLiked }">
      <template #like>{{ likeCount }}</template>
      <template #comment>{{ commentCount }}</template>
    </like-comment-share>
    <!-- 댓글 부분 -->
    <section class="the-custom-cocktail-desc-comment">
      <comment-form :page-id="feedId"></comment-form>
      <comment-list :page-id="feedId"></comment-list>
    </section>
  </div>
  <!-- navbar -->
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheCustomCocktailDescBody from "@/components/cocktails/TheCustomCocktailDescBody.vue";
import LikeCommentShare from "@/components/basics/LikeCommentShare.vue";
import CommentForm from "@/components/basics/CommentForm.vue";
import CommentList from "@/components/basics/CommentList.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { reactive, computed, onMounted, onUnmounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { CustomCocktail } from "../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

const originalCocktailId = Number(route.params.cocktailId);
const feedId = Number(route.params.feedId);

const setCustomCocktailDetail = () => {
  store.dispatch("customCocktailInfo/setCustomCocktailDetail", {
    feedId,
  });
};

onMounted(() => {
  setCustomCocktailDetail();
});

onUnmounted(() => {
  store.dispatch("customCocktailInfo/removeCustomCocktailDetail");
});

const customCocktailInfo = computed(() => {
  return store.getters["customCocktailInfo/getCustomCocktailDetail"];
});

// 좋아요 개수
const likeCount = computed(
  () => customCocktailInfo?.value?.customCocktail?.like
);

// 좋아요 상태 확인
const isLiked = computed(
  () => customCocktailInfo?.value?.customCocktail?.ilike
);

// 좋아요를 누른 경우
const clickLike = () => {
  const params = {
    postId: feedId,
    func: "customCocktailInfo/setCustomCocktailDetail",
    data: { feedId },
  };
  store.dispatch("post/toggleLike", params);
};

// 댓글 개수
const commentCount = computed(() => store.getters["comment/getCommentCount"]);
</script>

<style scoped lang="scss">
.the-custom-cocktail-desc-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  .the-custom-cocktail-desc-comment {
    @include flex(column);
    width: 100%;
  }
}
</style>
