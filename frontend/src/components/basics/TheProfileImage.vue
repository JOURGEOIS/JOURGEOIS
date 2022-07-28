<!-- 
  부모에서 보내는 props => profile-image
  -----------------------------------------------------
  profile-image: 프로필 이미지에 대한 정보를 담은 객체
  - image (string): 이미지 url
  - width (string): 원형 프로필 이미지 크기
-->

<template>
  <div
    class="profile-image"
    :style="{
      backgroundImage: imageUrl,
      width: imageWidth,
      height: imageWidth,
    }"
  ></div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";

export interface profileImage {
  image: string;
  width: string;
}

const props = defineProps<{
  profileImage: profileImage;
}>();

const imageUrl = computed(() => {
  return `url(${props.profileImage.image})`;
});

const imageWidth = ref(props.profileImage.width);
</script>

<style scoped lang="scss">
.profile-image {
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
