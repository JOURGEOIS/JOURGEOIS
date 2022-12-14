<template>
  <div class="content-container">
    <section>
      <title-block :contents="titleContents" />
      <form @submit.prevent="postCheckEmail">
        <input-basic
          :data="emailInputData"
          :input-style="emailInputStyle"
          v-model.trim="emailInputValue"
        ></input-basic>
        <section class="condition-checker-section" v-if="showDuplicateAlert">
          <condition-checker :props="emailDuplicateCheckerProps" />
        </section>
        <title-block :contents="infoContents" v-if="showButtonContainer" />
        <!-- 인증메일 전송 버튼 -->
        <div>
          <button-basic
            :button-style="[submitButtonColor, 'long', 'small']"
            class="submit-check-email-button"
            v-if="!showButtonContainer"
            :disabled="!isFulfillToSubmit"
          >
            인증메일 전송
          </button-basic>
        </div>
      </form>
    </section>
    <section class="button-container" v-if="showButtonContainer">
      <!--인증메일 재전송 버튼 -->
      <button-basic
        :button-style="['primary-outline', '60%', 'small']"
        @click="postCheckEmail"
      >
        인증메일 재전송
      </button-basic>

      <!-- 다음 버튼: 다음 페이지로 이동 -->
      <button-basic
        :button-style="[nextButtonColor, '38%', 'small']"
        :disabled="!isFulfillToNext"
        @click="confirmEmailAuthentication"
      >
        다음
      </button-basic>
    </section>
  </div>
  <!-- 이메일 전송 중에 나오는 로딩창 -->
  <loading-basic v-if="loadingStatus"></loading-basic>

  <!-- 이메일 인증 없이 다음 누를 때의 popup -->
  <failure-pop-up
    v-if="getEmailNonAuthModalStatus"
    @off-modal="toggleTimedEmailNonAuthModalStatus"
    >이메일이 인증되지 않았습니다.</failure-pop-up
  >
</template>

<script setup lang="ts">
import ConditionChecker from "@/components/accounts/ConditionChecker.vue";
import TitleBlock from "@/components/accounts/TitleBlock.vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import LoadingBasic from "@/components/basics/LoadingBasic.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
import { reactive, ref, computed, watchEffect } from "vue";
import { useStore } from "vuex";
import { checkEmailCondition } from "../../functions/checkText";
import { watch } from "fs";

// 이메일 인증 없이 다음 누른 경우 시간제 모달
const getEmailNonAuthModalStatus = computed(
  () => store.getters["signup/getEmailNonAuthModalStatus"]
);

const toggleTimedEmailNonAuthModalStatus = () => {
  store.dispatch("signup/toggleTimedEmailNonAuthModalStatus");
};

const store = useStore();

// 제목 컴포넌트
const titleContents = reactive({
  mainList: ["이메일을 입력해주세요"],
  subList: ["입력하신 이메일로 확인 메일을 보내드릴게요."],
});

const infoContents = reactive({
  mainList: [],
  subList: [
    "인증은 3분 이내에 진행되어야 해요.",
    "3분을 초과하셨다면 인증메일 재전송을 눌러주세요.",
  ],
});

// 조건 체크에 대한 props들
const emailDuplicateCheckerProps = reactive({
  isChecked: true,
  checkContent: "이미 등록된 이메일입니다!",
  isIconTypeDanger: true,
});

// input-value
const emailInputValue = ref(store.state.signup.signUpEmail);

// 이메일이 형식에 맞다면 true 반환
const isFulfillToSubmit = computed(() => {
  // 입력이 있다면
  return checkEmailCondition(emailInputValue.value);
});
// email input스타일 normal로 정의
const emailInputStyle = ref("normal");

// [인증메일 재전송] / [완료] 있는 buttonContainer를 보일지 여부
let showButtonContainer = ref(false);

const submitButtonColor = computed(() => {
  return isFulfillToSubmit.value ? "primary" : "unchecked";
});

// 이메일 인증 함수
const checkEmailDuplication = (payload: object) => {
  store.dispatch("signup/checkEmailDuplication", payload);
};

// 인증메일 전송 로직
const loadingStatus = ref(false);
const postCheckEmail = async () => {
  // 로딩창 켜기
  loadingStatus.value = true;
  await checkEmailDuplication(
    reactive({
      emailInputValue,
      showButtonContainer,
      showDuplicateAlert,
      loadingStatus,
    })
  );
};

// 이메일 input 데이터
const emailInputData: object = reactive({
  button: true,
  id: "signup-email-input",
  placeholder: "example@naver.com",
  type: "text",
  isDisabled: showButtonContainer,
});

// 인증메일 전송 이후

// 중복된 이메일인 경우 경고 표시
const showDuplicateAlert = ref(false);

watchEffect(() => {
  if (showButtonContainer.value) {
    showDuplicateAlert.value = false;
  }
});

// 이메일 인증이 완료되어 다음 페이지로 넘어가도 될 때 true 반환
const isFulfillToNext = computed(() => {
  return true;
});

// 완료 버튼 색 설정
const nextButtonColor = computed(() => {
  return isFulfillToNext ? "primary" : "disabled";
});

// 다음 페이지로 이동
const nextSignupPage = () => {
  store.dispatch("signup/nextSignupPage");
};

// 이메일 인증 확인
const confirmEmailAuthentication = (payload: object) => {
  store.dispatch("signup/confirmEmailAuthentication", payload);
};
</script>

<style scoped lang="scss">
.content-container {
  @include flex(column);
  gap: 3rem;

  .submit-check-email-button {
    margin-top: 3rem;
  }

  .button-container {
    display: inline-flex;
    justify-content: space-between;
  }
}

.condition-checker-section {
  margin-top: 5px;
}
</style>
