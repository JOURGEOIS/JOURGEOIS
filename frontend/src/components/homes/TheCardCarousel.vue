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
import { useRouter } from "vue-router";
const router = useRouter();
interface carouselCocktail {
  cocktailId: number;
  title: string;
  img: string;
  base: string;
  abv: number;
}
const cocktails: carouselCocktail[] = [
  {
    cocktailId: 1,
    title: "마!가리타라라라라",
    img: "https://news.imaeil.com/photos/2020/01/21/2020012117452367900_l.jpg",
    base: "마가리타",
    abv: 13,
  },
  {
    cocktailId: 2,
    title: "마!가리릴리릴리",
    img: "https://www.noblesse.com/shop/data/m/editor_new/2020/12/17/e44b2891deae1b8aimage1.jpg",
    base: "레몬주스",
    abv: 15,
  },
  {
    cocktailId: 3,
    title: "마!가리릴리릴리",
    img: "https://www.noblesse.com/shop/data/m/editor_new/2020/12/17/e44b2891deae1b8aimage1.jpg",
    base: "레몬주스",
    abv: 17,
  },
  {
    cocktailId: 4,
    title: "마!가리타라라라라라라라라라라라라",
    img: "https://news.imaeil.com/photos/2020/01/21/2020012117452367900_l.jpg",
    base: "마가리타",
    abv: 12,
  },
  {
    cocktailId: 5,
    title: "마!가리타라라라라라라라라라라라라",
    img: "https://news.imaeil.com/photos/2020/01/21/2020012117452367900_l.jpg",
    base: "마가리타",
    abv: 12,
  },
  // {
  //   cocktailId: 6,
  //   title: "마!가리타라라라라라라라라라라라라",
  //   img: "https://news.imaeil.com/photos/2020/01/21/2020012117452367900_l.jpg",
  //   base: "마가리타",
  //   abv: 12,
  // },
  // {
  //   cocktailId: 7,
  //   title: "마!가리타라라라라라라라라라라라라",
  //   img: "https://news.imaeil.com/photos/2020/01/21/2020012117452367900_l.jpg",
  //   base: "마가리타",
  //   abv: 12,
  // },
];

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
