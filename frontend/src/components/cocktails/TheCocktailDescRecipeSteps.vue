<!-- 칵테일 상세 페이지: 레시피 탭 (레시피) -->
<template>
  <div class="cocktail-desc-steps">
    <!-- 레시피 title -->
    <div class="cocktail-desc-steps-title">
      <p>제작</p>
    </div>

    <!-- 레시피 steps -->
    <div class="cocktail-desc-steps-content">
      <div v-for="(step, index) in steps" :key="`step-${index}`">
        <p class="cocktail-desc-steps-content-index">{{ index + 1 }} 단계</p>
        <p class="cocktail-desc-steps-content-desc">{{ step }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, computed } from "vue";
import { useStore} from "vuex";
const store = useStore();

const cocktailData = computed(
  () => store.getters["cocktailDesc/getCurrentCocktailDataRecipe"]
);

const steps = cocktailData?.value?.split('<>')
</script>

<style scoped lang="scss">
.cocktail-desc-steps {
  @include flex(column);
  gap: 16px;
  width: 90%;
  margin-top: 40px;

  .cocktail-desc-steps-title {
    @include font($fs-main, $fw-medium);
    color: $navy700;
  }

  .cocktail-desc-steps-content {
    @include flex(column);
    justify-content: center;
    gap: 32px;
    .cocktail-desc-steps-content-index {
      @include font($fs-md, $fw-medium);
      color: $navy500;
    }
    .cocktail-desc-steps-content-desc {
      @include font($fs-md, $fw-medium);
      color: $gray100;
    }
  }
}
</style>
