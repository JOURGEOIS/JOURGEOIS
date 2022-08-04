<template>
  <section class="the-custom-cocktail-desc-body">
    <post-user-section
      :userInfo="data.followerDTO"
      :date="data.customCocktail.createTime"
    ></post-user-section>
  </section>
</template>

<script setup lang="ts">
import PostUserSection from "@/components/basics/PostUserSection.vue";
import { CustomCocktail } from "../../interface";

const props = defineProps<{
  data: CustomCocktail;
}>();
</script>

<style scoped lang="scss">
.the-custom-cocktail-desc-body {
  width: 100%;
}
</style>
