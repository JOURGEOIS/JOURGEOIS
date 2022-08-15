<template>
  <div
    :style="{ backgroundImage: image }"
    class="the-cocktail-awards-banner"
  ></div>
</template>

<script setup lang="ts">
import { computed } from "vue";
const props = defineProps<{
  imageUrl: string;
}>();

const image = computed(() => {
  return `url(${props.imageUrl})`;
});
</script>

<style scoped lang="scss">
.the-cocktail-awards-banner {
  width: 100%;
  aspect-ratio: 2/1;
  background: {
    color: $primary700;
    repeat: none;
    size: cover;
    position: center center;
  }
  cursor: pointer;
}
</style>
