<template>
  <div class="forgot-fw-title">
    <p>회원님의 비밀번호를 찾기 위해서 &nbsp;</p>
    <p>이름과 이메일이 필요합니다.</p>
  </div>
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
    <div class="forgot-pw-error-message" v-if="forgotPwErrorMsg">
      <p>
        주류주아에 등록되지 않은 이메일이거나, 이름 또는 이메일을 잘못
        입력하였습니다.
      </p>
    </div>
    <div class="forgot-pw-error-message" v-if="occurredError">
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
import { reactive, ref, computed, onBeforeMount } from "vue";
import { checkENKR, checkEmailCondition } from "../../modules/checkText";
import { useStore } from "vuex";
const store = useStore();

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

// 에러 메시지
const forgotPwErrorMsg = computed(
  () => store.getters["password/getForgotPwErrorMsgStatus"]
);

const toggleForgotPwErrorMsg = (value: boolean) => {
  store.dispatch("password/toggleForgotPwErrorMsg", value);
};

// submit
const submit = (data: object) =>
  store.dispatch("password/submitForgotPwForm", data);

const submitForgotPwForm = () => {
  // 리셋
  toggleForgotPwErrorMsg(false);
  errorMessage.length = 0;
  nameInputStyle.value = "normal";
  emailInputStyle.value = "normal";

  // 유효성 검사
  const nameCondition = checkENKR(nameInputValue.value);
  const emailCondition = checkEmailCondition(emailInputValue.value);

  // 전달 해야 하는 데이터
  const data: object = {
    userId: emailInputValue.value,
    userName: nameInputValue.value,
  };

  if (nameCondition && emailCondition) {
    submit(data);
  } else {
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
  }
};
</script>

<style scoped lang="scss">
.forgot-fw-title {
  margin-bottom: 16px;
  p {
    color: $sub-color;
    @include font($fs-main, $fw-medium);

    @media #{$tablet} {
      display: inline;
    }
  }
}
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
