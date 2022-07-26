<template>
  <div class="sign-out-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      회원 탈퇴
    </header-basic>

    <!-- 동적 컴포넌트로 페이지를 구성한다.  -->
    <section class="sign-out-section">
      <component :is="currentComponent"></component>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import { computed, defineAsyncComponent, onBeforeMount } from "vue";
import { useStore } from "vuex";

const store = useStore();

// 현재 탭
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
    margin-top: 2rem;

    @media #{$tablet} {
      width: 75%;
    }

    @media #{$pc} {
      width: 50%;
    }
  }
}
</style>
