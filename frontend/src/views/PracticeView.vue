<template>
  <div>
    <!-- <modal-pop-up modal-color="primary"> 로그인이 성공했습니다</modal-pop-up> -->
    <!-- <button-basic :button-style="['sub-outline', '200px', 'medium']">
      다음
    </button-basic> -->
    <!-- <header-basic :prev="trueValue" :success="trueValue">
      회원가입
    </header-basic> -->
    <input-basic :data="data" input-style="normal"></input-basic>
  </div>
</template>

<script setup lang="ts">
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import ModalPopUp from "@/components/basics/ModalPopUp.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import { ref, reactive } from "vue";

const trueValue: object = ref(true);
const falseValue: object = ref(false);

const data: object = reactive({
  button: true,
  id: "id-input",
  label: "아이디",
  placeholder: "example@gmail.com",
});
</script>

<style scoped></style>
