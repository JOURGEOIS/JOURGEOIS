<template>
  <section class="search-button-section">
    <button-basic-round :button-style="['primary', 'long', 'small']"
      >검색</button-basic-round
    >
  </section>
</template>

<script setup lang="ts">
import ButtonBasicRound from "@/components/basics/ButtonBasicRound.vue";
import { reactive } from "vue";
</script>

<style scoped lang="scss"></style>
