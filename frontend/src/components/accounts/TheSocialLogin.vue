<template>
  <hr />
  <div class="social-login">
    <p>or</p>
    <button-basic :button-style="['google-login', 'long', 'small']">
      <div class="button-content">
        <img
          src="https://raw.githubusercontent.com/JaeKP/image_repo/main/img/google.png"
        />
        Google 로 로그인
      </div>
    </button-basic>
    <button-basic :button-style="['kakao-login', 'long', 'small']">
      <div class="button-content">
        <img
          src="https://raw.githubusercontent.com/JaeKP/image_repo/main/img/kakao.png"
        />
        KaKao 로 로그인
      </div>
    </button-basic>
    <button-basic :button-style="['naver-login', 'long', 'small']">
      <div class="button-content">
        <img
          src="https://raw.githubusercontent.com/JaeKP/image_repo/main/img/naver.png"
        />
        Naver 로 로그인
      </div>
    </button-basic>
  </div>
</template>

<script setup lang="ts">
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { reactive } from "vue";
</script>

<style scoped lang="scss">
hr {
  @include hr;
  width: 100%;
}

p {
  @include font($fs-sm, $fw-regular);
}

img {
  position: absolute;
  left: 20%;
  width: 30px;
}
.social-login {
  @include flex(column);
  align-items: center;
  justify-content: center;
}

.social-login > * {
  margin-bottom: 1.2rem;
}

.button-content {
  position: relative;
  @include flex-center();
  @include font($fs-md, $fw-regular);
  letter-spacing: $ls-main;
  color: $main-color;
}

.button-content > img {
  position: absolute;
  left: -40px;
}

.google-login-bg {
  border: 1px solid $unchecked-color;
  background-color: $white;
}

.google-login-bg img {
  width: 20px;
  left: -35px;
}

.kakao-login-bg {
  background-color: rgb(253, 220, 63);
}

.naver-login-bg {
  background-color: rgb(3, 199, 90);
}
</style>
