<template>
  <the-cocktail-awards-vote-list></the-cocktail-awards-vote-list>
</template>

<script setup lang="ts">
import TheCocktailAwardsVoteList from "@/components/cocktailAwards/TheCocktailAwardsVoteList.vue";
import { onBeforeMount, onUnmounted } from "vue";
import { useStore } from "vuex";
const store = useStore();

const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "cocktailAwards/fetchCocktailAwardsVoteList",
    data: {},
  };
  store.dispatch("scroll/handleScroll", data);
};

// 초기 데이터, 무한 스크롤 연동
onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch("cocktailAwards/fetchCocktailAwardsVoteList");
});

// unMounted될 시, 데이터 리셋
onUnmounted(() => {
  store.dispatch("cocktailAwards/resetCocktailAwardsList");
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss"></style>
