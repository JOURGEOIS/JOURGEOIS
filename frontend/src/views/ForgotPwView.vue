<template>
  <div class="forgot-pw-view">
    <header-basic :prev="true" :success="false"> 비밀번호 찾기 </header-basic>
    <section class="forgot-pw-section">
      <p>회원님의 비밀번호를 찾기 위해선 &nbsp</p>
      <p>이름, 아이디, 이메일이 필요합니다.</p>
      <the-forgot-pw-form></the-forgot-pw-form>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheForgotPwForm from "@/components/accounts/TheForgotPwForm.vue";
</script>

<style scoped lang="scss">
.forgot-pw-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  section {
    width: 100%;
    margin-top: 2rem;

    p {
      color: $sub-color;
      @include font($fs-main, $fw-medium);

      @media #{$tablet} {
        display: inline;
      }
    }
  }
}
</style>
