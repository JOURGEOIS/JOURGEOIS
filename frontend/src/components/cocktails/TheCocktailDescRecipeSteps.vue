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
import { reactive } from "vue";

const steps = reactive([
  "얼음을 채운 하이볼 글라스에 보드카 1/2 oz를 넣어주세요. ",
  "데킬라, 진, 럼을 1/2 oz 넣어주세요. ",
  "콜라 1/32 oz를 넣어주고 레몬으로 장식하고 마무리해요.",
]);
</script>

<style scoped lang="scss">
.cocktail-desc-steps {
  @include flex(column);
  gap: 16px;
  width: 90%;
  margin-top: 16px;

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
