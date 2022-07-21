<template>
  <div class="login-view">
    <header-basic :prev="true" :success="false"> 로그인 </header-basic>
    <section>
      <the-login-form></the-login-form>
      <div class="login-link">
        <p>비밀번호 찾기</p>
        <p>회원가입</p>
      </div>
      <the-social-login></the-social-login>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheLoginForm from "@/components/accounts/TheLoginForm.vue";
import TheSocialLogin from "@/components/accounts/TheSocialLogin.vue";
</script>

<style scoped lang="scss">
.login-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
}

section {
  width: 100%;
  margin-top: 2rem;

  @media #{$tablet} {
    width: 80%;
  }

  @media #{$pc} {
    width: 70%;
  }
}

section > *:first-child {
  margin-bottom: 1rem;
}

.login-link {
  @include flex-center();
  gap: 2rem;
  @include font($fs-md, $fw-regular);
}
</style>
