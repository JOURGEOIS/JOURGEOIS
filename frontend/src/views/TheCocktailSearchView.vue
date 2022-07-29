<template>
  <div class="cocktail-search-view">
    <form @submit.prevent="searchComplete">
      <div class="cocktail-search-container">
        <!-- 검색 창 섹션 -->
        <the-cocktail-search-header></the-cocktail-search-header>
        <!-- 처음 보이는 공간 -->
        <the-cocktail-search-init></the-cocktail-search-init>

        <!-- 자동완성 -->
        <section class="auto-complete-section"></section>

        <!-- 검색 버튼 구역 -->
        <the-cocktail-search-button-section></the-cocktail-search-button-section>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import TheCocktailSearchHeader from "@/components/cocktails/TheCocktailSearchHeader.vue";
import TheCocktailSearchInit from "@/components/cocktails/TheCocktailSearchInit.vue";
import TheCocktailSearchButtonSection from "@/components/cocktails/TheCocktailSearchButtonSection.vue";
import { computed, onUnmounted } from "vue";
import { useStore } from "vuex";
const store = useStore();

const searchComplete = () => {
  store.dispatch("cocktailSearch/setRecentSearchWords");
};
</script>

<style scoped lang="scss">
.cocktail-search-view {
  @include accountLayOut;

  form {
    @include flex-center;
    width: 100%;
  }
  .cocktail-search-container {
    width: 100%;
    margin-top: 1rem;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }
  }
}

.title1 {
  @include font(17px, $fw-medium);
  padding-bottom: 10px;
}
</style>
