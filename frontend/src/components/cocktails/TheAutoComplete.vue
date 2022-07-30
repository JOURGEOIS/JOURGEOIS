<template>
  <section class="auto-complete-section">
    <h1 class="title1">이걸 찾으시나요?</h1>
    <article class="no-content" v-if="!setAutoCompleteSearchWords.length">
      검색 결과가 없습니다.
    </article>
    <article
      class="auto-complete-list"
      v-if="setAutoCompleteSearchWords.length"
    >
      <the-auto-complete-item
        v-for="(item, idx) in setAutoCompleteSearchWords"
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
import { searchAutoComplete } from "../../repositories/lookup";
const store = useStore();

const searchInputValue = computed(() => {
  return store.getters["cocktailSearch/getSearchInputValue"];
});

// * 검색 자동완성 리스트
const setAutoCompleteSearchWords = computed(() => {
  return store.getters["cocktails/setAutoCompleteSearchWords"];
});

// * 검색 자동완성 Debounce
let debounce: any;
watchEffect(() => {
  // watch 실행용 dummy code
  searchInputValue.value;
  if (debounce) {
    clearTimeout(debounce);
  }
  debounce = setTimeout(async () => {
    const resList = await searchAutoComplete(searchInputValue.value);
    console.log(resList);
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
