<template>
  <div>
    <!-- <modal-pop-up modal-color="primary"> 로그인이 성공했습니다</modal-pop-up> -->
    <!-- <button-basic :button-style="[`${item}`, 'long', 'small']">
      Basic
    </button-basic> -->
    <!-- <header-basic :prev="trueValue" :success="trueValue">
      회원가입
    </header-basic> -->
    <!-- <input-basic :data="data" :input-style="inputStyle"></input-basic> -->
    <profile-image :profile-image="profileImage"></profile-image>
  </div>
</template>

<script setup lang="ts">
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import ModalPopUp from "@/components/basics/ModalPopUp.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import ProfileImage from "@/components/basics/ProfileImage.vue";
import { ref, reactive } from "vue";

const profileImage = reactive({
  image:
    "https://github.com/JaeKP/image_repo/blob/main/img/%EB%91%98%EC%BD%94%EC%BD%94!!.png?raw=true",
  width: "100px",
});

const data: object = reactive({
  button: true,
  id: "id-input",
  label: "아이디",
  placeholder: "example@gmail.com",
});

const inputStyle = ref("normal");

const colors = reactive([
  "primary",
  "red",
  "emerald",
  "blue",
  "navy",
  "main",
  "sub",
]);
</script>

<style scoped>
div {
  margin: 1rem 0;
}
</style>
