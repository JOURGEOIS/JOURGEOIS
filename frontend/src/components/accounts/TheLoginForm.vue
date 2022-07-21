<template>
  <form>
    <input-basic :data="idInputData" :input-style="idInputStyle"></input-basic>
    <input-basic :data="pwInputData" :input-style="pwInputStyle"></input-basic>
    <button-basic :button-style="['unchecked', 'long', 'small']">
      로그인
    </button-basic>
  </form>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";

// 아이디
const idInputData: object = reactive({
  button: true,
  id: "login-id-input",
  label: "아이디",
  placeholder: "아이디를 입력하세요.",
});

const idInputStyle = ref("normal");

// 비밀번호
const pwInputData: object = reactive({
  button: true,
  id: "login-pw-input",
  label: "비밀번호",
  placeholder: "비밀번호를 입력하세요.",
});

const pwInputStyle = ref("normal");
</script>

<style scoped lang="scss">
form {
  @include flex(column);
  gap: 2rem;
  width: 100%;
  margin-top: 2rem;
}
</style>
