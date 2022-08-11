<template>
  <div class="carousel">
    <div
      class="carousel-item"
      v-for="item in cocktails"
      :key="item.cocktailId"
      @click="clickCarouselItem(item.cocktailId)"
    >
      <div
        class="carousel-img"
        :style="{ backgroundImage: `url(${item.img})` }"
      ></div>
      <div class="carousel-text">{{ item.title }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { CarouselCocktail } from "../../interface";
const router = useRouter();
const store = useStore();

const props = defineProps<{
  data: {
    setCarouselFunc: string;
    getCarouselFunc: string;
  };
}>();

const setCarouselFunc: string = props.data.setCarouselFunc;
const getCarouselFunc: string = props.data.getCarouselFunc;

store.dispatch(`carousel/${setCarouselFunc}`);
const cocktails = computed(() => store.getters[`carousel/${getCarouselFunc}`]);

// 요소 & 사이즈
const clickCarouselItem = (cocktailId: number) => {
  console.log(cocktailId);
};
</script>

<style scoped lang="scss">
@include scrollbar;

.carousel {
  display: flex;
  gap: 5px;
  width: calc(100% + 40px);
  padding: 0 16px;
  margin: 0 -20px;
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;
  scroll-snap-type: x mandatory;

  .carousel-item {
    @include flex(column);
    padding-bottom: 10px;
    flex: 0 0 170px;
    gap: 5px;
    @include for-click;

    .carousel-img {
      // width: 150px;
      // height: 150px;
      aspect-ratio: 1/1;
      background : {
        size: cover;
        position: center center;
      }
      border-radius: 5px;
    }
    .carousel-text {
      width: 100%;
      @include font-size-sub(12px);
    }
  }
}
</style>
