<template>
  <div class="cocktail-search-view">
    <form @submit.prevent="searchComplete">
      <div class="cocktail-search-container">
        <!-- 검색 창 섹션 -->
        <the-cocktail-search-header> </the-cocktail-search-header>
        <!-- 처음 보이는 공간 -->
        <the-cocktail-search-init
          v-if="!searchInputValue"
        ></the-cocktail-search-init>

        <!-- 자동완성 -->
        <the-auto-complete v-if="searchInputValue"></the-auto-complete>

        <!-- 검색 버튼 구역 -->
        <the-cocktail-search-button-section
          v-if="!searchInputValue"
          class="whole-search-button"
        ></the-cocktail-search-button-section>
      </div>
    </form>
  </div>
  <the-cocktail-search-filter v-if="filterStatus"></the-cocktail-search-filter>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import TheCocktailSearchHeader from "@/components/cocktails/TheCocktailSearchHeader.vue";
import TheCocktailSearchInit from "@/components/cocktails/TheCocktailSearchInit.vue";
import TheAutoComplete from "@/components/cocktails/TheAutoComplete.vue";
import TheCocktailSearchButtonSection from "@/components/cocktails/TheCocktailSearchButtonSection.vue";
import TheCocktailSearchFilter from "@/components/cocktails/TheCocktailSearchFilter.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { computed, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
const router = useRouter();
const route = useRoute();
const store = useStore();

// [basic] navbar icon 0번 켜기
const setNavIconStatus = (index: number) => {
  store.dispatch("navbar/setNavIconStatus", index);
};
setNavIconStatus(3);

const searchInputValue = computed(() => {
  return store.getters["cocktailSearch/getSearchInputValue"];
});

onMounted(() => {
  if (route.params.searchValue) {
    store.dispatch(
      "cocktailSearch/setSearchInputValue",
      route.params.searchValue
    );
  }
});

const searchComplete = () => {
  if (!searchInputValue.value) {
    // 전체 칵테일 view로 이동
    router.replace({
      name: "TheWholeCocktailView",
    });
  } else {
    // 검색어 저장
    store.dispatch("cocktailSearch/setRecentSearchWords");
    // 검색 결과 view로 이동
    router.push({
      name: "TheSearchResultView",
      params: { searchValue: searchInputValue.value },
    });
  }
};

const filterStatus = computed(
  () => store.getters["cocktailSearch/getFilterStatus"]
);

// 삭제 아이콘 누르기
const clickCloseIcon = () => {
  store.dispatch("cocktailSearch/setSearchInputValue", "");
};
</script>

<style scoped lang="scss">
.cocktail-search-view {
  @include flex;
  @include accountLayOut;
  form {
    @include flex(column);
    width: 100%;
  }
  .cocktail-search-container {
    @include flex(column);
    justify-content: space-between;
    width: 100%;
    margin-top: 1rem;

    .whole-search-button {
      position: fixed;
      left: 50%;
      transform: translate(-50%, 0);
      width: 90%;
      bottom: 100px;

      @media #{$tablet} {
        width: 50%;
      }

      @media #{$pc} {
        width: 60%;
      }
    }
  }
}

.title1 {
  @include font(17px, $fw-medium);
  padding-bottom: 10px;
}
</style>
