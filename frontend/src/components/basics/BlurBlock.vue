<template>
  <div class="blur-login-modal-content">
    <article>
      <slot></slot>
    </article>
    <button-basic
      :button-style="['primary', '50%', 'small']"
      @click="clickLogin"
      class="blur-login-modal-btn"
    >
      로그인
    </button-basic>
  </div>
</template>

<script setup lang="ts">
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { clickLogin } from "../../functions/clickEvent";
</script>

<style scoped lang="scss">
.blur-login-modal-content {
  @include flex(column);
  gap: 32px;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: 40px;
  left: 50%;
  min-width: 264px;
  width: 80%;
  transform: translate(-50%, 0);
  @include shadow-modal;
  @include font(18px, $fw-medium);
  background-color: white;
  padding: 24px 8px;
  border-radius: 10px;

  @media #{$tablet} {
    width: 300px;
    top: 30px;
  }

  article {
    @include flex(column);
    align-items: center;
    justify-content: center;
  }

  .blur-login-modal-btn {
    padding: 8px 0px;
  }
}
</style>
