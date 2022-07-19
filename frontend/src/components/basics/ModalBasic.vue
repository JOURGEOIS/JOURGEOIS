<!-- 
  :modal-color="색상"
  modalColor 프롭스에 따라 색상이 변합니다.
  modalColor가 primary이면 프라이머리컬러 pop-up이 생성됩니다.
  modalColor가 white이면 하얀색 pop-up이 생성됩니다. 
-->

<template>
  <teleport to="body">
    <div class="modal-container">
      <div class="modal-basic" :class="modalStyle">
        <slot></slot>
      </div>
    </div>
  </teleport>
</template>

<script setup lang="ts">
import selectModalColor from "../../modules/selectModalColor";
const props = defineProps<{
  modalColor: string;
}>();
const { modalStyle }: any = selectModalColor(props.modalColor);
</script>

<style lang="scss" scoped>
@import "@/styles/style.scss";
.modal-container {
  @include flex-xy(center, center);
  position: fixed;
  top: 0;
  height: 100vh;
  width: 100vw;
  background-color: rgba(0, 0, 0, 0.7);
}

.modal-basic {
  @include modal;
  @include font($fs-md, $fw-regular);
  @include hide-scroll;
  width: 300px;
  max-height: 60vh;
  overflow-y: scroll;

  @media #{$tablet} {
    width: 600px;
    font-size: $fs-main;
  }

  @media #{$pc} {
    width: 800px;
  }
}

.primary-modal {
  color: $white;
  background-color: $primary-color;
}

.white-modal {
  color: $main-color;
  background-color: $white;
}
</style>
