<template>
  <the-cocktail-awards-vote-list></the-cocktail-awards-vote-list>
</template>

<script setup lang="ts">
import TheCocktailAwardsVoteList from "@/components/cocktailAwards/TheCocktailAwardsVoteList.vue";
import { onBeforeMount, onUnmounted, onMounted } from "vue";
import { useStore } from "vuex";
const store = useStore();

const handleScroll = (event: any) => {
  const data = {
    event,
    action: "cocktailAwards/getCocktailAwardsVoteList",
    data: {},
  };
  store.dispatch("scroll/handleScroll", data);
};

// 초기 데이터, 무한 스크롤 연동
onMounted(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch("cocktailAwards/getCocktailAwardsVoteList");
});

// unMounted될 시, 데이터 리셋
onUnmounted(() => {
  store.dispatch("cocktailAwards/resetCocktailAwardsList");
});
</script>

<style scoped lang="scss"></style>
