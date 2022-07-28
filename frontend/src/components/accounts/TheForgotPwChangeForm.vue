<!-- 비밀번호 찾기 페이지의 세번째 컴포넌트 
  이메일 인증까지 완료한 대상이 비밀번호를 수정한다. 
  1. 비밀번호 기입
  2. 비밀번호 확인
  
  또한, 프론트단에서 진행하는 유효성 검사(92번째 줄)와 에러 메시지가 표시되는 공간이 인풋과 버튼 사이에 존재한다. 
-->

<template>
  <div class="pw-change-title">
    <p>인증이 완료되었습니다.&nbsp;</p>
    <p>새로운 비밀번호를 입력해주세요.</p>
  </div>
  <form class="pw-change-form" @submit.prevent="submitPwChangeForm">
    <!-- input: 비밀번호 입력 -->
    <input-basic
      :data="pwInputData"
      :input-style="pwInputStyle"
      v-model="pwInputValue"
    ></input-basic>

    <!-- input: 비밀번호 확인 -->
    <input-basic
      :data="pwConfirmInputData"
      :input-style="pwConfirmInputStyle"
      v-model="pwConfirmInputValue"
    ></input-basic>

    <!-- 유효성 검사 -->
    <div class="forgot-pw-error-message" v-if="occurredError">
      <p v-for="(msg, index) in errorMessage" :key="index">{{ msg }}</p>
    </div>

    <!-- catch 메시지-->
    <p class="forgot-pw-Fail-message" v-if="pwChangeFailStatus">
      오류가 발생했습니다. 잠시 후에 시도해주세요.
    </p>
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
import { reactive, ref, computed } from "vue";
import { useStore } from "vuex";
import {
  checkTripleCombination,
  checkPasswordLength,
  checkIsEqualPassword,
} from "../../functions/checkText";

const store = useStore();

// pw Input
const pwInputData: object = reactive({
  button: true,
  id: "change-pw-input",
  label: "새 비밀번호",
  placeholder: "비밀번호 (8 ~ 20자리)",
  maxlength: 20,
  type: "password",
});

const pwInputStyle = ref("normal");
const pwInputValue = ref("");

// pwCheck Input
const pwConfirmInputData: object = reactive({
  button: true,
  id: "change-pw-confirm-input",
  label: "새 비밀번호 확인",
  placeholder: "비밀번호 재입력",
  maxlength: 20,
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

// 에러 메시지
const pwChangeFailStatus = ref(false);

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
    email: email.value,
    passwordNew: pwInputValue.value,
    passwordConfirm: pwConfirmInputValue.value,
    failStatus: pwChangeFailStatus,
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
  gap: 32px;
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
  .forgot-pw-Fail-message {
    margin-top: -25px;
    color: $danger-color;
    @include font(13px, $fw-regular);
  }
}
</style>
