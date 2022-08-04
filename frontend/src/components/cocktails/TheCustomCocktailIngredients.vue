<template>
  <div class="custom-cocktail-search">
    <label for="custom-cocktail-search-input">재료 </label>
    <input
      type="text"
      id="custom-cocktail-search-input"
      v-model="customCocktailSearchValue"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

const customCocktailSearchValue = ref("");
</script>

<style scoped lang="scss"></style>
