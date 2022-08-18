<template>
  <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
    <h1 class="title">
      주류주아 <span class="important">HOT</span> 칵테일
    </h1></header-basic
  >
  <div class="cocktail-list-view top-view-no-margin">
    <div class="the-item-container">
      <the-list-item-carousel-cocktail
        v-for="(item, idx) in allHotCocktails"
        :key="idx"
        :data="item"
        @click="clickCocktail(item)"
      ></the-list-item-carousel-cocktail>
    </div>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import TheListItemCarouselCocktail from "@/components/cocktails/TheListItemCarouselCocktail.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { computed, onBeforeMount, onUnmounted } from "vue";
import { CarouselCocktail } from "../../interface";
const router = useRouter();
const store = useStore();

// navbar 색깔 부여
store.dispatch("navbar/setNavIconStatus", 0);

// 전체 칵테일 리스트
const allHotCocktails = computed(
  () => store.getters["carousel/getAllHotCocktails"]
);

// 칵테일 누른 경우 칵테일 상세 페이지로 이동
const clickCocktail = (item: CarouselCocktail) => {
  store.dispatch("carousel/clickShowMoreItem", item);
};

const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "carousel/setAllHotCocktails",
  };
  store.dispatch("scroll/handleScroll", data);
};

// 전체 칵테일 추가 함수
const setAllHotCocktails = () => {
  store.dispatch("carousel/setAllHotCocktails");
};

onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  setAllHotCocktails();
});

onUnmounted(() => {
  store.dispatch("carousel/removeAllHotCocktails");
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

.title {
  @include font(20px, $fw-medium);

  .important {
    @include font(20px, $fw-bold);
    color: $red-color;
  }
}
</style>
