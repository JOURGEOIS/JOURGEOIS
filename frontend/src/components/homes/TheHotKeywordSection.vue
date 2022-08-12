<template>
  <section class="hot-keyword-section" v-if="hotKeywords.keywords.length">
    <article class="article-title">
      <div class="line-title">
        <slot></slot>
        <span
          class="material-icons-outlined icon-info"
          @click="toggleShowDescription"
          >info</span
        >
        <p class="standard-time">{{ date }} 기준</p>
      </div>
      <div class="line-description" v-if="isShowDescription">
        지난 1시간동안 많이 검색된 검색어들
      </div>
    </article>
    <article class="article-lists">
      <li
        class="rank-item"
        v-for="(item, idx) in hotKeywords.keywords"
        :key="idx"
        @click="clickRankItem(item)"
      >
        <span class="rank-item-number">{{ idx + 1 }}</span>
        <span class="rank-item-content">
          <span class="rank-item-title">{{ item.keyword }}</span>
          <span class="rank-item-delta">{{}}</span>
          <span class="material-icons icon-up" v-if="delta[idx] > 0">
            eject
          </span>
          <span class="material-icons icon-down" v-if="delta[idx] < 0">
            eject
          </span>
          <span class="material-icons icon-maintain" v-if="!delta[idx]">
            horizontal_rule
          </span>
        </span>
      </li>
    </article>
  </section>
</template>

<script setup lang="ts">
import { reactive, ref, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { HotKeyword } from "../../interface";
const store = useStore();
const router = useRouter();
store.dispatch("carousel/setHotKeywords");

const isShowDescription = ref(false);

const toggleShowDescription = () => {
  isShowDescription.value = !isShowDescription.value;
};

// 랭킹 불러오기
const hotKeywords = computed(() => store.getters["carousel/getHotKeywords"]);
const delta = computed(() => store.getters["carousel/getHotKeywordDelta"]);
const date = computed(() => store.getters["carousel/getHotKeywordDate"]);

const clickRankItem = (item: HotKeyword) => {
  // 검색어 저장
  store.dispatch("cocktailSearch/setRecentSearchWords");
  // 검색 결과 view로 이동
  router.push({
    name: "TheSearchResultView",
    params: { searchValue: item.keyword },
  });
};
</script>

<style scoped lang="scss">
.hot-keyword-section {
  @include flex(column);
  width: 100%;
  padding: 20px;
  border-radius: 5px;
  background-color: $white;
  gap: 25px;
  @include shadow-feed;

  .article-title {
    @include flex(column);
    gap: 5px;

    .line-title {
      @include flex-xy(flex-start, flex-end);
      gap: 5px;

      .standard-time {
        @include font-size-sub(13px);
      }
      .icon-info {
        @include font-size-placeholder(19px);
      }
    }
    .line-description {
      @include font(13px, $fw-regular);
      color: $sub-color;
    }
  }
  .article-lists {
    @include flex(column);

    .rank-item {
      @include flex-xy(flex-start, center);
      gap: 10px;
      padding: 10px 5px;
      border-bottom: 1px solid $white150;
      @include list-hover;
      .rank-item-number {
        @include flex;
        flex-grow: 0;
        @include font(15px, $fw-medium);
      }
      .rank-item-content {
        @include flex-xy(space-between, center);
        flex-grow: 1;
        @include font(13px);
      }
    }
  }
}

.icon-up {
  @include font-size-danger(25px);
}

.icon-down {
  @include font-size-blue(25px);
  transform: rotate(0.5turn);
}

.icon-maintain {
  @include font-size-sub(25px);
  transform: scale(0.6, 1);
}

.material-icons {
  opacity: 0.5;
}
</style>
