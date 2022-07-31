<template>
  <div class="the-cocktail-filter-ingredients">
    <div class="ingredients-container">
    <header-no-hr :prev="true" :success="false" @prevClicked="clickPrev">
      칵테일 상세 정보
    </header-no-hr>
    </div>
  </div>
</template>

<script setup lang="ts">
import { baseAlcohol, liqueur, drinks, ingredients } from "../../assets/filter";
import HeaderNoHr from "@/components/basics/HeaderNoHr.vue";
import { computed } from "vue";
import { useStore } from "vuex";

const store = useStore();
const array = [baseAlcohol, liqueur, drinks, ingredients];

const index = computed(
  () => store.getters["cocktailDesc/getFilterIngredientIndex"]
);

const clickPrev = () => {
  store.dispatch("cocktailDesc/toggleFilterIngredientsComponent", false)
}
</script>

<style scoped lang="scss">
.the-cocktail-filter-ingredients {
  position: absolute;
  top:0;
  height: 100vh;
  width: 100%;
  padding: 0 16px;
  background-color: white;
}

</style>
