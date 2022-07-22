<template>
  <form @submit.prevent="submitLoginForm">
    <input-basic
      :data="emailInputData"
      :input-style="emailInputStyle"
      v-model="emailInputValue"
    ></input-basic>
    <input-basic
      :data="pwInputData"
      :input-style="pwInputStyle"
      v-model="pwInputValue"
    ></input-basic>
    <div class="login-error-message" :v-if="occurredError">
      <p v-for="(msg, index) in errorMessage" :key="index">{{ msg }}</p>
    </div>
    <button-basic
      :button-style="[buttonColor, 'long', 'small']"
      :disabled="emailInputValue == '' || pwInputValue == ''"
    >
      로그인
    </button-basic>
  </form>
</template>

<script setup lang="ts">
import { reactive, ref, computed } from "vue";
import { useStore } from "vuex";
import {
  checkTripleCombination,
  checkPasswordLength,
  checkEmailCondition,
} from "../../modules/checkText";

import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";

// input-value
const emailInputValue = ref("");
const pwInputValue = ref("");

// button-style
const buttonColor = computed(() => {
  if (emailInputValue.value !== "" && pwInputValue.value !== "") {
    return "primary";
  } else {
    return "unchecked";
  }
});

// 이메일 input 데이터
const emailInputData: object = reactive({
  button: true,
  id: "login-id-input",
  label: "이메일",
  placeholder: "이메일을 입력하세요.",
  type: "text",
});

const emailInputStyle = ref("normal");

// 비밀번호 input 데이터
const pwInputData: object = reactive({
  button: true,
  id: "login-pw-input",
  label: "비밀번호",
  placeholder: "비밀번호를 입력하세요.",
  type: "password",
});

const pwInputStyle = ref("normal");

// 에러 메시지
const errorMessage: string[] = reactive([]);
const occurredError = ref(false);
const emailConditionErrorMessage: string = "올바른 이메일 형식이 아닙니다.";
const pwConditionAErrorMessage: string =
  "비밀번호는 영어 대문자, 소문자, 숫자, 특수문자 중 3종류 이상이여야 합니다.";
const pwConditionBErrorMessage: string =
  "비밀번호는 최소 8자 최대 20자로 입력되어야 합니다.";

// 제출
const store = useStore();
const data = {
  email: emailInputValue,
  password: pwInputValue,
};
const login = (data: object) => store.dispatch("account/logIn", data);

const submitLoginForm = () => {
  errorMessage.length = 0;
  const emailCondition = checkEmailCondition(emailInputValue.value);
  const pwConditionA = checkTripleCombination(pwInputValue.value);
  const pwConditionB = checkPasswordLength(pwInputValue.value);

  if (emailCondition && pwConditionA && pwConditionB) {
    login(data);
  } else {
    if (!emailCondition) {
      emailInputStyle.value = "error";
      occurredError.value = true;
      errorMessage.push(emailConditionErrorMessage);
    }
    if (!pwConditionB) {
      pwInputStyle.value = "error";
      occurredError.value = true;
      errorMessage.push(pwConditionBErrorMessage);
    }
    if (!pwConditionA) {
      pwInputStyle.value = "error";
      occurredError.value = true;
      errorMessage.push(pwConditionAErrorMessage);
    }
  }
};
</script>

<style scoped lang="scss">
form {
  @include flex(column);
  gap: 2rem;
  width: 100%;
  margin-top: 32px;

  .login-error-message {
    margin-top: -25px;
    color: $danger-color;
    @include font($fs-sm, $fw-regular);

    p {
      margin: 0;
      padding: 0;
    }
  }
}
</style>
