;
<!-- 
  부모에서 보내는 props => round-image
  -----------------------------------------------------
  round-image: 프로필 이미지에 대한 정보를 담은 객체
  - image (string): 이미지 url
  - width (string): 원형 프로필 이미지 크기
-->

<template>
  <div
    class="round-image"
    :style="{
      backgroundImage: imageUrl,
      width: imageWidth,
      height: imageWidth,
    }"
  ></div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";

export interface roundImage {
  image: string;
  width?: string;
}

const props = defineProps<{
  roundImage: roundImage;
}>();

const imageUrl = computed(() => {
  return `url(${props.roundImage.image})`;
});

const imageWidth = ref(props.roundImage.width);
</script>

<style scoped lang="scss">
.round-image {
  width: 87px;
  height: 87px;
  border: 0;
  border-radius: 50%;
  background-repeat: no-repeat;
  background-color: $white200;
  background-position: center;
  background-size: cover;
}
</style>
