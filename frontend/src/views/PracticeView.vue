<template>
  <div>
    <!-- <modal-pop-up modal-color="primary"> 로그인이 성공했습니다</modal-pop-up> -->
    <!-- <button-basic :button-style="['sub-outline', '200px', 'medium']">
      다음
    </button-basic> -->
    <header-basic :prev="trueValue" :success="trueValue">
      회원가입
    </header-basic>
  </div>
</template>

<script setup lang="ts">
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import ModalPopUp from "@/components/basics/ModalPopUp.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";

const trueValue: boolean = true;
const falseValue: boolean = false;
</script>

<style scoped></style>
