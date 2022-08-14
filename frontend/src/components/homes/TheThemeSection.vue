<template>
  <div class="home-basic-section">
    <article class="article-title">
      <slot></slot>
      <span
        class="material-icons-outlined icon-info"
        @click="toggleShowDescription"
      >
        info
      </span>
    </article>
    <article class="article-description" v-if="isShowDescription">
      상황, 무드, 테마별로 추천해드리는 칵테일
    </article>
    <!-- 테마 선택 carousel -->
    <the-category-carousel></the-category-carousel>
    <!-- card carousel -->
    <!-- <the-card-carousel v-show="" :data="aloneCarouselData"></the-card-carousel> -->

    <!-- <article class="article-more-button">
      <div class="more-button" @click="clickMore">전체 보기</div>
    </article> -->
  </div>
</template>

<script setup lang="ts">
import TheCategoryCarousel from "@/components/homes/TheCategoryCarousel.vue";
import TheCardCarousel from "@/components/homes/TheCardCarousel.vue";
import { ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
const store = useStore();
const router = useRouter();

// const carouselData = {
//   setCarouselFunc: props.data.setCarouselFunc,
//   getCarouselFunc: props.data.getCarouselFunc,
// };

const isShowDescription = ref(false);

const toggleShowDescription = () => {
  isShowDescription.value = !isShowDescription.value;
};
// 더보기 클릭
const clickMore = () => {
  // router.push({ name: props.data.showMoreView });
};
</script>

<style scoped lang="scss">
.home-basic-section {
  @include flex(column);
  width: 100%;
  padding: 20px;
  border-radius: 5px;
  background-color: $white;
  gap: 25px;
  @include shadow-feed;
}

// title slot
.article-title {
  @include flex-xy(flex-start, flex-end);
  gap: 5px;

  .icon-info {
    @include font-size-placeholder(19px);
  }
}

.article-description {
  margin-top: -20px;
  @include font(13px, $fw-regular);
  color: $sub-color;
}

.article-more-button {
  .more-button {
    @include flex-center;
    width: 100%;
    padding: 10px;
    border: 1px solid $unchecked-color;
    border-radius: 5px;
    @include font(12px, $fw-medium);
    color: $primary-color;
    @include shadow-feed;
    @include for-click;
  }
}
</style>
