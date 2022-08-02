<template class="the-search-result-cocktail">
  <the-list-item-cocktail
    v-for="(item, idx) in searchCocktails"
    :key="idx"
    :data="item"
    @click="clickCocktail(item)"
  ></the-list-item-cocktail>
</template>

<script setup lang="ts">
import TheListItemCocktail from "@/components/cocktails/TheListItemCocktail.vue";
import { computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
const router = useRouter();
const route = useRoute();
const store = useStore();

const searchCocktails = computed(() => {
  return store.getters["searchResult/getSearchCocktails"];
});

interface Cocktail {
  id: number;
  name: string;
  img: string | null;
  alcohol: number | null;
  baseLiquor: string;
}

const clickCocktail = (item: Cocktail) => {
  router.push({ name: "TheCocktailDescView", params: { cocktailId: item.id } });
};
</script>

<style scoped lang="scss"></style>
