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
  margin: 0 auto;
  aspect-ratio: 2/1;
  border-radius: 10px;
  background: {
    repeat: none;
    size: cover;
    position: center center;
  }
  cursor: pointer;
}
</style>
