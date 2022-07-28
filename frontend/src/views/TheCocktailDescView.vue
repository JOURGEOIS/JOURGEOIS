<template>
  <div>{{ route.params.cocktailId }}</div>
</template>

<script setup lang="ts">
import { onBeforeMount } from "vue";
import { useRoute } from "vue-router";
const route = useRoute();

onBeforeMount(() => {
  console.log(route.params.cocktailId);
});
</script>

<style scoped lang="scss"></style>
