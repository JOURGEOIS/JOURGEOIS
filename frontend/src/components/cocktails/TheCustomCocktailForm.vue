<template>
  <form @submit.prevent="submitCustomCocktailForm" class="custom-cocktail-form">
    <input-basic
      :data="customCocktailTitleData"
      :input-style="customCocktailTitleStyle"
      v-model.trim="customCocktailValue"
    ></input-basic>
    <the-custom-cocktail-image-input
      @change-image="changeCocktailImage"
    ></the-custom-cocktail-image-input>
    <the-custom-cocktail-ingredients-input></the-custom-cocktail-ingredients-input>
    <the-custom-cocktail-recipe-input></the-custom-cocktail-recipe-input>
    <the-custom-cocktail-textarea
      v-model.trim="customCocktailDescValue"
    ></the-custom-cocktail-textarea>
  </form>
</template>

<script setup lang="ts">
import { ref, reactive, computed, unref } from "vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import TheCustomCocktailImageInput from "@/components/cocktails/TheCustomCocktailImageInput.vue";
import TheCustomCocktailTextarea from "@/components/cocktails/TheCustomCocktailTextarea.vue";
import TheCustomCocktailIngredientsInput from "@/components/cocktails/TheCustomCocktailIngredientsInput.vue";
import TheCustomCocktailRecipeInput from "@/components/cocktails/TheCustomCocktailRecipeInput.vue";
import { useStore } from "vuex";
const store = useStore();

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
const customCocktailTIle = unref(
  computed(() => store.getters["customCocktail/getTitle"])
);
const customCocktailValue = ref(customCocktailTIle);

// description input
const customCocktailDescription = unref(
  computed(() => store.getters["customCocktail/getDescription"])
);
const customCocktailDescValue = ref(customCocktailDescription);

// image input
let customCocktailImageValue = reactive({});
const changeCocktailImage = (data: object) => {
  customCocktailImageValue = data;
};

const submitCustomCocktailForm = () => {
  const data = {
    title: customCocktailValue.value,
    description: customCocktailDescValue.value,
    img: customCocktailImageValue,
  };
  store.dispatch("customCocktail/submitCustomCocktailForm", data);
};
</script>

<style scoped lang="scss">
.custom-cocktail-form {
  @include flex(column);
  gap: 40px;
}
</style>
