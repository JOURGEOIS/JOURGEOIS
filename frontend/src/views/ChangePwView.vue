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
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import { computed, defineAsyncComponent } from "vue";
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
  }
}
</style>
