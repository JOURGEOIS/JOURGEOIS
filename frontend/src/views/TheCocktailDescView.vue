<template>
  <div class="cocktail-desc-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      칵테일 상세 정보
    </header-basic>

    <!-- 칵테일 상세 페이지: 상단부분 -->
    <the-cocktail-desc-detail></the-cocktail-desc-detail>

    <!-- 칵테일 상세 페이지: 탭-->
    <section class="cocktail-desc-section">
      <hr class="cocktail-desc-hr" />
      <!-- 탭: 탭을 선택하면 해당 탭으로 컴포넌트를 바꾼다.  -->
      <div class="cocktail-desc-tab">
        <div class="cocktail-desc-tab-recipe" :class="`index-${index}`">
          <p @click="clickRecipeTab">레시피</p>
        </div>
        <div class="cocktail-desc-tab-review" :class="`index-${index}`">
          <p @click="clickReviewTab">후기</p>
          <span>99+</span>
        </div>
        <div class="cocktail-desc-tab-custom" :class="`index-${index}`">
          <p @click="clickCustomTab">커스텀</p>
          <span>99+</span>
        </div>
      </div>
      <!-- 동적 컴포넌트: 탭에 따라 변경된다. -->
      <keep-alive>
        <component :is="currentComponent"></component>
      </keep-alive>
    </section>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import TheCocktailDescDetail from "@/components/cocktails/TheCocktailDescDetail.vue";
import { onMounted, defineAsyncComponent, computed, watch } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const store = useStore();
const route = useRoute();

// 동적 컴포넌트 (탭)
const componentArray = [
  defineAsyncComponent(
    () => import("@/components/cocktails/TheCocktailDescRecipe.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/cocktails/TheCocktailDescReview.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/cocktails/TheCocktailDescCustom.vue")
  ),
];

const index = computed(() => store.getters["cocktailDesc/getCurrentTab"]);
const currentComponent = computed(() => {
  return componentArray[index.value];
});

const clickRecipeTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 0);
const clickReviewTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 1);
const clickCustomTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 2);

// params가 변경될 때
watch(
  () => route.params.cocktail,
  () => {
    store.dispatch("cocktailDesc/getCocktailDb", route.params.cocktailId);
  }
);

// 동적 라우팅
onMounted(() => {
  store.dispatch("cocktailDesc/getCocktailDb", route.params.cocktailId);
});
</script>

<style scoped lang="scss">
.cocktail-desc-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
  margin-bottom: 48px;

  section {
    @include flex(column);
    justify-content: center;
    align-items: center;
    gap: 24px;
    width: 100%;
    margin-top: 24px;

    @media #{$tablet} {
      gap: 40px;
    }

    .cocktail-desc-hr {
      width: 100%;
      height: 1px;
      border: 0;
      background-color: $unchecked-color;
    }
    .cocktail-desc-tab {
      @include flex-xy(space-between, center);
      width: 80%;
      div {
        @include flex-center;
        position: relative;
        width: 72px;
        border-bottom: 2px solid $white400;

        @media #{$tablet} {
          width: 96px;
        }

        @media #{$pc} {
          width: 120px;
        }

        p {
          color: $white400;
          @include font($fs-main, $fw-medium);
          padding-bottom: 6px;
          cursor: pointer;
        }

        span {
          position: absolute;
          right: -8px;
          top: -2px;
          @include font($fs-sm, $fw-regular);
          color: $white300;
        }
      }

      // 탭 활성화 색상 변경
      .cocktail-desc-tab-recipe.index-0 {
        border-bottom: 2px solid $primary-color;
        p {
          color: $main-color;
        }
      }

      .cocktail-desc-tab-review.index-1 {
        border-bottom: 2px solid $primary-color;
        p {
          color: $main-color;
        }

        span {
          color: $white400;
        }
      }

      .cocktail-desc-tab-custom.index-2 {
        border-bottom: 2px solid $primary-color;
        p {
          color: $main-color;
        }
        span {
          color: $white400;
        }
      }
    }
  }
}
.cocktail-desc-bookmark-use {
  cursor: pointer;
}
</style>
