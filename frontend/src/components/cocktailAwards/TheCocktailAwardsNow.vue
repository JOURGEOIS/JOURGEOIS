<template>
  <div></div>
</template>

<script setup lang="ts">
import { onBeforeMount, onUnmounted } from "vue";
import { useStore } from "vuex";
const store = useStore();

onBeforeMount(() => {
  console.log("데이터!");
  store.dispatch("cocktailAwards/getCocktailAwardsNowList");
});

onUnmounted(() => {
  console.log("리셋");
  store.dispatch("cocktailAwards/resetCocktailAwardsList");
});
</script>

<style scoped lang="scss"></style>
