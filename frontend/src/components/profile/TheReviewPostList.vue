<template>
  <h4>
    후기
  </h4>
</template>

<script setup lang="ts">
import { computed, onMounted } from '@vue/runtime-core';
import { useRoute } from 'vue-router';
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const reviewPost = computed(() => 
  store.getters["profileDesc/getCurrentUserPostReview"]
)

onMounted(() => {
  store.dispatch("profileDesc/getCurrentUserPostReview", route.params.userId )
});
</script>

<style scoped lang="scss">

</style>