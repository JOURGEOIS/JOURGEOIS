<template>
  <div class="sign-out-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      회원 탈퇴
    </header-basic>

    <!-- 동적 컴포넌트로 페이지를 구성한다.  -->
    <section class="sign-out-section top-view">
      <component :is="currentComponent"></component>
    </section>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { computed, defineAsyncComponent, onBeforeMount } from "vue";
import { useStore } from "vuex";

const store = useStore();

// navbar 색깔 부여
store.dispatch("navbar/setNavIconStatus", 4);

// 현재 탭 계산
const index = computed(() => store.getters["account/getSignOutCurrentTab"]);
const componentArray = [
  defineAsyncComponent(
    () => import("@/components/accounts/TheSignOutAuthForm.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/accounts/TheSignOutCheck.vue")
  ),
];
const currentComponent = computed(() => {
  return componentArray[index.value];
});

// vuex 리셋하기 (현재 탭)
const changeCurrentComponent = (value: number) =>
  store.dispatch("account/changeSignOutCurrentTab", value);
onBeforeMount(() => {
  changeCurrentComponent(0);
});
</script>

<style scoped lang="scss">
.sign-out-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  section {
    @include flex(column);
    width: 100%;
    gap: 36px;

    @media #{$tablet} {
      width: 75%;
    }

    @media #{$pc} {
      width: 50%;
    }
  }
}
</style>
