<template>
  <div class="login-view">
    <header-basic :prev="true" :success="true"> 로그인 </header-basic>
    <section>
      <the-login-form></the-login-form>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheLoginForm from "@/components/accounts/TheLoginForm.vue";
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
</style>
