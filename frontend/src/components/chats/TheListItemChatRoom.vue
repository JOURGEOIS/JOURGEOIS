<template>
  <div class="the-list-item-chat-room">
    <div class="part-left">
      <round-image :round-image="userImage"></round-image>
      <div class="chat-room-info-text">
        <h1 class="chat-room-nickname">{{ data.opponent.nickname }}</h1>
        <p class="chat-room-message">{{ data.lastMessage.message }}</p>
      </div>
    </div>
    <!-- 안 읽음 표시 + 마지막 연락 시각 위치 -->
    <div class="part-right">
      <div class="time"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { ref, computed } from "vue";
import { useStore } from "vuex";
import { useRouter, useRoute } from "vue-router";
import { ChatRoom } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

const props = defineProps<{
  data: ChatRoom;
}>();

// 유저 이미지
const userImage = {
  image: props.data.opponent.img,
  width: "50px",
};
</script>

<style scoped lang="scss">
.the-list-item-chat-room {
  @include flex-xy(space-between, center);
  padding: 10px;
  gap: 10px;
  border-bottom: 1px solid $seperate-color;
  @include list-hover;

  .part-left {
    @include flex-xy(flex-start, center);
    @include text-overflow-ellipsis;
    gap: 10px;
    .chat-room-info-text {
      @include flex(column);
      @include text-overflow-ellipsis;
      gap: 3px;

      .chat-room-nickname {
        @include text-overflow-ellipsis;
        @include font(15px, $fw-medium);
      }
      .chat-room-message {
        @include text-overflow-ellipsis;
        @include font-size-sub(11px);
      }
    }
  }

  // .part-right {
  // }
}
</style>
