<template>
  <div class="sign-out-content">
    <div class="sign-out-context">
      <p>
        사용하고 계신 아이디를 탈퇴하시면 회원정보 및 개인형 서비스 이용기록은
        모두 삭제되며, 삭제된 데이터는 <span>복구</span>되지 않습니다.
      </p>
      <p>
        커스텀 칵테일 및 후기는 자동 삭제 되지 않으니, 반드시 탈퇴 전
        <span>삭제</span>하시기 바랍니다.
      </p>
    </div>
    <div class="sign-out-checker" @click="clickSignOutChecker">
      <span :class="agreeStatus" class="checker-icon material-icons">
        check_circle_outline
      </span>
      <span>위 내용을 확인하였으며, 이에 동의합니다.</span>
    </div>
    <div class="sign-out-btn">
      <button-basic
        :button-style="[buttonColor, 'long', 'small']"
        :disabled="agreeStatus === 'unchecked'"
        @click="clickSignOut"
      >
        탈퇴하기
      </button-basic>
      <button-basic
        :button-style="['main-blank', 'long', 'small']"
        @click="$router.go(-1)"
      >
        돌아가기
      </button-basic>
    </div>
  </div>
</template>

<script setup lang="ts">
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { ref, computed } from "vue";

const agreeStatus = ref("unchecked");

const clickSignOutChecker = () => {
  if (agreeStatus.value === "unchecked") {
    agreeStatus.value = "primary";
  } else {
    agreeStatus.value = "unchecked";
  }
};

const buttonColor = computed(() => {
  if (agreeStatus.value === "unchecked") {
    return "sub";
  } else {
    return "primary";
  }
});

const clickSignOut = () => {
  console.log("클릭");
};
</script>

<style scoped lang="scss">
.sign-out-content {
  @include flex(column);
  gap: 100px;
  margin-top: 16px;
  font-weight: $fw-medium;

  .sign-out-context {
    @include flex(column);
    justify-content: center;
    margin-bottom: 40px;

    span {
      color: $primary-color;
      font-weight: $fw-medium;
    }
  }

  .sign-out-checker {
    @include flex-center;
    gap: 5px;
    user-select: none;
  }

  .sign-out-btn {
    @include flex-center;
    gap: 32px;
  }

  button {
    @include shadow-modal;
  }
}

.unchecked {
  color: $unchecked-color;
}

.primary {
  color: $primary-color;
}
</style>
