<template>
  <!-- 칵테일 어워즈 현황 리스트 -->
  <div class="the-cocktail-awards-now-list">
    <!-- 칵테일 어워즈 현황 article -->
    <article
      class="the-cocktail-awards-now-item"
      v-for="(item, index) in cocktailAwardsNowList"
      :key="item.postId"
    >
      <div class="the-cocktail-awards-now-rank" v-if="index <= 4">
        {{ `${index + 1}등` }}
      </div>
      <!-- item -->
      <the-cocktail-awards-now-item
        :cocktail-awards-now-item="item"
        :cocktail-awards-now-rank="index"
      ></the-cocktail-awards-now-item>
    </article>
  </div>
</template>

<script setup lang="ts">
import TheCocktailAwardsNowItem from "@/components/cocktailAwards/TheCocktailAwardsNowItem.vue";
import { computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

// 데이터 리스트
const cocktailAwardsNowList = computed(
  () => store.getters["cocktailAwards/getCocktailAwardsNowList"]
);
</script>

<style scoped lang="scss">
.the-cocktail-awards-now-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(50%, auto));
  justify-items: center;
  justify-content: center;
  align-items: center;
  align-content: center;
  row-gap: 32px;
  width: 100%;

  @media #{$tablet} {
    grid-template-columns: repeat(auto-fill, minmax(33.33333%, auto));
  }

  article {
    position: relative;
    width: 80%;
    aspect-ratio: 1/1.7;

    .the-cocktail-awards-now-rank {
      position: absolute;
      right: -0.1px;
      padding: 0.7em;
      border-radius: 0px 10px 0px 12px;
      background-color: $red500;
      color: $white;
      @include font($fs-sm, $fw-medium);
      letter-spacing: $ls-btn;
    }
  }
}
</style>
