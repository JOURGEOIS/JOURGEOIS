<template>
  <div class="cocktail-search-view">
    <form @submit.prevent="searchComplete">
      <div class="cocktail-search-container">
        <!-- 검색 창 섹션 -->
        <the-cocktail-search-header></the-cocktail-search-header>
        <!-- 처음 보이는 공간 -->
        <the-cocktail-search-init
          v-if="!searchInputValue"
        ></the-cocktail-search-init>

        <!-- 자동완성 -->
        <the-auto-complete v-if="searchInputValue"></the-auto-complete>

        <!-- 검색 버튼 구역 -->
        <the-cocktail-search-button-section></the-cocktail-search-button-section>
      </div>
    </form>
  </div>
  <the-cocktail-search-filter v-if="filterStatus"></the-cocktail-search-filter>
</template>

<script setup lang="ts">
import TheCocktailSearchHeader from "@/components/cocktails/TheCocktailSearchHeader.vue";
import TheCocktailSearchInit from "@/components/cocktails/TheCocktailSearchInit.vue";
import TheAutoComplete from "@/components/cocktails/TheAutoComplete.vue";
import TheCocktailSearchButtonSection from "@/components/cocktails/TheCocktailSearchButtonSection.vue";
import TheCocktailSearchFilter from "@/components/cocktails/TheCocktailSearchFilter.vue";
import { computed, onUnmounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import TheSearchResultCocktailVue from "../components/cocktails/TheSearchResultCocktail.vue";
const router = useRouter();
const store = useStore();

const searchInputValue = computed(() => {
  return store.getters["cocktailSearch/getSearchInputValue"];
});

const searchComplete = () => {
  store.dispatch("cocktailSearch/setRecentSearchWords");
  // 검색 결과 view로 이동
  router.push({
    name: "TheSearchResultView",
    params: { searchValue: searchInputValue.value },
  });
};

const filterStatus = computed(
  () => store.getters["cocktailSearch/getFilterStatus"]
);
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
