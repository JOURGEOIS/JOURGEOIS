<!-- 회원 탈퇴 페이지의 두번째 컴포넌트
회원 탈퇴 전, 마지막으로 확인한다.
1. 동의한다는 내용을 체크하면 탈퇴하기 버튼이 활성화 된다.
2. 체크버튼을 토클로 동의를 해제할 수 있다. (탈퇴하기 버튼이 비활성화 된다.)
3. 탈퇴하면 서버와 로컬스토리지 vuex에 저장된 정보를 삭제한다.
-->

<template>
  <div class="sign-out-content">
    <!-- 회원 탈퇴 설명 문구 -->
    <div class="sign-out-context">
      <p>
        사용하고 계신 아이디를 탈퇴하시면 회원정보 및 개인형 서비스 이용기록은
        모두 삭제되며, 삭제된 데이터는 <span>복구</span>되지 않습니다.
      </p>
      <!-- <p>
        커스텀 칵테일 및 후기는 자동 삭제 되지 않으니, 반드시 탈퇴 전
        <span>삭제</span>하시기 바랍니다.
      </p> -->
    </div>

    <!-- 동의 여부 확인 -->
    <div class="sign-out-checker" @click="clickSignOutChecker">
      <span :class="agreeStatus" class="checker-icon material-icons">
        check_circle_outline
      </span>
      <span>위 내용을 확인하였으며, 이에 동의합니다.</span>
    </div>

    <!-- 버튼 -->
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

  <!-- 실패 팝업 -->
  <failure-pop-up v-if="failPopUp">잠시 후 다시 시도해주세요</failure-pop-up>
</template>

<script setup lang="ts">
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { ref, computed, watch } from "vue";
import { useStore } from "vuex";

const store = useStore();
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

// 실패 팝업
const failPopUp = ref(false);
watch(failPopUp, () => {
  setTimeout(() => (failPopUp.value = false), 2000);
});

// 탈퇴!
const clickSignOut = () => {
  store.dispatch("account/signOut", failPopUp);
};
</script>

<style scoped lang="scss">
.sign-out-content {
  @include flex(column);
  justify-content: center;
  align-items: center;
  gap: 100px;
  margin-top: 16px;
  font-weight: $fw-regular;

  .sign-out-context {
    @include flex(column);
    justify-content: center;
    width: 90%;
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
    @include shadow-popup1;
  }
}

.unchecked {
  color: $unchecked-color;
}

.primary {
  color: $primary-color;
}
</style>
