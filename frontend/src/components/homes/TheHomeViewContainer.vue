<template>
  <div class="home-view-container top-view-no-margin">
    <the-home-basic-section></the-home-basic-section>
    <the-home-basic-section></the-home-basic-section>
  </div>
</template>

<script setup lang="ts">
import TheHomeBasicSection from "@/components/homes/TheHomeBasicSection.vue";
</script>

<style scoped lang="scss">
.home-view-container {
  @include flex(column);
  width: 100%;
  margin-top: 8px;
  gap: 20px;

  @media #{$tablet} {
    width: 60%;
  }

  @media #{$pc} {
    width: 50%;
  }
}
</style>
