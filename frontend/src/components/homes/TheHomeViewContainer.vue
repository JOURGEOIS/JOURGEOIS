<template>
  <div class="home-view-container">
    <!-- 칵테일 어워즈 배너 -->
    <section class="cocktail-awards-section">
      <the-cocktail-awards-banner
        image-url="https://jourgeois-profile-image.s3.ap-northeast-2.amazonaws.com/default/jurjeois_home_banner.gif"
        @click="$router.push({ name: 'TheCocktailAwardsView' })"
      ></the-cocktail-awards-banner>
    </section>
    <!-- 실시간 인기 급상승 검색어 -->
    <the-hot-keyword-section
      ><h1 class="title">
        실시간 <span class="important">급상승</span> 검색어
      </h1></the-hot-keyword-section
    >
    <!-- 테마별 추천 칵테일 -->
    <the-theme-section
      ><h1 class="title">
        <span class="important">테마별</span> 추천 칵테일
      </h1></the-theme-section
    >
    <!-- 좋아요 기반 추천 칵테일  -->
    <the-home-basic-section
      v-if="isLoggedIn && bookmarkList.length"
      :data="likeRecommendedCocktailData"
    >
      <h1 class="title">
        당신을 위한 <span class="important">취향저격</span> 칵테일
      </h1>
    </the-home-basic-section>
    <!-- 초심자들을 위한 영상 추천 -->
    <the-video-section><h1 class="title">영상 추천</h1></the-video-section>
    <!-- 주류주아 HOT 칵테일  -->
    <the-home-basic-section :data="hotCocktailData">
      <h1 class="title">주류주아 <span class="important">HOT</span> 칵테일</h1>
    </the-home-basic-section>

    <!-- 유저들의 NEW 커스텀 칵테일 -->
    <the-home-basic-section :data="latestCustomCocktailData">
      <h1 class="title">유저들의 <span class="important">NEW</span> 칵테일</h1>
    </the-home-basic-section>
    <!-- 유저들의 이번 주 HOT 칵테일  -->
    <the-home-basic-section :data="weeklyHotCocktailData">
      <h1 class="title">
        유저들의 <span class="important">이번 주 HOT</span> 칵테일
      </h1>
    </the-home-basic-section>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";
import TheVideoSection from "@/components/homes/TheVideoSection.vue";
import TheHomeBasicSection from "@/components/homes/TheHomeBasicSection.vue";
import TheHotKeywordSection from "@/components/homes/TheHotKeywordSection.vue";
import TheThemeSection from "@/components/homes/TheThemeSection.vue";
import TheCocktailAwardsBanner from "@/components/cocktailAwards/TheCocktailAwardsBanner.vue";
const store = useStore();

// 유저들의 NEW 커스텀 칵테일
const latestCustomCocktailData = {
  description: "유저들이 최근에 등록한 커스텀 칵테일",
  moreButtonText: "전체 보기",
  setCarouselFunc: "setLatestCustomCocktails",
  getCarouselFunc: "getLatestCustomCocktails",
  showMoreView: "TheAllLatestCustomCocktailView",
};

// 주류주아 HOT 칵테일
const hotCocktailData = {
  description: "북마크와 댓글 개수가 가장 많은 칵테일",
  moreButtonText: "전체 보기",
  setCarouselFunc: "setHotCocktails",
  getCarouselFunc: "getHotCocktails",
  showMoreView: "TheAllHotCocktailView",
};

// 유저들의 이번 주 HOT 칵테일
const weeklyHotCocktailData = {
  description: "한 주간 유저들이 올린 커스텀 칵테일 중 인기가 많은 칵테일",
  moreButtonText: "전체 보기",
  setCarouselFunc: "setWeeklyHotCocktails",
  getCarouselFunc: "getWeeklyHotCocktails",
  showMoreView: "TheAllWeeklyHotCocktailView",
};

// 좋아요 기반 추천 칵테일
const likeRecommendedCocktailData = {
  description: "당신이 좋아하는 칵테일들을 기반으로 추천하는 취향저격 칵테일",
  moreButtonText: "전체 보기",
  setCarouselFunc: "setLikeRecommendedCocktails",
  getCarouselFunc: "getLikeRecommendedCocktails",
  showMoreView: "TheAllLikeRecommendedCocktailView",
};

// 로그인 여부 확인
const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);

// 좋아요 여부 확인
const bookmarkList = computed(
  () => store.getters["carousel/getLikeRecommendedCocktails"]
);
</script>

<style scoped lang="scss">
.home-view-container {
  @include flex(column);
  width: calc(100% + 32px);
  margin-left: -16px;
  padding: 10px 0;
  gap: 30px;
  background-color: $white150;
}

.cocktail-awards-section {
  @include shadow-feed;
  background-color: $white;
  margin-top: -10px;
}

.title {
  @include font(17px, $fw-medium);

  .important {
    @include font(17px, $fw-bold);
    color: $red-color;
  }
}
</style>
