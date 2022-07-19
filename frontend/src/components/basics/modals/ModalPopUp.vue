<!-- 
  :modal-color="색상"
  modalColor 프롭스에 따라 색상이 변합니다.
  modalColor가 purple이면 보라색 pop-up이 생성됩니다.
  그 외, 하얀색 pop-up이 생성됩니다. 
-->

<template>
  <teleport to="body">
    <div class="modal-container">
      <div class="modal-popup" :class="modalStyle">
        <slot></slot>
      </div>
    </div>
  </teleport>
</template>

<script setup lang="ts">
import selectModalColor from "../../../modules/selectModalColor";
const props = defineProps<{
  modalColor: string;
}>();

const { modalStyle }: any = selectModalColor(props.modalColor);
</script>

<style lang="scss">
@import "@/styles/style.scss";
.modal-container {
  @include flex-xy(center, flex-start);
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0;
}

.modal-popup {
  @include popup;
  @include font(fs-md, fw-regular);
  margin-top: 10vh;
  width: 90vw;
  max-height: 60vh;

  @media #{$tablet} {
    width: 70vw;
  }

  @media #{$pc} {
    width: 70vw;
    max-width: 1000px;
    font-size: $fs-main;
  }
}

.purple-modal {
  color: $white;
  background-color: $primary-color;
}

.white-modal {
  color: $main-color;
  background-color: $white;
}
</style>
