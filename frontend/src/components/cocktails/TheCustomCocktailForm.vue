<template>
  <form @submit.prevent="submitCustomCocktailForm" class="custom-cocktail-form">
    <input-basic
      :data="customCocktailTitleData"
      :input-style="customCocktailTitleStyle"
      v-model="customCocktailValue"
    ></input-basic>
    <the-custom-cocktail-image-input></the-custom-cocktail-image-input>
    <the-custom-cocktail-ingredients></the-custom-cocktail-ingredients>
    <the-custom-cocktail-textarea
      v-model="customCocktailDescValue"
    ></the-custom-cocktail-textarea>
  </form>
</template>

<script setup lang="ts">
import { ref } from "vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import TheCustomCocktailImageInput from "@/components/cocktails/TheCustomCocktailImageInput.vue";
import TheCustomCocktailTextarea from "@/components/cocktails/TheCustomCocktailTextarea.vue";
import TheCustomCocktailIngredients from "@/components/cocktails/TheCustomCocktailIngredients.vue";

// title input
const customCocktailTitleData = {
  button: true,
  required: true,
  id: "custom-cocktail-title-input",
  label: "칵테일 이름",
  placeholder: "",
  type: "text",
  maxlength: 20,
};

const customCocktailTitleStyle = ref("normal");
const customCocktailValue = ref("");

// description input
const customCocktailDescValue = ref("");

const submitCustomCocktailForm = () => {
  console.log("폼 제출");
};
</script>

<style scoped lang="scss">
.custom-cocktail-form {
  @include flex(column);
  gap: 40px;
}
</style>
