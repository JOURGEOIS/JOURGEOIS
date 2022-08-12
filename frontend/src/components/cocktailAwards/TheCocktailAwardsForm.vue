<template>
  <form
    class="the-cocktail-awards-form"
    @submit.prevent="submitCocktailAwardsForm"
  >
    <input-basic
      :data="cocktailAwardsTitleData"
      :input-style="cocktailAwardsTitleStyle"
      v-model.trim="cocktailAwardsTitleValue"
    >
    </input-basic>
    <the-custom-cocktail-image-input
      @change-image="changeCocktailAwardsImage"
    ></the-custom-cocktail-image-input>
    <the-custom-cocktail-textarea
      v-model.trim="cocktailAwardsDescValue"
    ></the-custom-cocktail-textarea>
    <the-cocktail-awards-agree-input
      v-model="isAgreeValue"
    ></the-cocktail-awards-agree-input>
  </form>
</template>

<script setup lang="ts">
import InputBasic from "@/components/basics/InputBasic.vue";
import TheCustomCocktailImageInput from "@/components/cocktails/TheCustomCocktailImageInput.vue";
import TheCustomCocktailTextarea from "@/components/cocktails/TheCustomCocktailTextarea.vue";
import TheCocktailAwardsAgreeInput from "@/components/cocktailAwards/TheCocktailAwardsAgreeInput.vue";
import { ref, reactive } from "vue";
import { useStore } from "vuex";
const store = useStore();

// title input
const cocktailAwardsTitleData = {
  button: true,
  required: true,
  id: "cocktail-awards-title-input",
  label: "칵테일 이름",
  placeholder: "",
  type: "text",
  maxlength: 20,
};

const cocktailAwardsTitleStyle = ref("normal");
const cocktailAwardsTitleValue = ref("");

// imageInput
let cocktailAwardsImageValue = reactive({});
const changeCocktailAwardsImage = (data: object) => {
  cocktailAwardsImageValue = data;
};

// textarea
const cocktailAwardsDescValue = ref("");
const isAgreeValue = ref(false);

// 제출
const submitCocktailAwardsForm = () => {
  const data = {
    title: cocktailAwardsTitleValue.value,
    description: cocktailAwardsDescValue.value,
    img: cocktailAwardsImageValue,
    isAgree: isAgreeValue.value,
  };
  store.dispatch("cocktailAwards/checkCocktailAwardsForm", data);
};
</script>

<style scoped lang="scss">
.the-cocktail-awards-form {
  @include flex(column);
  gap: 40px;
}
</style>
