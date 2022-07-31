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
      <div class="word-list">
        <tag-basic-event
          :tagStyle="['sub', '12px']"
          v-for="(word, idx) in popularSearchWords.keywords"
          :key="idx"
          >{{ word }}</tag-basic-event
        >
      </div>
    </article>
  </section>
</template>

<script setup lang="ts">
import TagBasicEvent from "@/components/basics/TagBasicEvent.vue";
import { computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
const router = useRouter();
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
  router.push({
    name: "TheSearchResultView",
    params: { searchValue: keyword },
  });
};

onMounted(() => {
  store.dispatch("cocktailSearch/setPopularSearchWords");
});
</script>

<style scoped lang="scss">
.initial-search-section {
  .search-article {
    padding-bottom: 50px;
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
