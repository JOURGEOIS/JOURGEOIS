<template>
  <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
    칵테일</header-basic
  >
  <div class="cocktail-list-view top-view-no-margin">
    <div class="the-item-container">
      <the-list-item-cocktail
        v-for="(item, idx) in wholeCocktails"
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

// 전체 칵테일 리스트
const wholeCocktails = computed(
  () => store.getters["searchResult/getWholeCocktails"]
);

// 칵테일 누른 경우 칵테일 상세 페이지로 이동
const clickCocktail = (item: Cocktail) => {
  router.push({ name: "TheCocktailDescView", params: { cocktailId: item.id } });
};

const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "searchResult/setWholeCocktail",
  };
  store.dispatch("scroll/handleScroll", data);
};

// 전체 칵테일 추가 함수
const setWholeCocktail = () => {
  store.dispatch("searchResult/setWholeCocktail");
};

onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  setWholeCocktail();
  setTimeout(() => {
    setWholeCocktail();
  }, 100);
});

onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
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
