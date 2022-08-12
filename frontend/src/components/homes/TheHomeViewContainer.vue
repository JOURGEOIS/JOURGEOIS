<template>
  <div class="home-view-container">
    <the-hot-keyword-section
      ><h1 class="title">
        실시간 <span class="important">급상승</span>
      </h1></the-hot-keyword-section
    >
    <!-- 유저들의 NEW 커스텀 칵테일 -->
    <the-home-basic-section :data="latestCustomCocktailData">
      <h1 class="title">
        유저들의 <span class="important">NEW</span> 커스텀 칵테일
      </h1>
    </the-home-basic-section>
  </div>
</template>

<script setup lang="ts">
import { useStore } from "vuex";
import TheHomeBasicSection from "@/components/homes/TheHomeBasicSection.vue";
import TheHotKeywordSection from "@/components/homes/TheHotKeywordSection.vue";
const store = useStore();

const latestCustomCocktailData = {
  description: "유저들이 최근에 등록한 커스텀 칵테일",
  moreButtonText: "전체 보기",
  setCarouselFunc: "setLatestCustomCocktails",
  getCarouselFunc: "getLatestCustomCocktails",
  showMoreView: "TheAllLatestCustomCocktailView",
};
</script>

<style scoped lang="scss">
.home-view-container {
  @include flex(column);
  width: calc(100% + 32px);
  margin-left: -16px;
  padding: 10px 0;
  gap: 20px;
  background-color: $white150;
}

.title {
  @include font(17px, $fw-medium);

  .important {
    @include font(17px, $fw-bold);
    color: $danger-color;
  }
}
</style>
