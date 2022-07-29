<!-- 
  부모에서 보내는 props=> :button-style="[컬러, 길이, 폰트 크기]"
  --------------------------------------------------------------
  1. 컬러: 색깔과 버튼 스타일을 선택할 수 있다. (총 30가지의 선택지)
  색은 primary, red, emerald, blue, navy, main, sub, white, light-primary가 있다. 
  스타일은 기본, outline, blank가 있다. 
  ex1) 'primary'
  ex2) 'primary-outline'
  ex3) 'primary-blank'

  2. 길이: 버튼의 길이를 조정할 수 있다. 
  long혹은 직접 너비를 입력할수 있다.
  long을 선택할 경우 마진을 제외하고 꽉찬다. 단, tablet부터는 max-width가 정해져있다. 
  ex1) 'long'
  ex2) '16px'

  3. 폰트 크기: 버튼 내 폰트 크기를 설정할 수 있다. 
  small(15px), medium(17px), large(20px)
  --------------------------------------------------------------
  예시
  :button-style="['sub-outline', 'long', 'large']"
  :button-style="['primary-outline', '15px', 'small']"
-->

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

// 버튼 색상 선택 (scss로 세팅한 클래스명을 부여해서 설정한다. -> 총 30가지의 선택지)
const buttonColor = computed((): string => {
  return `${props.buttonStyle[0]}-bg`;
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
