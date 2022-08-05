<template>
  <the-cocktail-desc-review-item
    v-for="(review, id) in cocktailReviewData"
    :key="`main-${id}`"
    :data="review"
  ></the-cocktail-desc-review-item>
</template>

<script setup lang="ts">
import TheCocktailDescReviewItem from '@/components/cocktails/TheCocktailDescReviewItem.vue'
import { ref, reactive, computed } from 'vue'
import { useStore } from 'vuex'

const store = useStore()

// 칵테일 후기 정보 불러오기
const cocktailReviewData = computed(
  () => store.getters['cocktailReview/getCurrentCocktailReview'],
)
const getReview = (cocktailId: number) =>
  store.dispatch('cocktailReview/getCocktailReview', cocktailId)
// 칵테일 id
const cocktailData = computed(
  () => store.getters['cocktailDesc/getCurrentCocktailData'],
)
const cocktailId = Number(cocktailData.value.id)

getReview(cocktailId)
</script>

<style scoped lang="scss"></style>
