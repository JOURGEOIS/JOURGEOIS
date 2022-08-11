<template>
  <div></div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { Notice } from "../../interface";

const props = defineProps<{
  noticeData: Notice;
}>();
</script>

<style scoped lang="scss"></style>
