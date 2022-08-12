<template>
  <!-- 칵테일 어워즈 현황 리스트 -->
  <div class="the-cocktail-awards-now-list">
    <!-- 칵테일 어워즈 현황 아이템 -->
    <article
      class="the-cocktail-awards-now-item"
      v-for="item in cocktailAwardsNowList"
      :key="item.postId"
      :cocktailAwardsNowItem="item"
    ></article>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

// 데이터 리스트
const cocktailAwardsNowList = computed(
  () => store.getters["cocktailAwards/getCocktailAwardsNowList"]
);
</script>

<style scoped lang="scss"></style>
