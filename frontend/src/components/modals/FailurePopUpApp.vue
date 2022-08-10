<template>
  <modal-pop-up modal-color="danger" @off-modal="offModal">
    <div class="failure-popup-content">
      <span class="material-icons"> error_outline </span>
      <slot></slot>
    </div>
  </modal-pop-up>
</template>

<script setup lang="ts">
import ModalPopUp from "@/components/basics/ModalPopUp.vue";

const emit = defineEmits<{ (event: "offModal"): void }>();
const offModal = () => {
  emit("offModal");
};
</script>
<style scoped lang="scss">
.failure-popup-content {
  @include flex-center();
  gap: 16px;

  .material-icons {
    font-size: 32px;
  }
}
</style>
