<template>
  <div class="the-list-item-chat-room">
    <div class="part-left">
      <round-image
        :round-image="userImage"
        class="chat-room-profile-image"
      ></round-image>
      <div class="chat-room-info-text">
        <h1 class="chat-room-nickname">{{ data.opponent.nickname }}</h1>
        <p class="chat-room-message">{{ data.lastMessage.message }}</p>
      </div>
      <div v-if="data.hasNewMessage" class="chat-room-new"></div>
    </div>
    <!-- 안 읽음 표시 + 마지막 연락 시각 위치 -->
    <div class="part-right">
      <div class="chat-room-time">{{ time }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { calcDateDelta2 } from "../../functions/date";
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

// 시간
const time = computed(() => {
  const t = new Date(
    props.data.lastMessage.timestamp.seconds * 1000
  ).toString();
  return calcDateDelta2(t);
  return;
});
</script>

<style scoped lang="scss">
.the-list-item-chat-room {
  @include flex-xy(space-between, center);
  padding: 10px;
  gap: 10px;
  width: 100%;
  border-bottom: 1px solid $seperate-color;
  @include list-hover;
  position: relative;

  .part-left {
    @include flex-xy(flex-start, center);
    gap: 10px;
    @include text-overflow-ellipsis;

    .chat-room-profile-image {
      flex-shrink: 0;
    }
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

  .chat-room-new {
    position: absolute;
    width: 10px;
    height: 10px;
    background-color: $danger-color;
    border-radius: 50%;
    top: 10px;
    left: 0px;
  }

  .part-right {
    align-self: flex-start;
    text-align: right;

    flex-grow: 1;
    flex-shrink: 0;
    .chat-room-time {
      width: 100%;
      @include font($fs-sm, $fw-regular);
    }
  }
}
</style>
