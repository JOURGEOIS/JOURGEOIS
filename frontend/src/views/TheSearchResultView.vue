<template>
  <div class="search-result-view">
    <div class="cocktail-search-container">
      <!-- 상단 입력창 및 필터 아이콘 섹션 -->
      <the-cocktail-search-header></the-cocktail-search-header>
      <!-- 탭 섹션 -->
      <section class="tab-section">
        <div class="tab-cocktail" :class="`index-${index}`">
          <p @click="clickCocktailTab">칵테일</p>
        </div>
        <div class="tab-user" :class="`index-${index}`">
          <p @click="clickUserTab">계정</p>
        </div>
      </section>
      <!-- 탭에 따라 변경되는 동적 컴포넌트 -->
      <section class="dynamic-component-section">
        <keep-alive>
          <component :is="currentComponent"></component>
        </keep-alive>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import TheCocktailSearchHeader from "@/components/cocktails/TheCocktailSearchHeader.vue";
import { computed, onMounted, defineAsyncComponent } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

// 검색 결과
const searchInputValue = computed(() => {
  return store.getters["cocktailSearch/getSearchInputValue"];
});

onMounted(() => {
  // router의 params를 keyword로 지정
  const keyword = route.params.searchValue;
  // vuex의 state에 router params 저장
  store.dispatch("cocktailSearch/setSearchInputValue", keyword);

  // keyword로 최초 유저정보 불러오기
  store.dispatch("searchResult/setSearchUser", keyword);
});

// 탭 클릭
const clickCocktailTab = () => store.dispatch("searchResult/setCurrentTab", 0);
const clickUserTab = () => store.dispatch("searchResult/setCurrentTab", 1);

// 동적 컴포넌트 (탭)
const componentArray = [
  defineAsyncComponent(
    () => import("@/components/cocktails/TheSearchResultCocktail.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/cocktails/TheSearchResultUser.vue")
  ),
];

const index = computed(() => store.getters["searchResult/getCurrentTab"]);
const currentComponent = computed(() => {
  return componentArray[index.value];
});
</script>

<style scoped lang="scss">
.search-result-view {
  @include accountLayOut;
  @include flex-xy(center, center);

  .cocktail-search-container {
    width: 100%;
    margin-top: 1rem;

    .tab-section {
      @include flex-xy(space-between, center);
      width: 100%;
      div {
        @include flex-center;
        position: relative;
        width: 50%;
        border-bottom: 1px solid $white400;

        p {
          color: $white400;
          @include font($fs-main, $fw-medium);
          padding-bottom: 10px;
          cursor: pointer;
        }
      }

      // 탭 활성화 색상 변경
      .tab-cocktail.index-0 {
        border-bottom: 2px solid $primary-color;
        p {
          color: $main-color;
        }
      }

      .tab-user.index-1 {
        border-bottom: 2px solid $primary-color;
        p {
          color: $main-color;
        }
      }
    }

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }
  }
}

.title1 {
  @include font(17px, $fw-medium);
  padding-bottom: 10px;
}
</style>
