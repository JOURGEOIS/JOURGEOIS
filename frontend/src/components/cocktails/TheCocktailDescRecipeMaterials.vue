<!-- 칵테일 상세 페이지: 레시피 탭 (재료) -->
<template>
  <div class="cocktail-desc-materials">
    <!-- 칵테일 재료 title -->
    <div class="cocktail-desc-materials-title">
      <p>재료</p>
      <!-- 더보기를 누를 시, 칵테일 재료를 모두 볼 수 있다. -->
      <p v-if="showMore" @click="clickShowMore">더보기</p>
    </div>

    <!-- 칵테일 재료 나열 -->
    <div class="cocktail-desc-materials-main" v-if="showMore">
      <div
        class="cocktail-desc-materials-item"
        v-for="(ingredient, index) in cocktailIngredientsPreview"
        :key="`main-${index}`"
      >
        <round-image
          :round-image="{ image: ingredient[1], width: '90px' }"
        ></round-image>
        <p>{{ ingredient[0] }}</p>
      </div>
    </div>
    <div class="cocktail-desc-materials-main" v-if="!showMore">
      <div
        class="cocktail-desc-materials-item"
        v-for="(ingredient, index) in cocktailIngredients"
        :key="`main-${index}`"
      >
        <round-image
          :round-image="{ image: ingredient[1], width: '90px' }"
        ></round-image>
        <p>{{ ingredient[0] }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { ref, reactive, computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

// 칵테일 데이터
const cocktailData = computed(
  () => store.getters["cocktailDesc/getCurrentCocktailDataIngredients"]
);

// 칵테일 재료 (더보기를 누른 경우, 모든 재료를 보여준다.)
const cocktailIngredients: string[] = cocktailData.value.map((item: string) => {
  return item.split(",");
});

// 더보기를 안눌렀을 경우, 화면 크기에 따라 보여주는 개수가 다르다.
const cocktailIngredientsPreview = computed(() => {
  if (window.innerWidth < 768) {
    return cocktailIngredients.slice(0, 3);
  } else {
    return cocktailIngredients.slice(0, 5);
  }
});

// 더보기 상태
const showMore = ref(true);

// 더보기 클릭
const clickShowMore = () => {
  showMore.value = false;
};
</script>

<style scoped lang="scss">
.cocktail-desc-materials {
  @include flex(column);
  gap: 16px;
  width: 90%;
  .cocktail-desc-materials-title {
    @include flex-xy(space-between, center);
    margin-bottom: 18px;
    p {
      &:first-child {
        @include font($fs-main, $fw-medium);
        color: $navy700;
      }
      &:nth-child(2) {
        @include font($fs-md, $fw-medium);
        color: $white400;
        cursor: pointer;
      }
    }
  }

  .cocktail-desc-materials-main {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(33.33333%, auto));
    justify-items: center;
    justify-content: center;
    row-gap: 32px;
    width: 100%;

    p {
      width: 90px;
      text-align: center;
      @include font($fs-sm, $fw-regular);
      color: $main-color;
    }

    @media #{$tablet} {
      grid-template-columns: repeat(auto-fill, minmax(25%, auto));
    }
    @media #{$pc} {
      grid-template-columns: repeat(auto-fill, minmax(20%, auto));
    }
  }
}
</style>
