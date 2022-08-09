<!-- 회원 탈퇴 페이지의 첫번째 컴포넌트 
  비밀번호 확인을 통해 본인인증을 진행한다. 
  1. vuex에 저장된 유저 정보를 토대로 이메일 데이터를 가져온다. 
  2. 비밀번호 기입
  3. 서버에 위의 두 데이터를 전송하여 본인이 맞는지 확인
  
  또한, 프론트단에서 진행하는 유효성 검사(87번째 줄)와 에러 메시지가 표시되는 공간이 인풋과 버튼 사이에 존재한다. 
-->

<template>
  <div class="sign-out-auth-title">
    <p>회원님의 소중한 정보 보호를 위해&nbsp;</p>
    <p>현재 계정의 비밀번호를 확인해주세요.</p>
  </div>
  <form class="sign-out-auth-form" @submit.prevent="submitChangePwAuthForm">
    <div class="sign-out-auth-input">
      <p>{{ emailInputValue }}</p>
    </div>

    <!-- input: 비밀번호 입력 -->
    <input-basic
      :data="pwInputData"
      :input-style="pwInputStyle"
      v-model.trim="pwInputValue"
    ></input-basic>

    <!-- 유효성 검사 -->
    <div class="sign-out-auth-error" v-if="occurredError">
      <p v-for="(msg, index) in errorMessage" :key="index">{{ msg }}</p>
    </div>

    <!-- catch 메시지-->
    <p class="sign-out-Fail-message" v-if="pwChangeFailStatus">
      비밀번호가 틀렸습니다. 다시 확인해주세요.
    </p>

    <!-- 비밀번호 찾기 창으로 이동 -->
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
} from "../../functions/checkText";

const store = useStore();

// email input
const emailInputValue = computed(
  () => store.getters["personalInfo/getUserInfoId"]
);

// password input
const pwInputData: object = reactive({
  button: true,
  id: "sign-out-input",
  label: "현재 비밀번호",
  placeholder: "비밀번호 (8 ~ 20자리)",
  maxlength: 20,
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

// 에러 메시지
const pwChangeFailStatus = ref(false);

// 제출
const submit = (data: object) =>
  store.dispatch("account/submitSignOutAuth", data);

const submitChangePwAuthForm = () => {
  // 리셋
  errorMessage.length = 0;
  pwChangeFailStatus.value = false;
  pwInputStyle.value = "normal";

  // 유효성 검사 결과
  const pwConditionA = checkTripleCombination(pwInputValue.value);
  const pwConditionB = checkPasswordLength(pwInputValue.value);

  // 제출시 전달할 데이터
  const data = {
    pwInputValue,
    failStatus: pwChangeFailStatus,
  };
  // 인증 제출
  if (pwConditionA && pwConditionB) {
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
  }
};
</script>

<style scoped lang="scss">
.sign-out-auth-title {
  p {
    color: $sub-color;
    @include font($fs-main, $fw-medium);

    @media #{$tablet} {
      display: inline;
    }
  }
}
.sign-out-auth-form {
  @include flex(column);
  gap: 32px;
  width: 100%;

  .sign-out-auth-input {
    padding-bottom: 16px;
    border-bottom: 2px solid $white250;
    p {
      color: $white400;
      @include font($fs-md, $fw-bold);
    }
  }
  .sign-out-auth-error {
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
  .sign-out-Fail-message {
    margin-top: -25px;
    color: $danger-color;
    @include font(13px, $fw-regular);
  }
}
</style>
