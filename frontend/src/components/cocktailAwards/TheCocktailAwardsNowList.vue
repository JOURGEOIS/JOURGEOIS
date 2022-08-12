<template>
  <div class="the-cocktail-awards-now-list">
    <article
      class="the-cocktail-awards-now-item"
      v-for="item in cocktailAwardsNowList"
      :key="item.postId"
      :cocktailAwardsNowItem="item"
    ></article>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";

const store = useStore();

const cocktailAwardsNowList = computed(
  () => store.getters["cocktailAwards/getCocktailAwardsNowList"]
);
</script>

<style scoped lang="scss"></style>
