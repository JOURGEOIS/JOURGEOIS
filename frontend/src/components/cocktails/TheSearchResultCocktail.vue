<template class="the-search-result-cocktail">
  <div class="no-content" v-if="!searchCocktailAlls.length">
    검색 결과가 없습니다
  </div>
  <the-list-item-cocktail
    v-for="(item, idx) in searchCocktailAlls"
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

const searchCocktailAlls = computed(() => {
  return store.getters["searchResult/getSearchCocktailAlls"];
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

<style scoped lang="scss">
.no-content {
  text-align: center;
  padding: 30px 0;
  @include font-size-sub(15px);
}
</style>
