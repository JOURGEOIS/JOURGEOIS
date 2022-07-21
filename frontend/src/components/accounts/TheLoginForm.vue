<template>
  <form>
    <input-basic
      :data="idInputData"
      :input-style="idInputStyle"
      v-model="idInputValue"
    ></input-basic>
    <input-basic
      :data="pwInputData"
      :input-style="pwInputStyle"
      v-model="pwInputValue"
    ></input-basic>
    <button-basic :button-style="[buttonColor, 'long', 'small']">
      로그인
    </button-basic>
  </form>
</template>

<script setup lang="ts">
import { reactive, ref, computed } from "vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";

// input-value
const idInputValue = ref("");
const pwInputValue = ref("");

// button-style
const buttonColor = computed(() => {
  if (idInputValue.value !== "" && pwInputValue.value !== "") {
    return "primary";
  } else {
    return "unchecked";
  }
});
// 아이디
const idInputData: object = reactive({
  button: true,
  id: "login-id-input",
  label: "이메일",
  placeholder: "이메일을 입력하세요.",
  type: "email",
});

const idInputStyle = ref("normal");

// 비밀번호
const pwInputData: object = reactive({
  button: true,
  id: "login-pw-input",
  label: "비밀번호",
  placeholder: "비밀번호를 입력하세요.",
  type: "password",
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
