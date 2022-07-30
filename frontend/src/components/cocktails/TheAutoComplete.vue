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

const clickCocktail = (item: autoCompleteWord) => {
  alert(item.nameKr);
  // router.push({ name: "TheHomeView", params: {  } });
};

const clickIngredient = (item: autoCompleteWord) => {
  alert(item.nameKr);
  // router.push({ name: "TheHomeView", params: { } });
};

const clickUser = (item: autoCompleteWord) => {
  alert(item.nameKr);
  // router.push({ name: "TheProfileView", params: { userId: item.id } });
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
