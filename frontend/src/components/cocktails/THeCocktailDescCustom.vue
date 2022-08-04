<template>
  <section class="add-custom-cocktail" @click="addCustomCocktail">
    <span class="material-icons add-icon"> add_circle </span>
    <span>커스텀 칵테일 만들기</span>
  </section>
  <section class="custom-cocktail-section">
    <the-list-item-custom-cocktail></the-list-item-custom-cocktail>
    <the-list-item-custom-cocktail></the-list-item-custom-cocktail>
  </section>
</template>

<script setup lang="ts">
import TheListItemCustomCocktail from "@/components/cocktails/TheListItemCustomCocktail.vue";
import { useRoute, useRouter } from "vue-router";
const router = useRouter();
const route = useRoute();

const customCocktails = [];

// 커스텀 칵테일 만들기 클릭
const addCustomCocktail = () => {
  alert("커스텀 칵테일 form view로 이동시켜라");
  // router.push({
  //   name: "TheCustomCocktailFormView",
  //   params: { cocktailId: route.params.cocktailId },
  // });
};
</script>

<style scoped lang="scss">
.add-custom-cocktail {
  @include flex-center;
  gap: 10px;
  @include font-size-navy(18px);
  user-select: none;
  cursor: pointer;

  &:hover {
    color: $navy500;
    .add-icon {
      color: $navy500;
    }
  }

  .add-icon {
    @include font-size-navy(20px);
  }
}

.custom-cocktail-section {
  width: 100%;
  @include flex(column);
  gap: 15px;
}
</style>
