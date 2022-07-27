<!-- 비밀번호 찾기 페이지 -->
<template>
  <div class="forgot-pw-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      비밀번호 찾기
    </header-basic>

    <!-- 동적 컴포넌트로 페이지를 구성한다.  -->
    <section class="forgot-pw-section">
      <component :is="currentComponent"> </component>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import { computed, defineAsyncComponent, onBeforeMount } from "vue";
import { useStore } from "vuex";

const store = useStore();

// 현재 탭
const index = computed(() => store.getters["password/getForgotPwCurrentTab"]);
const componentArray = [
  defineAsyncComponent(
    () => import("@/components/accounts/TheForgotPwForm.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/accounts/TheForgotAuthForm.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/accounts/TheForgotPwChangeForm.vue")
  ),
];
const currentComponent = computed(() => {
  return componentArray[index.value];
});

//vuex 리셋하기 (현재 탭, 에러 메시지, 이메일 정보)
const changeCurrentComponent = (value: number) =>
  store.dispatch("password/changeForgotPwCurrentTab", value);

const toggleForgotPwErrorMsg = (value: boolean) => {
  store.dispatch("password/toggleForgotPwErrorMsg", value);
};

const changeForgotPwEmail = (email: string) =>
  store.dispatch("password/changeForgotPwEmail", email);

onBeforeMount(() => {
  changeCurrentComponent(0);
  toggleForgotPwErrorMsg(false);
  changeForgotPwEmail("");
});
</script>

<style scoped lang="scss">
.forgot-pw-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  section {
    @include flex(column);
    width: 100%;
    gap: 36px;
    margin-top: 2rem;
  }
}

a {
  text-decoration: none;
}
</style>
