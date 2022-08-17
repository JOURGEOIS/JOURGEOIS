<template>
  <div class="the-user-bookmark-list">
    <article
      class="the-user-bookmark-item"
      v-for="bookmark in userBookmarkData"
      :key="bookmark.cocktailId"
    >
      <the-bookmark-cocktail-item
        :bookmark="bookmark"
      ></the-bookmark-cocktail-item>
    </article>
  </div>
</template>

<script setup lang="ts">
import TheBookmarkCocktailItem from "@/components/profile/TheBookmarkCocktailItem.vue";
import { computed, onBeforeMount, onUnmounted, watch } from "@vue/runtime-core";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const userBookmarkData = computed(
  () => store.getters["profileDesc/getCurrentUserPostBookmark"]
);

// 인피니티 스크롤
const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "profileDesc/getCurrentUserPostBookmarkData",
    data: route.params.userId,
  };
  store.dispatch("scroll/handleScroll", data);
};

// 인피니티 스크롤을 연동, 처음 데이터 가져오기
onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch(
    "profileDesc/getCurrentUserPostBookmarkData",
    route.params.userId
  );
});

// unmount될 때, 페이지와 리스트를 리셋한다.
onUnmounted(() => {
  store.dispatch("profileDesc/resetCurrentUserPost");
  window.removeEventListener("scroll", handleScroll);
});

const paramsUserId = computed(() => route.params.userId);
watch(paramsUserId, () => {
  store.dispatch(
    "profileDesc/getCurrentUserPostBookmarkData",
    paramsUserId.value
  );
});
</script>

<style scoped lang="scss">
.the-user-bookmark-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(50%, auto));
  justify-items: center;
  justify-content: center;
  align-items: center;
  align-content: center;
  row-gap: 32px;
  width: 100%;

  @media #{$tablet} {
    grid-template-columns: repeat(auto-fill, minmax(33.33333%, auto));
  }

  article {
    position: relative;
    width: 80%;
    aspect-ratio: 1/1.7;
  }
}
</style>
