<template>
  <div class="cocktail-desc-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      칵테일 상세 정보
    </header-basic>

    <!-- 칵테일 디테일 -->
    <the-cocktail-desc-detail></the-cocktail-desc-detail>

    <!-- 칵테일 동적 컴포넌트 -->
    <section class="cocktail-desc-section">
      <hr class="cocktail-desc-hr" />
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
      <keep-alive>
        <component :is="currentComponent"></component>
      </keep-alive>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheCocktailDescDetail from "@/components/cocktails/TheCocktailDescDetail.vue";
import { ref, onBeforeMount, defineAsyncComponent, computed } from "vue";
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
    () => import("@/components/cocktails/THeCocktailDescCustom.vue")
  ),
];

const index = computed(() => store.getters["cocktailDesc/getCurrentTab"]);
const currentComponent = computed(() => {
  return componentArray[index.value];
});

const clickRecipeTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 0);
const clickReviewTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 1);
const clickCustomTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 2);

// 동적 라우팅
onBeforeMount(() => {
  console.log(route.params.cocktailId);
});
</script>

<style scoped lang="scss">
.cocktail-desc-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  section {
    @include flex(column);
    justify-content: center;
    align-items: center;
    gap: 24px;
    width: 100%;
    margin-top: 48px;

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
</style>
