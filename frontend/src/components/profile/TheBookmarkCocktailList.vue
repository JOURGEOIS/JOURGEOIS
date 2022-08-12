<template>
  <h4>
    북마크
  </h4>
</template>

<script setup lang="ts">
import { computed, onMounted } from '@vue/runtime-core';
import { useRoute } from 'vue-router';
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const bookmarkPost = computed(() => 
  store.getters["profileDesc/getCurrentUserPostBookmark"]
)

onMounted(() => {
  store.dispatch("profileDesc/getCurrentUserPostBookmarkData", route.params.userId )
});
</script>

<style scoped lang="scss">
.the-cocktail-awards-vote-list {
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