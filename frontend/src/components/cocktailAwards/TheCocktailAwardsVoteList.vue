<template>
  <!-- 칵테일 어워즈 투표 리스트 -->
  <div class="the-cocktail-awards-vote-list">
    <article
      class="the-cocktail-awards-vote-item"
      v-for="(item, index) in cocktailAwardsVoteList"
      :key="item.postId"
    >
      <the-cocktail-awards-vote-item
        :cocktail-awards-vote-item="item"
        :cocktail-awards-vote-rank="index"
      >
      </the-cocktail-awards-vote-item>
    </article>
  </div>
</template>

<script setup lang="ts">
import TheCocktailAwardsVoteItem from "@/components/cocktailAwards/TheCocktailAwardsVoteItem.vue";
import { computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

// 데이터 리스트
const cocktailAwardsVoteList = computed(
  () => store.getters["cocktailAwards/getCocktailAwardsVoteList"]
);
</script>

<style scoped lang="scss">
.the-cocktail-awards-vote-list {
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
    aspect-ratio: 2/3;
  }
}
</style>
