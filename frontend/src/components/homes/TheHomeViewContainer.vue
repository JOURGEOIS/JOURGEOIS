<template>
  <div class="home-view-container top-view-no-margin">
    <!-- 유저들의 NEW 커스텀 칵테일 -->
    <the-home-basic-section :data="newCustomCocktailData">
      <h1 class="title">
        유저들의 <span class="important">NEW</span> 커스텀 칵테일
      </h1>
    </the-home-basic-section>
  </div>
</template>

<script setup lang="ts">
import TheHomeBasicSection from "@/components/homes/TheHomeBasicSection.vue";

const newCustomCocktailData = {
  description: "유저들이 최근에 등록한 커스텀 칵테일",
  moreButtonText: "전체 보기",
  callCaroselFunc: "",
  showMoreView: "",
};
</script>

<style scoped lang="scss">
.home-view-container {
  @include flex(column);
  width: calc(100% + 32px);
  margin-top: 8px;
  gap: 20px;
  background-color: $white150;
  padding: 20px 0;

  @media #{$tablet} {
    width: 60%;
  }

  @media #{$pc} {
    width: 50%;
  }
}

.title {
  @include font(15px, $fw-medium);

  .important {
    @include font(15px, $fw-bold);
    color: $danger-color;
  }
}
</style>
