<template>
  <div class="auto-complete-item">
    <div class="item-name">{{ data.nameKr }}</div>
    <div class="item-type" :v-if="!isUser">{{ data.name }}</div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();
const props = defineProps<{
  data: {
    id: number;
    name: string;
    nameKr: string;
    type: string;
  };
}>();

const isUser = computed(() => {
  return props.data.type === "계정";
});
</script>

<style scoped lang="scss">
.auto-complete-item {
  @include flex-xy(space-between, center);
  padding: 10px 0;
  border-bottom: 1px solid $unchecked-color;
  @include font(15px);

  .item-type {
    @include font-size-unchecked(12px);
  }
}
</style>
