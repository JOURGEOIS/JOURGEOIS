<template class="the-search-result-user">
  <the-list-item-cocktail
    v-for="(item, idx) in searchCocktails"
    :key="idx"
    :data="item"
  ></the-list-item-cocktail>
</template>

<script setup lang="ts">
import TheListItemCocktail from "@/components/cocktails/TheListItemCocktail.vue";
import { computed } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const searchCocktails = computed(() => {
  return store.getters["searchResult/getSearchCocktails"];
});
</script>

<style scoped lang="scss"></style>
