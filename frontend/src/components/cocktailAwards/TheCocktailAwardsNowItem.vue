<template>
  <div class="cocktail-awards-now-item-card">
    <div
      class="cocktail-awards-now-item-image"
      :style="{ backgroundImage: imageUrl }"
    ></div>
    <p>{{ cocktailAwardsNowItem.title }}</p>
    <div
      class="cocktail-awards-now-item-percentage"
      :style="{ backgroundColor: cocktailAwardsNowColor }"
    >
      {{ cocktailAwardsNowItem.percentage }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { ContestCocktail } from "../../interface";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { computed } from "vue";

const props = defineProps<{
  cocktailAwardsNowItem: ContestCocktail;
  cocktailAwardsNowRank: number;
}>();

const imageUrl = computed(() => {
  return `url(${props.cocktailAwardsNowItem.imgLink})`;
});

const cocktailAwardsNowColor = computed(() => {
  if (props.cocktailAwardsNowRank <= 4) {
    return "#a2a2fc";
  } else {
    return "#c8cfdc";
  }
});
</script>

<style scoped lang="scss">
.cocktail-awards-now-item-card {
  @include flex-xy(center, flex-start);
  flex-wrap: wrap;
  gap: 6px;
  width: 100%;
  height: 100%;
  border-radius: 12px;
  @include feed;
  padding: 0;

  .cocktail-awards-now-item-image {
    align-self: flex-start;
    width: 100%;
    aspect-ratio: 1/1;
    border-radius: 12px 12px 0px 0px;
    background : {
      size: cover;
      position: center center;
    }
  }

  p {
    overflow: hidden;
    text-overflow: ellipsis;
    width: 90%;
    white-space: nowrap;
    text-align: center;
    @include font($fs-main, $fw-regular);
  }

  .cocktail-awards-now-item-percentage {
    width: 90%;
    border-radius: 6px;
    text-align: center;
    color: $white;
    @include font($fs-sm, $fw-regular);
    letter-spacing: $ls-btn;
    padding: 0.3em;
  }
}
</style>

<!-- import ButtonBasic from "@/components/basics/ButtonBasic.vue"; -->
<!-- <button-basic
      button-style="['red', 'long','very-small' ]"
      v-if="!cocktailAwardsNowItem.like"
    >
      투표하기
    </button-basic>
    <button-basic button-style="['sub', 'long','very-small' ]" v-else>
      투표취소
    </button-basic> -->
