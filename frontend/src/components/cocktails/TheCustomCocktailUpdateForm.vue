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
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const store = useStore();
const route = useRoute();

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

// title input
const customCocktailValue = computed({
  get: () => store.getters["customCocktail/getTitle"],
  set: (newValue) => store.dispatch("customCocktail/setTitle", newValue),
});

// description input
const customCocktailDescValue = computed({
  get: () => store.getters["customCocktail/getDescription"],
  set: (newValue) => store.dispatch("customCocktail/setDescription", newValue),
});

// image input
let customCocktailImageValue = reactive({});
const changeCocktailImage = (data: object) => {
  customCocktailImageValue = data;
};

const submitCustomCocktailForm = () => {
  if (!(customCocktailImageValue instanceof File)) {
    const data = {
      title: customCocktailValue.value,
      description: customCocktailDescValue.value,
      img: "",
      postId: route.params.feedId,
    };
    store.dispatch("customCocktail/updateCustomCocktail", data);
  } else {
    const data = {
      title: customCocktailValue.value,
      description: customCocktailDescValue.value,
      img: customCocktailImageValue,
      postId: route.params.feedId,
    };
    store.dispatch("customCocktail/updateCustomCocktail", data);
  }
};
</script>

<style scoped lang="scss">
.custom-cocktail-form {
  @include flex(column);
  gap: 40px;
}
</style>
