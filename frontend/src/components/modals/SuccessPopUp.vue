<template>
  <modal-pop-up modal-color="primary" @off-modal="offModal">
    <div class="success-popup-content">
      <div class="check-icon">
        <div class="icon-line line-left"></div>
        <div class="icon-line line-right"></div>
      </div>
      <div class="check-title">
        <slot></slot>
      </div>
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
@keyframes icon-line-left {
  0% {
    width: 0px;
  }
  50% {
    width: 20px;
  }
}

@keyframes icon-line-right {
  0% {
    width: 0px;
  }
  49% {
    width: 0px;
  }
  50% {
    left: 18px;
    bottom: 3.5px;
    width: 1px;
  }
  60% {
    left: 17px;
    bottom: 4px;
    width: 5px;
  }

  65% {
    left: 16.5px;
    bottom: 5px;
    width: 8px;
  }

  70% {
    left: 16px;
    bottom: 6px;
    width: 11px;
  }

  75% {
    left: 16px;
    bottom: 7px;
    width: 14px;
  }

  80% {
    left: 15px;
    bottom: 8px;
    width: 17px;
  }

  85% {
    left: 15px;
    bottom: 9.5px;
    width: 20px;
  }

  90% {
    left: 14.5px;
    bottom: 10.5px;
    width: 23px;
  }

  95% {
    left: 14px;
    bottom: 12px;
    width: 27px;
  }

  100% {
    left: 13.5px;
    bottom: 13px;
    width: 30px;
  }
}

.success-popup-content {
  position: relative;
  @include flex-center();
  gap: 16px;
  height: 30px;
}

.check-icon {
  position: absolute;
  top: -10px;
  left: -10px;
  width: 39px;
  height: 39px;

  .icon-line {
    position: absolute;
    height: 5px;
    border-radius: 2px;
    background-color: $white;

    &.line-left {
      bottom: 8.5px;
      left: 3.5px;
      width: 20px;
      transform: rotate(45deg);
      animation: icon-line-left 0.5s;
    }
    &.line-right {
      left: 13.5px;
      bottom: 13px;
      width: 30px;
      transform: rotate(-45deg);
      animation: icon-line-right 0.5s;
    }
  }
}

.check-title {
  margin-left: 45px;
}
</style>
