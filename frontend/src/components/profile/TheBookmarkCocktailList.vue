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
  store.dispatch("profileDesc/getCurrentUserPostBookmark", route.params.userId )
});
</script>

<style scoped lang="scss">

</style>