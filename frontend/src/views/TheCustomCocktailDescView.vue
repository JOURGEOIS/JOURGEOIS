<template>
  <div class="the-custom-cocktail-desc-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      커스텀 칵테일
    </header-basic>
    <!-- 제목, 사진, 제조법, 설명 등 커스텀 칵테일 내용 -->
    <the-custom-cocktail-desc-body></the-custom-cocktail-desc-body>
    <!-- 댓글 좋아요 공유 부분 -->
    <like-comment-share>
      <template #like>888</template>
      <template #comment>777</template>
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
import TheLikeCommentShare from "@/components/basics/LikeCommentShare.vue";
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

onMounted(() => {
  store.dispatch("customCocktailInfo/setCustomCocktailDetail", {
    feedId,
  });
});

onUnmounted(() => {
  store.dispatch("customCocktailInfo/removeCustomCocktailDetail");
});

const customCocktailInfo = computed(() => {
  return store.getters["customCocktailInfo/getCustomCocktailDetail"];
});
</script>

<style scoped lang="scss">
.the-custom-cocktail-desc-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  .the-custom-cocktail-desc-comment {
    @include flex(column);
    gap: 40px;
    width: 100%;
  }
}
</style>
