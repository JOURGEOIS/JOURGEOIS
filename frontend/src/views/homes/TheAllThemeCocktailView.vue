<template>
  <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
    <h1 class="title">
      <div v-if="selectedCategory === 'ALONE'">
        <span class="important">집에서 혼자 </span>즐기는 칵테일
      </div>
      <div v-if="selectedCategory === 'PARTY'">
        <span class="important">친구들과 함께 </span>홈파티 칵테일
      </div>
      <div v-if="selectedCategory === 'LOVE'">
        <span class="important">연인과 함께 </span>분위기 있는 칵테일
      </div>
      <div v-if="selectedCategory === 'SPECIAL'">
        <span class="important">특별한 날엔 </span>특별한 칵테일
      </div>
    </h1></header-basic
  >
  <div class="cocktail-list-view top-view-no-margin">
    <div class="the-item-container">
      <the-list-item-carousel-cocktail
        v-for="(item, idx) in allThemeCocktails"
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
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { computed, onBeforeMount, onUnmounted } from "vue";
import { CarouselCocktail } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

// navbar 색깔 부여
store.dispatch("navbar/setNavIconStatus", 0);

store.dispatch("carousel/setSelectedCategory", route.params.theme);

// 선택된 테마
const selectedCategory = computed(() => {
  return store.getters["carousel/getSelectedCategory"];
});

// 전체 칵테일 리스트
const allThemeCocktails = computed(
  () => store.getters["carousel/getAllThemeCocktails"]
);

// 칵테일 누른 경우 칵테일 상세 페이지로 이동
const clickCocktail = (item: CarouselCocktail) => {
  store.dispatch("carousel/clickShowMoreItem", item);
};

const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "carousel/setAllThemeCocktails",
  };
  store.dispatch("scroll/handleScroll", data);
};

// 전체 칵테일 추가 함수
const setAllThemeCocktails = () => {
  store.dispatch("carousel/setAllThemeCocktails");
};

onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  setAllThemeCocktails();
  setTimeout(() => {
    setAllThemeCocktails();
  }, 100);
});

onUnmounted(() => {
  store.dispatch("carousel/removeAllThemeCocktails");
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
