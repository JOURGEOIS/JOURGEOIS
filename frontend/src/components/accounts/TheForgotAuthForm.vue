<!-- 비밀번호 찾기 페이지의 두번째 컴포넌트 
  이메일 인증이 진행된다. 
  1. 앞서 기입한 이메일에 인증 메일은 보낸다. 
  2. 재전송 할 수 있다. 
  3. 이메일 인증이 되지 않으면, 다음 페이지로 넘어갈 수 없다. 

  인풋과 버튼 사이에 이메일 인증은 3분 이내로 해야 한다는 사항과 에러 메시지를 배치한다. 
-->

<template>
  <div class="forgot-pw-auth-title">
    <p>회원님의 비밀번호 재설정을 위한&nbsp;</p>
    <p>인증이 필요합니다.</p>
  </div>
  <form class="forgot-pw-auth-form" @submit.prevent="submitForgotPwAuthForm">
    <!-- input: 이메일 (disabled상태) -->
    <input-basic
      :data="emailInputData"
      input-style="normal"
      v-model="emailInputValue"
    ></input-basic>

    <!-- 최초로 이메일을 전송하는 버튼 -->
    <button-basic
      :button-style="['primary', 'long', 'small']"
      v-if="!forgotPwAuthBtnStatus"
    >
      인증번호 받기
    </button-basic>
  </form>

  <!-- catch 메시지 -->
  <p class="forgot-pw-auth-fail-error" v-if="authFailError">
    오류가 발생했습니다. 잠시 후에 시도해주세요.
  </p>

  <!-- 전송 성공 후, 생기는 섹션 -->
  <section class="forgot-pw-auth-section" v-if="forgotPwAuthBtnStatus">
    <p>인증은 3분 이내에 진행되어야 합니다.&nbsp;</p>
    <p>3분은 초과했다면 인증번호 재전송 버튼을 눌러주세요.</p>

    <!-- 전송 후, 확인 과정 중 발생하는 catch 메시지 -->
    <p class="forgot-pw-auth-error" v-if="forgotPwAuthError">
      인증이 되지 않았습니다.이메일을 다시 한번 확인해주세요.
    </p>
    <div>
      <!-- 재전송 버튼: 이메일 재전송 -->
      <button-basic
        :button-style="['primary-outline', '60%', 'small']"
        @click="submitForgotPwAuthForm"
      >
        인증메일 재전송
      </button-basic>

      <!-- 다음 버튼: 다음 페이지로 이동 -->
      <button-basic
        :button-style="['primary', '40%', 'small']"
        @click="confirmAuthEmail"
      >
        다음
      </button-basic>
    </div>
  </section>
  <!-- 이메일 전송 중에 나오는 로딩창 -->
  <loading-basic v-if="loadingStatus"></loading-basic>
</template>

<script setup lang="ts">
import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import LoadingBasic from "@/components/basics/LoadingBasic.vue";
import { reactive, ref, computed, onBeforeMount } from "vue";
import { useStore } from "vuex";
const store = useStore();

// email Input
const emailInputData: object = reactive({
  button: false,
  id: "forgot-pw-email-input",
  label: "",
  placeholder: "",
  type: "text",
  isDisabled: true,
});

const emailInputValue = computed(
  () => store.getters["password/getForgotPwEmail"]
);

// 전송 실패
const authFailError = ref(false);

// 버튼들 section
const forgotPwAuthBtnStatus = ref(false);

// 에러 메시지
const forgotPwAuthError = ref(false);

// 로딩창
const loadingStatus = ref(false);

// 이메일 전송
const payload = {
  btnStatus: forgotPwAuthBtnStatus,
  loadingStatus,
  authFailError,
};

const submitForgotPwAuthForm = () => {
  loadingStatus.value = true;
  store.dispatch("password/submitForgotPwAuthForm", payload);
};

// 이메일 인증 확인
const confirmAuthEmail = () => {
  store.dispatch("password/confirmAuthEmail", forgotPwAuthError);
};
</script>

<style scoped lang="scss">
.forgot-pw-auth-title {
  p {
    color: $sub-color;
    @include font($fs-main, $fw-medium);

    @media #{$tablet} {
      display: inline;
    }
  }
}
.forgot-pw-auth-form {
  @include flex(column);
  gap: 2rem;
  width: 100%;
}

.forgot-pw-auth-fail-error {
  margin-top: -25px;
  color: $danger-color;
  @include font(13px, $fw-regular);
}

.forgot-pw-auth-section {
  margin-top: -25px;
  p {
    color: $sub-color;
    @include font($fs-sm, $fw-regular);
  }
  .forgot-pw-auth-error {
    margin-top: 16px;
    color: $danger-color;
    @include font(13px, $fw-regular);
  }

  div {
    @include flex-center;
    margin-top: 24px;
    gap: 16px;
  }
}
</style>
