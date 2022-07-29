<template>
  <section class="initial-search-section">
    <!-- 최근 검색어 -->
    <article class="search-article">
      <div class="search-header">
        <h1 class="title1">최근 검색어</h1>
        <span
          v-if="recentSearchWords.length"
          class="material-icons"
          @click="clickDeleteIcon"
        >
          delete_outline
        </span>
      </div>
      <div class="word-list">
        <div class="no-content" v-if="!recentSearchWords.length">
          검색 기록이 없습니다.
        </div>
        <tag-basic-event
          :tagStyle="['sub', '12px']"
          v-for="(word, idx) in recentSearchWords"
          :key="idx"
          @clickTag="clickTag(word)"
          >{{ word }}</tag-basic-event
        >
      </div>
    </article>

    <!-- 인기 검색어 -->
    <article class="search-article">
      <h1 class="title1">인기 검색어</h1>
      <div class="word-container">
        <tag-basic-event
          :tagStyle="['sub', '12px']"
          v-for="(word, idx) in popularSearchWords"
          :key="idx"
          >{{ word }}</tag-basic-event
        >
      </div>
    </article>
  </section>
</template>

<script setup lang="ts">
import TagBasicEvent from "@/components/basics/TagBasicEvent.vue";
import { computed, toRefs, reactive } from "vue";
import { useStore } from "vuex";
const store = useStore();

// 최근 검색어
const recentSearchWords = computed(() => {
  return store.getters["cocktailSearch/getRecentSearchWords"];
});

// 인기 검색어
const popularSearchWords = computed(() => {
  return store.getters["cocktailSearch/getPopularSearchWords"];
});

// 최근 검색어 삭제
const clickDeleteIcon = () => {
  store.dispatch("cocktailSearch/removeRecentSearchWords");
};

// 태그 눌렀을 때 이동
const clickTag = (keyword: string) => {
  const data: object = {
    keyword,
    page: 0,
  };
  // 키워드로 칵테일 검색 0번 페이지
  store.dispatch("cocktailSearch/searchKeywordCocktail", data);
};
</script>

<style scoped lang="scss">
.initial-search-section {
  .search-article {
    padding: 20px 0;
    .search-header {
      @include flex-xy(space-between, center);
    }
    .word-list {
      @include flex-xy(flex-start, center);
      flex-wrap: wrap;
      gap: 7px;
    }
  }
}
.title1 {
  @include font(17px, $fw-medium);
  padding-bottom: 10px;
}

.no-content {
  @include font-size-unchecked(13px);
}
</style>
