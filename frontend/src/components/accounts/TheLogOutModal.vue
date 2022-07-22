<template>
  <modal-basic modal-color="white" v-if="logOutModalStatus">
    <div class="logout-modal-content">
      <p>로그아웃 하시겠어요?</p>
      <div class="logout-modal-button">
        <button-basic :button-style="['main-blank', '50%', 'small']"
          >취소</button-basic
        >
        <button-basic :button-style="['primary', '50%', 'small']"
          >로그아웃</button-basic
        >
      </div>
    </div>
  </modal-basic>
</template>

<script setup lang="ts">
import ModalBasic from "@/components/basics/ModalBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { useStore } from "vuex";
import { computed } from "vue";

const store = useStore();
const logOutModalStatus = computed(
  () => store.getters["account/getLogOutModalStatus"]
);
</script>

<style scoped lang="scss">
.logout-modal-content {
  @include flex(column);
  justify-content: center;
  align-items: center;
  gap: 36px;
  width: 300px;

  @media (max-width: 300px) {
    width: 80vw;
  }

  p {
    @include font($fs-lg, $fw-medium);
    text-align: center;
  }
}

.logout-modal-button {
  @include flex-xy(center, center);
  gap: 20px;
  width: 100%;

  button {
    @include p-component(md);
    @include shadow-modal;
  }
}
</style>
