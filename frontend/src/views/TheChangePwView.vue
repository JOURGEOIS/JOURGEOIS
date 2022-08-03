<!-- 비밀번호 변경 페이지 -->
<template>
  <div class="change-pw-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      비밀번호 재설정
    </header-basic>

    <!-- 동적 컴포넌트로 페이지를 구성한다.  -->
    <section class="change-pw-section">
      <component :is="currentComponent"> </component>
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
const index = computed(() => store.getters["password/getChangePwCurrentTab"]);

const componentArray = [
  defineAsyncComponent(
    () => import("@/components/accounts/ThePwCheckForm.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/accounts/ThePwChangeForm.vue")
  ),
];

const currentComponent = computed(() => {
  return componentArray[index.value];
});

// vuex 리셋하기 (현재 탭)
const changeCurrentComponent = (value: number) =>
  store.dispatch("password/changePwCurrentTab", value);

onBeforeMount(() => {
  changeCurrentComponent(0);
});
</script>

<style scoped lang="scss">
.change-pw-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  section {
    @include flex(column);
    width: 100%;
    gap: 36px;
    margin-top: 36px;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }
  }
}
</style>
