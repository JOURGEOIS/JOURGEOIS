<template>
  <div class="the-cocktail-desc-review">
    <the-cocktail-desc-review-form></the-cocktail-desc-review-form>
    <the-cocktail-desc-review-list></the-cocktail-desc-review-list>
  </div>
</template>

<script setup lang="ts">
import TheCocktailDescReviewForm from "@/components/cocktails/TheCocktailDescReviewForm.vue";
import TheCocktailDescReviewList from "@/components/cocktails/TheCocktailDescReviewList.vue";
</script>

<style scoped lang="scss">
.the-cocktail-desc-review {
  width: 100%;
  @include flex(column);
  gap: 24px;
}
</style>
