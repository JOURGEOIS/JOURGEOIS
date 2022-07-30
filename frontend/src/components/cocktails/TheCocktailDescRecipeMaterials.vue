<!-- 칵테일 상세 페이지: 레시피 탭 (재료) -->
<template>
  <div class="cocktail-desc-materials">
    <!-- 칵테일 재료 title -->
    <div class="cocktail-desc-materials-title">
      <p>재료</p>
      <!-- 더보기를 누를 시, 칵테일 재료를 모두 볼 수 있다. -->
      <p v-if="showMore">더보기</p>
    </div>

    <!-- 칵테일 재료 나열 -->
    <div class="cocktail-desc-materials-main">
      <div
        class="cocktail-desc-materials-item"
        v-for="(material, index) in materials"
        :key="`main-${index}`"
      >
        <round-image
          :round-image="{ image: material.image, width: '90px' }"
        ></round-image>
        <p>{{ material.name }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { ref, reactive } from "vue";

// 더보기 상태
const showMore = ref(true);

// 재료
const materials = reactive([
  {
    image:
      "https://p.turbosquid.com/ts-thumb/X2/ai5JUA/Yz/247baileys_irish_cream_whiskey0001/jpg/1640734371/600x600/fit_q87/bb55ec9121ee3f934bda4f4058e35bcfedd71ef9/247baileys_irish_cream_whiskey0001.jpg",
    name: "베일리스 아이리쉬 크림",
  },
  {
    image:
      "https://p.turbosquid.com/ts-thumb/X2/ai5JUA/Yz/247baileys_irish_cream_whiskey0001/jpg/1640734371/600x600/fit_q87/bb55ec9121ee3f934bda4f4058e35bcfedd71ef9/247baileys_irish_cream_whiskey0001.jpg",
    name: "베일리스 아이리쉬 크림",
  },
  {
    image:
      "https://p.turbosquid.com/ts-thumb/X2/ai5JUA/Yz/247baileys_irish_cream_whiskey0001/jpg/1640734371/600x600/fit_q87/bb55ec9121ee3f934bda4f4058e35bcfedd71ef9/247baileys_irish_cream_whiskey0001.jpg",
    name: "베일리스 아이리쉬 크림",
  },
  {
    image:
      "https://p.turbosquid.com/ts-thumb/X2/ai5JUA/Yz/247baileys_irish_cream_whiskey0001/jpg/1640734371/600x600/fit_q87/bb55ec9121ee3f934bda4f4058e35bcfedd71ef9/247baileys_irish_cream_whiskey0001.jpg",
    name: "베일리스 아이리쉬 크림",
  },
]);
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
      }
    }
  }

  .cocktail-desc-materials-main {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(33.33333%, auto));
    justify-items: center;
    justify-content: center;
    row-gap: 16px;
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
