<!-- 필터 검색 결과 view -->
<template>
  <div class="cocktail-list-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      검색 결과
    </header-basic>
    <div class="the-item-container">
      <the-list-item-cocktail
        v-for="(item, idx) in searchFilterList"
        :key="idx"
        :data="item"
        @click="clickCocktail(item)"
      ></the-list-item-cocktail>
    </div>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import TheListItemCocktail from "@/components/cocktails/TheListItemCocktail.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import axios from "axios";
import api from "../api/api";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { computed, onBeforeMount, onUnmounted } from "vue";
const router = useRouter();
const store = useStore();

// 칵테일 interface
interface Cocktail {
  id: number;
  name: string;
  img: string | null;
  alcohol: number | null;
  baseLiquor: string;
}

// 보여줄 칵테일 리스트 불러오기
const searchFilterList = computed(
  () => store.getters["searchResult/getSearchFilter"]
);

// 칵테일 누른 경우 칵테일 상세 페이지로 이동
const clickCocktail = (item: Cocktail) => {
  router.push({ name: "TheCocktailDescView", params: { cocktailId: item.id } });
};

// 스크롤 함수
const handleScroll = (event: any) => {
  const data = {
    event,
    action: "searchResult/setSearchFilter",
  };
  store.dispatch("scroll/handleScroll", data);
};

// 전체 칵테일 추가 함수
const setSearchFilter = () => {
  store.dispatch("searchResult/setSearchFilter");
};

// 칵테일 불러오기
onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch("cocktailSearch/submitSearchFilter");
  setSearchFilter();
  setTimeout(() => {
    setSearchFilter();
  }, 1000);
});

// 떠날 때, 정보 제거
onUnmounted(() => {
  store.dispatch("searchResult/removeSearchFilter");
});
</script>

<style scoped lang="scss">
.cocktail-list-view {
  @include flex(column);
  @include accountLayOut;
  justify-content: flex-start;
  align-items: center;
  .the-item-container {
    @include flex(column);
    width: 100%;

    margin-top: 1rem;
  }
}
</style>
