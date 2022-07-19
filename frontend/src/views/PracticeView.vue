<template>
  <div>
    <h1>Practice</h1>
    <!-- <modal-pop-up modal-color="primary"> 로그인이 성공했습니다</modal-pop-up> -->
    <ButtonBasic :button-style="['primary', '10px', 'large']">다음</ButtonBasic>
  </div>
</template>

<script setup lang="ts">
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import ModalPopUp from "@/components/basics/ModalPopUp.vue";
</script>

<style scoped></style>
