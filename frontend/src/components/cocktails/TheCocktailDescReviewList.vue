<template>
  <the-cocktail-desc-review-item
    v-for="(review, id) in cocktailReviewData"
    :key="`main-${id}`"
    :data="review"
  ></the-cocktail-desc-review-item>
</template>

<script setup lang="ts">
import TheCocktailDescReviewItem from "@/components/cocktails/TheCocktailDescReviewItem.vue";
import axios from "axios";
import { computed, onBeforeMount, onUnmounted } from "vue";
import { useStore } from "vuex";

const store = useStore();
const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);

// 칵테일 id
const cocktailData = computed(
  () => store.getters["cocktailDesc/getCurrentCocktailData"]
);
const cocktailId = Number(cocktailData.value.id);

// 칵테일 후기 정보 불러오기
const cocktailReviewData = computed(
  () => store.getters["cocktailReview/getCurrentCocktailReview"]
);

// 스크롤 감지
const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "cocktailReview/getCocktailReview",
    data: cocktailId,
  };
  store.dispatch("scroll/handleScroll", data);
};

// 전체 후기 추가 함수
const getWholeReview = (cocktailId: number) => {
  store.dispatch("cocktailReview/getCocktailReview", cocktailId);
};

onBeforeMount(() => {
  if (isLoggedIn.value) {
    window.addEventListener("scroll", handleScroll);
  }
  getWholeReview(cocktailId);
});

// 리셋
onUnmounted(() => {
  store.dispatch("cocktailReview/resetCocktailReview");
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss"></style>
