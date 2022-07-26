<template>
  <div class="sign-out-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      회원 탈퇴
    </header-basic>
    <section class="sign-out-section">
      <sign-out-check> </sign-out-check>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import SignOutCheck from "@/components/accounts/SignOutCheck.vue";
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
