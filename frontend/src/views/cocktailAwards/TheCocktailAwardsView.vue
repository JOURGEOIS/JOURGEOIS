<template>
  <div class="the-cocktail-awards-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      칵테일 어워즈
    </header-basic>
    <section class="the-cocktail-awards-section top-view" v>
      <!-- 상단 배너 -->
      <the-cocktail-awards-banner></the-cocktail-awards-banner>
      <section>
        <p>가장 예쁜 칵테일에 투표해주세요</p>

        <!-- 탭 : 탭을 선택하면 해당 탭으로 컴포넌트를 바꾼다.  -->
        <div class="the-cocktail-awards-tab">
          <hr />
          <div>
            <div class="cocktail-desc-tab-vote">
              <p :class="`awards-${index}`" @click="changeTab(0)">투표 하기</p>
            </div>
            <div class="cocktail-desc-tab-now">
              <p :class="`awards-${index}`" @click="changeTab(1)">투표 현황</p>
            </div>
          </div>
          <hr />
        </div>

        <!-- 동적 컴포넌트: 탭에 따라 변경된다.  -->
        <component :is="currentComponent"></component>
      </section>
    </section>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import TheCocktailAwardsBanner from "@/components/cocktailAwards/TheCocktailAwardsBanner.vue";
import { defineAsyncComponent, computed } from "vue";
import { useStore } from "vuex";
const store = useStore();
store.dispatch("navbar/setNavIconStatus", 0);

// 동적 컴포넌트 (탭)
const componentArray = [
  defineAsyncComponent(
    () => import("@/components/cocktailAwards/TheCocktailAwardsVote.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/cocktailAwards/TheCocktailAwardsNow.vue")
  ),
];

const index = computed(() => store.getters["cocktailAwards/getCurrentTab"]);
const currentComponent = computed(() => componentArray[index.value]);
const changeTab = (value: number) =>
  store.dispatch("cocktailAwards/changeCurrentTab", value);
</script>

<style scoped lang="scss">
.the-cocktail-awards-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  .the-cocktail-awards-section {
    width: 100%;
    margin-bottom: 24px;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }

    section {
      @include flex(column);
      gap: 24px;

      > p {
        @include font($fs-main, $fw-medium);
      }
      .the-cocktail-awards-tab {
        @include flex(column);
        gap: 12px;
        hr {
          height: 1px;
          margin: 0;
          border: 0;
          background-color: $white300;
        }

        > div {
          display: grid;
          display: grid;
          grid-template-columns: repeat(auto-fill, minmax(50%, auto));
          justify-items: center;
          justify-content: center;
          align-items: center;
          align-content: center;
          @include font($fs-main, $fw-medium);

          p {
            cursor: pointer;
          }
          .cocktail-desc-tab-vote {
            .awards-0 {
              color: $main-color;
            }
          }

          .cocktail-desc-tab-vote {
            .awards-1 {
              color: $white400;
              font-weight: $fw-regular;
            }
          }

          .cocktail-desc-tab-now {
            .awards-0 {
              color: $white400;
              font-weight: $fw-regular;
            }
          }

          .cocktail-desc-tab-now {
            .awards-1 {
              color: $main-color;
            }
          }
        }
      }
    }
  }
}
</style>
