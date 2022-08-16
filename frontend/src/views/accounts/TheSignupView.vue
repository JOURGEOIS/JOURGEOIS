<template>
  <div class="signup-view">
    <progress-bar :progress="progress"></progress-bar>
    <header-basic
      :prev="true"
      @prevClicked="clickPrevIconSignupPage"
      :success="false"
    >
      회원가입
    </header-basic>
    <section class="signup-section">
      <signup-page1 v-if="currentPage === 0" />
      <signup-page2 v-if="currentPage === 1" />
      <signup-page3 v-if="currentPage === 2" />
      <signup-page4 v-if="currentPage === 3" />
    </section>
  </div>
  <failure-pop-up v-if="failModalStatus">문제가 발생했습니다.</failure-pop-up>
</template>

<script setup lang="ts">
import SignupPage1 from "@/components/accounts/SignupPage1.vue";
import SignupPage2 from "@/components/accounts/SignupPage2.vue";
import SignupPage3 from "@/components/accounts/SignupPage3.vue";
import SignupPage4 from "@/components/accounts/SignupPage4.vue";
import { computed, onUnmounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import ProgressBar from "@/components/basics/ProgressBar.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
const router = useRouter();
const store = useStore();

// navbar 색깔 부여
store.dispatch("navbar/setNavIconStatus", 4);

// 회원가입view에서 이탈한 경우
const removeSignUpInfo = () => {
  store.dispatch("signup/removeSignUpInfo");
};
onUnmounted(() => {
  // 임시 회원정보 전부 삭제
  removeSignUpInfo();
});

// 현재 페이지 번호 및 페이지 이동
let progress = computed(() => store.getters["signup/getProgress"]);
let currentPage = computed(() => store.getters["signup/getCurrentPage"]);

// 오류 및 실패 모달
const failModalStatus = computed(
  () => store.getters["signup/getFailModalStatus"]
);

// 다음 페이지 이동 actions
const nextSignupPage = () => {
  store.dispatch("signup/nextSignupPage");
};

// 이전 페이지 이동 actions
const prevSignupPage = () => {
  store.dispatch("signup/prevSignupPage");
};

// 이전 페이지 버튼 클릭 이벤트 함수
const clickPrevIconSignupPage = () => {
  if (progress.value === 0) {
    router.go(-1);
  } else {
    prevSignupPage();
  }
};
</script>

<style scoped lang="scss">
.signup-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  .signup-section {
    width: 100%;
    margin-top: 1rem;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }
  }
}
</style>
