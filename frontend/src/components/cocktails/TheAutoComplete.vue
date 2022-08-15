<template>
  <section class="auto-complete-section">
    <!-- 칵테일 -->
    <article
      class="auto-complete-list"
      v-if="autoCompleteSearchWords.cocktails.length"
    >
      <h1 class="title1">칵테일</h1>
      <the-auto-complete-item
        v-for="(item, idx) in autoCompleteSearchWords.cocktails"
        :key="idx"
        :data="item"
        @click="clickCocktail(item)"
      ></the-auto-complete-item>
    </article>

    <!-- 재료 -->
    <article
      class="auto-complete-list"
      v-if="autoCompleteSearchWords.ingredients.length"
    >
      <h1 class="title1">재료</h1>
      <the-auto-complete-item
        v-for="(item, idx) in autoCompleteSearchWords.ingredients"
        :key="idx"
        :data="item"
        @click="clickIngredient(item)"
      ></the-auto-complete-item>
    </article>

    <!-- 계정 -->
    <article
      class="auto-complete-list"
      v-if="autoCompleteSearchWords.users.length"
    >
      <h1 class="title1">계정</h1>
      <the-auto-complete-item
        v-for="(item, idx) in autoCompleteSearchWords.users"
        :key="idx"
        :data="item"
        @click="clickUser(item)"
      ></the-auto-complete-item>
    </article>
  </section>
</template>

<script setup lang="ts">
import TheAutoCompleteItem from "@/components/cocktails/TheAutoCompleteItem.vue";
import { reactive, computed, watchEffect } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
const router = useRouter();
const store = useStore();

const searchInputValue = computed(() => {
  return store.getters["cocktailSearch/getSearchInputValue"];
});

// * 검색 자동완성 리스트
const autoCompleteSearchWords = computed(() => {
  return store.getters["cocktailSearch/getAutoCompleteSearchWords"];
});

// * 검색 자동완성 리스트 추가
const setAutoCompleteSearchWords = (keyword: string) => {
  store.dispatch("cocktailSearch/setAutoCompleteSearchWords", keyword);
};

// * 검색 자동완성 Debounce
let debounce: any;
watchEffect(() => {
  // watch 실행용 dummy code
  searchInputValue.value;
  if (debounce) {
    clearTimeout(debounce);
  }
  debounce = setTimeout(async () => {
    setAutoCompleteSearchWords(searchInputValue.value);
  }, 200);
});

// * 자동완성 클릭
interface autoCompleteWord {
  id: number;
  name: string;
  nameKr: string;
  type: string;
}

// 칵테일 자동완성 클릭 시
const clickCocktail = (item: autoCompleteWord) => {
  router.push({ name: "TheCocktailDescView", params: { cocktailId: item.id } });
};

// 재료 자동완성 클릭 시
const clickIngredient = (item: autoCompleteWord) => {
  // 한글 재료명을 입력값으로 저장
  store.dispatch("cocktailSearch/setSearchInputValue", item.nameKr);
  // 입력값 state를 최근 검색어에 추가
  store.dispatch("cocktailSearch/setRecentSearchWords");
  // 재료 id로 재료를 포함한 칵테일 리스트 저장
  store.dispatch("searchResult/setSearchCocktail", item.id);
  // 칵테일 리스트 view로 이동
  router.push({
    name: "TheSearchCocktailByIngredientView",
    params: { ingredientId: item.id },
  });
};

// 계정 자동완성 클릭 시
const clickUser = (item: autoCompleteWord) => {
  router.push({ name: "TheUserProfileView", params:{userId: item.id}})
};
</script>
<style scoped lang="scss">
.title1 {
  @include font(17px, $fw-medium);
  padding-bottom: 10px;
}
.no-content {
  @include flex-xy(center);
  @include font-size-sub(13px);
}

.auto-complete-list {
  margin-bottom: 40px;
}
</style>
