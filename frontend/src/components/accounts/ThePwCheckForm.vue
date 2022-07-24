<template>
  <div class="change-pw-auth-title">
    <p>회원님의 소중한 정보 보호를 위해&nbsp;</p>
    <p>현재 계정의 비밀번호를 확인해주세요.</p>
  </div>
  <form class="change-pw-auth-form" @submit.prevent="submitChangePwAuthForm">
    <div class="change-pw-auth-input">
      <p>{{ emailInputValue }}</p>
    </div>

    <!-- input: 비밀번호 입력 -->
    <input-basic
      :data="pwInputData"
      :input-style="pwInputStyle"
      v-model="pwInputValue"
    ></input-basic>

    <!-- 유효성 검사 -->
    <div class="change-pw-auth-error" v-if="occurredError">
      <p v-for="(msg, index) in errorMessage" :key="index">{{ msg }}</p>
    </div>

    <router-link to="/user/help/password"
      >비밀번호가 기억나지 않으세요?</router-link
    >
    <!-- 본인 인증 버튼 -->
    <button-basic
      :button-style="[buttonColor, 'long', 'small']"
      :disabled="!pwInputValue"
    >
      확인
    </button-basic>
  </form>
</template>

<script setup lang="ts">
import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { reactive, ref, computed, onBeforeMount } from "vue";
import { useStore } from "vuex";
import {
  checkTripleCombination,
  checkPasswordLength,
} from "../../modules/checkText";

const store = useStore();

// email input
const emailInputValue = computed(
  () => store.getters["personalInfo/getUserInfoId"]
);

// password input
const pwInputData: object = reactive({
  button: true,
  id: "change-pw-input",
  label: "새 비밀번호",
  placeholder: "비밀번호 (8 ~ 20자리)",
  type: "password",
});

const pwInputStyle = ref("normal");
const pwInputValue = ref("");

// 버튼 색상
const buttonColor = computed(() => {
  if (pwInputValue.value) {
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

// 제출
const submit = (data: string) =>
  store.dispatch("password/submitChangePwAuthForm", data);

const submitChangePwAuthForm = () => {
  // 리셋
  errorMessage.length = 0;
  pwInputStyle.value = "normal";

  // 유효성 검사 결과
  const pwConditionA = checkTripleCombination(pwInputValue.value);
  const pwConditionB = checkPasswordLength(pwInputValue.value);

  // 제출시 전달할 데이터

  // 인증 제출
  if (pwConditionA && pwConditionB) {
    submit(pwInputValue.value);
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
  }
};
</script>

<style scoped lang="scss">
.change-pw-auth-title {
  p {
    color: $sub-color;
    @include font($fs-main, $fw-medium);

    @media #{$tablet} {
      display: inline;
    }
  }
}
.change-pw-auth-form {
  @include flex(column);
  gap: 32px;
  width: 100%;

  .change-pw-auth-input {
    padding-bottom: 16px;
    border-bottom: 2px solid $white250;
    p {
      color: $white400;
      @include font($fs-md, $fw-bold);
    }
  }
  .change-pw-auth-error {
    margin-top: -25px;
    color: $danger-color;
    @include font($fs-sm, $fw-regular);

    p {
      margin: 0;
      padding: 0;
    }
  }

  a {
    margin-top: -16px;
    color: $gray100;
    @include font($fs-sm, $fw-regular);
  }
}
</style>
