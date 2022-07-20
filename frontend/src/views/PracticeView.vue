<template>
  <div>
    <!-- <modal-pop-up modal-color="primary"> 로그인이 성공했습니다</modal-pop-up> -->
    <!-- <button-basic :button-style="['sub-outline', '200px', 'medium']">
      다음
    </button-basic> -->
    <header-basic :prev="falseValue" :success="trueValue">
      회원가입
    </header-basic>
  </div>
</template>

<script setup lang="ts">
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import ModalPopUp from "@/components/basics/ModalPopUp.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import { ref } from "vue";

const trueValue: object = ref(true);
const falseValue: object = ref(false);
</script>

<style scoped></style>
