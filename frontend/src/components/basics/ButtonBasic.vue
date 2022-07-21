<template>
  <button
    :class="buttonColor"
    :style="{ width: buttonWidth, fontSize: buttonFontSize }"
  >
    <slot></slot>
  </button>
</template>

<script setup lang="ts">
import { computed } from "vue";

const props = defineProps<{
  buttonStyle: string[];
}>();

// 버튼 색상 선택 (scss로 세팅한 클래스명을 부여해서 설정한다. -> 총 21가지의 선택지)
const buttonColor = computed((): string => {
  return `${props.buttonStyle[0]}-button`;
});

// 버튼 너비 선택 (long or 특정 값 입력  )
const buttonWidth = computed((): string => {
  if (props.buttonStyle[1] === "long") {
    return "100% ";
  } else {
    return props.buttonStyle[1];
  }
});

// 버튼 폰트 사이즈 선택 (small or medium or large)
const buttonFontSize = computed(() => {
  switch (props.buttonStyle[2]) {
    case "small":
      return "15px";
    case "medium":
      return "17px";
    case "large":
      return "20px";
  }
});
</script>
<style scoped lang="scss"></style>
