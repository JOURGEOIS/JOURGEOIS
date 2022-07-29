<!-- 
  부모에서 보내는 props=> :tag-style="[컬러, 폰트 크기, 둥근 정도]"
  --------------------------------------------------------------
  1. 컬러: 색깔과 버튼 스타일을 선택할 수 있다. (총 27가지의 선택지)
  색은 primary, red, emerald, blue, navy, main, sub, white, light-primary가 있다. 
  스타일은 기본, outline, blank가 있다. 
  ex1) 'primary'
  ex2) 'primary-outline'
  ex3) 'primary-blank'

  2. 폰트 크기: 직접 css 크기 단위로 입력 

  3. 둥근 정도: 직접 css 크기 단위로 입력
  small(15px), medium(17px), large(20px)
  --------------------------------------------------------------
  예시
  :tag-style="['sub-outline', '12px', '0.2em']"
  :tag-style="['primary-outline', '17px', '2px']"
-->

<template>
  <div
    class="filledTag"
    :class="tagColor"
    :style="{
      fontSize: tagStyle[1],
      borderRadius: tagStyle[2],
    }"
  >
    <slot></slot>
  </div>
</template>

<script setup lang="ts">
import { defineProps, computed } from "vue";

const props = defineProps<{ tagStyle: string[] }>();

const tagColor = computed((): string => {
  return `${props.tagStyle[0]}-bg`;
});
</script>
<style scoped lang="scss">
.filledTag {
  padding: 0.3em 0.6em;
}
</style>
