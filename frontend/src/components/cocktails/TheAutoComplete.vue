<template>
  <section class="auto-complete-section">
    <h1 class="title1">이걸 찾으시나요?</h1>
    <article class="no-content" v-if="!autoCompleteSearchWords.length">
      검색 결과가 없습니다.
    </article>
    <article class="auto-complete-list" v-if="autoCompleteSearchWords.length">
      <the-auto-complete-item
        v-for="(item, idx) in autoCompleteSearchWords"
        :key="idx"
        :data="item"
      ></the-auto-complete-item>
    </article>
  </section>
</template>

<script setup lang="ts">
import TheAutoCompleteItem from "@/components/cocktails/TheAutoCompleteItem.vue";
import { reactive, computed, watchEffect } from "vue";
import { useStore } from "vuex";
const store = useStore();

const searchInputValue = computed(() => {
  return store.getters["cocktailSearch/getSearchInputValue"];
});

// * 검색 자동완성 리스트
const autoCompleteSearchWords = computed(() => {
  return store.getters["cocktails/getAutoCompleteSearchWords"];
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
</style>
