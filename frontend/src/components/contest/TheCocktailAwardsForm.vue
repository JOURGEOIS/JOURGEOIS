<template>
  <form class="the-contest-form" @submit.prevent="submitContestForm">
    <input-basic
      :data="contestTitleData"
      :input-style="contestTitleStyle"
      v-model.trim="contestTitleValue"
    >
    </input-basic>
    <the-custom-cocktail-image-input
      @change-image="changeContestImage"
    ></the-custom-cocktail-image-input>
    <the-custom-cocktail-textarea
      v-model.trim="contestDescValue"
    ></the-custom-cocktail-textarea>
    <the-contest-agree-input v-model="isAgreeValue"></the-contest-agree-input>
  </form>
</template>

<script setup lang="ts">
import InputBasic from "@/components/basics/InputBasic.vue";
import TheCustomCocktailImageInput from "@/components/cocktails/TheCustomCocktailImageInput.vue";
import TheCustomCocktailTextarea from "@/components/cocktails/TheCustomCocktailTextarea.vue";
import TheContestAgreeInput from "@/components/contest/TheContestAgreeInput.vue";
import { ref, reactive } from "vue";
import { useStore } from "vuex";
const store = useStore();

// title input
const contestTitleData = {
  button: true,
  required: true,
  id: "contest-title-input",
  label: "칵테일 이름",
  placeholder: "",
  type: "text",
  maxlength: 20,
};

const contestTitleStyle = ref("normal");
const contestTitleValue = ref("");

// imageInput
let contestImageValue = reactive({});
const changeContestImage = (data: object) => {
  contestImageValue = data;
};

// textarea
const contestDescValue = ref("");
const isAgreeValue = ref(false);

// 제출
const submitContestForm = () => {
  const data = {
    title: contestTitleValue.value,
    description: contestDescValue.value,
    img: contestImageValue,
    isAgree: isAgreeValue.value,
  };
  store.dispatch("contest/checkContestForm", data);
};
</script>

<style scoped lang="scss">
.the-contest-form {
  @include flex(column);
  gap: 40px;
}
</style>
