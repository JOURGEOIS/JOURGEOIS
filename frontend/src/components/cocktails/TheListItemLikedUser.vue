<template>
  <div class="the-list-item-liked-user">
    <round-image :round-image="userImage"></round-image>
    <div class="user-info-text">
      <h1 class="user-nickname">{{ likedUser.nickname }}</h1>
      <p class="user-introduce">{{ likedUser.introduce }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { User } from "../../interface";

const props = defineProps<{
  likedUser: User;
}>();

// 유저 이미지
const userImage = {
  image: props.likedUser.profileImg,
  width: "50px",
};
</script>

<style scoped lang="scss">
.the-list-item-liked-user {
  @include flex-xy(flex-start, center);
  padding: 10px;
  gap: 10px;
  border-bottom: 1px solid $seperate-color;
  @include list-hover;

  .user-info-text {
    @include flex(column);
    gap: 3px;

    .user-nickname {
      @include font(15px, $fw-medium);
    }
    .user-introduce {
      @include font-size-sub(11px);
    }
  }
}
</style>
