<!-- 칵테일 상세 페이지: 레시피 탭 -->
<template>
  <div class="cocktail-desc-recipe-tab">
    <the-cocktail-desc-recipe-materials></the-cocktail-desc-recipe-materials>
    <the-cocktail-desc-recipe-steps></the-cocktail-desc-recipe-steps>
  </div>
</template>

<script setup lang="ts">
import TheCocktailDescRecipeMaterials from "@/components/cocktails/TheCocktailDescRecipeMaterials.vue";
import TheCocktailDescRecipeSteps from "@/components/cocktails/TheCocktailDescRecipeSteps.vue";
</script>

<style scoped lang="scss">
.cocktail-desc-recipe-tab {
  @include flex(column);
  align-items: center;
  gap: 72px;
  width: 100%;
}
</style>
