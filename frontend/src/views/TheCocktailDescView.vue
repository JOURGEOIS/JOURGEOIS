<template>
  <div class="cocktail-desc-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      칵테일 상세 정보
    </header-basic>
    <div class="top-view">
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
            <!-- <span>99+</span> -->
          </div>
          <div class="cocktail-desc-tab-custom" :class="`index-${index}`">
            <p @click="clickCustomTab">커스텀</p>
            <!-- <span>99+</span> -->
          </div>
        </div>
        <!-- 동적 컴포넌트: 탭에 따라 변경된다. -->
        <component :is="currentComponent"></component>
      </section>
    </div>
  </div>
  <nav-bar></nav-bar>
  <success-pop-up v-if="successStatus" @off-modal="offSuccessModal">
    {{ successMessage }}</success-pop-up
  >
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import TheCocktailDescDetail from "@/components/cocktails/TheCocktailDescDetail.vue";
import SuccessPopUp from "@/components/modals/SuccessPopUp.vue";
import {
  onMounted,
  defineAsyncComponent,
  computed,
  watch,
  onBeforeMount,
  onUnmounted,
} from "vue";
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
    () => import("@/components/cocktails/TheCocktailDescCustomCocktail.vue")
  ),
];

const index = computed(() => store.getters["cocktailDesc/getCurrentTab"]);
const currentComponent = computed(() => {
  return componentArray[index.value];
});

const clickRecipeTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 0);
const clickReviewTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 1);
const clickCustomTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 2);

// 칵테일 db 불러오기
onMounted(() => {
  store.dispatch("cocktailDesc/getCocktailDb", route.params.cocktailId);
});

// 성공 팝업 =======================================
const successMessage = computed(
  () => store.getters["customCocktail/getSuccessMessage"]
);

const successStatus = computed(
  () => store.getters["customCocktail/getAlertStatus"]
);

// 시간제 모달
watch(successStatus, () => {
  if (successStatus.value) {
    setTimeout(() => offSuccessModal(), 2000);
  }
});
const offSuccessModal = () => {
  store.dispatch("customCocktail/changeAlertStatus", false);
};

// 리셋
onUnmounted(() => {
  store.dispatch("cocktailDesc/changeCurrentTab", 0);
  store.dispatch("cocktailDesc/resetCocktailDb");
});
</script>

<style scoped lang="scss">
.cocktail-desc-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
  margin-bottom: 48px;

  .top-view {
    width: 100%;
  }

  section {
    @include flex(column);
    justify-content: center;
    align-items: center;
    gap: 40px;
    width: 100%;
    margin-top: 40px;

    @media #{$tablet} {
      gap: 40px;
    }

    .cocktail-desc-hr {
      width: 100%;
      height: 1px;
      border: 0;
      background-color: $unchecked-color;
      margin: 0;
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
