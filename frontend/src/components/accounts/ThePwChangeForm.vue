<template>
  <div class="pw-change-title">
    <p>인증이 완료되었습니다.&nbsp;</p>
    <p>새로운 비밀번호를 설정해주세요.</p>
  </div>
  <form class="pw-change-form" @submit.prevent="submitPwChangeForm">
    <input-basic
      :data="pwInputData"
      :input-style="pwInputStyle"
      v-model="pwInputValue"
    ></input-basic>
    <input-basic
      :data="pwConfirmInputData"
      :input-style="pwConfirmInputStyle"
      v-model="pwConfirmInputValue"
    ></input-basic>
    <div class="forgot-pw-error-message" :v-if="occurredError">
      <p v-for="(msg, index) in errorMessage" :key="index">{{ msg }}</p>
    </div>
    <button-basic
      :button-style="[buttonColor, 'long', 'small']"
      :disabled="pwInputValue == '' || pwConfirmInputValue == ''"
    >
      확인
    </button-basic>
  </form>
</template>

<script setup lang="ts">
import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import {
  checkTripleCombination,
  checkPasswordLength,
  checkIsEqualPassword,
} from "../../modules/checkText";
import { reactive, ref, computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

// name Input
const pwInputData: object = reactive({
  button: true,
  id: "change-pw-input",
  label: "새 비밀번호",
  placeholder: "비밀번호 (8 ~ 20자리)",
  type: "password",
});

const pwInputStyle = ref("normal");
const pwInputValue = ref("");

// email Input
const pwConfirmInputData: object = reactive({
  button: true,
  id: "change-pw-confirm-input",
  label: "새 비밀번호 확인",
  placeholder: "비밀번호 재입력",
  type: "password",
});

const pwConfirmInputStyle = ref("normal");
const pwConfirmInputValue = ref("");

// 버튼 색상
const buttonColor = computed(() => {
  if (pwInputValue.value !== "" && pwConfirmInputValue.value !== "") {
    return "primary";
  } else {
    return "unchecked";
  }
});

// 유효성 검사
const errorMessage: string[] = reactive([]);
const occurredError = ref(false);
const pwConditionAErrorMessage: string =
  "비밀번호는 영어 대문자, 소문자, 숫자, 특수문자 중 3종류 이상이여야 합니다.";
const pwConditionBErrorMessage: string =
  "비밀번호는 최소 8자 최대 20자로 입력되어야 합니다.";
const pwConditionCErrorMessage: string =
  "비밀번호와 비밀번호 확인이 일치하지 않습니다.";

// 제출
const submit = (data: object) =>
  store.dispatch("password/submitChangePwForm", data);
const email = computed(() => store.getters["password/getForgotPwEmail"]);
const submitPwChangeForm = () => {
  // 리셋
  errorMessage.length = 0;
  pwInputStyle.value = "normal";
  pwConfirmInputStyle.value = "normal";

  // 유효성 검사 결과
  const pwConditionA = checkTripleCombination(pwInputValue.value);
  const pwConditionB = checkPasswordLength(pwInputValue.value);
  const pwConditionC = checkIsEqualPassword(
    pwInputValue.value,
    pwConfirmInputValue.value
  );

  // 비밀번호 변경시 전달할 데이터
  const data: object = {
    userId: email.value,
    passwordNew: pwInputValue.value,
    passwordConfirm: pwConfirmInputValue.value,
  };

  // 비밀번호 변경
  if (pwConditionA && pwConditionB && pwConditionC) {
    submit(data);
  } else {
    if (!pwConditionA) {
      pwInputStyle.value = "error";
      occurredError.value = true;
      errorMessage.push(pwConditionAErrorMessage);
    }
    if (!pwConditionB) {
      pwInputStyle.value = "error";
      occurredError.value = true;
      errorMessage.push(pwConditionBErrorMessage);
    }
    if (!pwConditionC) {
      pwInputStyle.value = "error";
      pwConfirmInputStyle.value = "error";
      occurredError.value = true;
      errorMessage.push(pwConditionCErrorMessage);
    }
  }
};
</script>

<style scoped lang="scss">
.pw-change-title {
  margin-bottom: 16px;
  p {
    color: $sub-color;
    @include font($fs-main, $fw-medium);

    @media #{$tablet} {
      display: inline;
    }
  }
}
.pw-change-form {
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
