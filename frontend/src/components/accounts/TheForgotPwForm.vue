<template>
  <form class="forgot-fw-form" @submit.prevent="submitForgotPwForm">
    <input-basic
      :data="nameInputData"
      :input-style="nameInputStyle"
      v-model="nameInputValue"
    ></input-basic>
    <input-basic
      :data="emailInputData"
      :input-style="emailInputStyle"
      v-model="emailInputValue"
    ></input-basic>
    <div class="forgot-pw-error-message" :v-if="occurredError">
      <p v-for="(msg, index) in errorMessage" :key="index">{{ msg }}</p>
    </div>
    <button-basic
      :button-style="[buttonColor, 'long', 'small']"
      :disabled="nameInputValue == '' || emailInputValue == ''"
    >
      비밀번호 찾기
    </button-basic>
  </form>
</template>

<script setup lang="ts">
import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { reactive, ref, computed } from "vue";
import { checkENKR, checkEmailCondition } from "../../modules/checkText";

// name Input
const nameInputData: object = reactive({
  button: true,
  id: "forgot-pw-name-input",
  label: "이름",
  placeholder: "이름을 입력하세요.",
  type: "text",
});

const nameInputStyle = ref("normal");
const nameInputValue = ref("");

// email Input
const emailInputData: object = reactive({
  button: true,
  id: "forgot-pw-email-input",
  label: "이메일",
  placeholder: "이메일을 입력하세요.",
  type: "text",
});

const emailInputStyle = ref("normal");
const emailInputValue = ref("");

// 버튼 색상
const buttonColor = computed(() => {
  if (nameInputValue.value !== "" && emailInputValue.value !== "") {
    return "primary";
  } else {
    return "unchecked";
  }
});

// 유효성 검사
const errorMessage: string[] = reactive([]);
const occurredError = ref(false);
const nameConditionErrorMessage: string =
  "이름은 한글 또는 영어만 입력이 가능합니다.";
const emailConditionAErrorMessage: string = "올바른 이메일 형식이 아닙니다.";

const submitForgotPwForm = () => {
  errorMessage.length = 0;

  const nameCondition = checkENKR(nameInputValue.value);
  const emailCondition = checkEmailCondition(emailInputValue.value);

  if (!nameCondition) {
    nameInputStyle.value = "error";
    occurredError.value = true;
    errorMessage.push(nameConditionErrorMessage);
  }

  if (!emailCondition) {
    emailInputStyle.value = "error";
    occurredError.value = true;
    errorMessage.push(emailConditionAErrorMessage);
  }
};
</script>

<style scoped lang="scss">
.forgot-fw-form {
  @include flex(column);
  gap: 2rem;
  width: 100%;
  margin-top: 32px;

  .forgot-pw-error-message {
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
