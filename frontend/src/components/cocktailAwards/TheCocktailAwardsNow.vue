<template>
  <the-cocktail-awards-now-list></the-cocktail-awards-now-list>
</template>

<script setup lang="ts">
import { onBeforeMount, onUnmounted } from "vue";
import TheCocktailAwardsNowList from "@/components/cocktailAwards/TheCocktailAwardsNowList.vue";
import { useStore } from "vuex";
const store = useStore();

const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "cocktailAwards/fetchCocktailAwardsNowList",
    data: {},
  };
  store.dispatch("scroll/handleScroll", data);
};

// 초기 데이터, 무한 스크롤 연동
onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch("cocktailAwards/fetchCocktailAwardsNowList");
});

// unMounted될 시, 데이터 리셋
onUnmounted(() => {
  store.dispatch("cocktailAwards/resetCocktailAwardsList");
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss"></style>
