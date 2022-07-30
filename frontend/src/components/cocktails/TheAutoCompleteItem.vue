<template>
  <div class="auto-complete-item">
    <div class="item-name">{{ data.nameKr }}</div>
    <div class="item-type">{{ data.type }}</div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
const props = defineProps<{
  data: {
    name: string;
    nameKr: string;
    type: string;
  };
}>();
</script>

<style scoped lang="scss">
.auto-complete-item {
  @include flex-xy(space-between, center);
  padding: 5px 0;
  border-bottom: 1px solid $unchecked-color;
  @include font(15px);

  .item-type {
    @include font-size-unchecked(15px);
  }
}
</style>
