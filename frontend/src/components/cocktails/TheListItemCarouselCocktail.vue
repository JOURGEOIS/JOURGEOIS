<template>
  <div class="the-list-item-cocktail">
    <round-image :round-image="cocktailImage"></round-image>
    <div class="cocktail-info-text">
      <h1 class="cocktail-name">{{ data.title }}</h1>
      <p class="cocktail-base-liquor" v-if="data.base">
        {{ data.base }} 베이스
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { CarouselCocktail } from "../../interface";

const props = defineProps<{
  data: CarouselCocktail;
}>();

// 칵테일 이미지
const cocktailImage = {
  image: props.data.img,
  width: "50px",
};
</script>

<style scoped lang="scss">
.the-list-item-cocktail {
  @include flex-xy(flex-start, center);
  padding: 10px;
  gap: 10px;
  border-bottom: 1px solid $seperate-color;
  @include list-hover;

  .cocktail-info-text {
    @include flex(column);
    gap: 3px;

    .cocktail-name {
      @include font(15px, $fw-medium);
    }
    .cocktail-base-liquor {
      @include font-size-sub(11px);
    }
  }
}
</style>
