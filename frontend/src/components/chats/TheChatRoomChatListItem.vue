<template>
  <!-- 상대방 -->
  <div v-if="!isMine" class="you the-chat-room-chat-item">
    <round-image
      :round-image="{ image: opponent.img }"
      class="the-chat-room-chat-item-image"
    ></round-image>
    <div class="the-chat-room-chat-item-content">
      <p class="the-chat-room-chat-item-name">{{ opponent.nickname }}</p>
      <div class="the-chat-room-chat-item-message">
        <p>{{ chatLog.message }}</p>
      </div>
      <p class="the-chat-room-chat-item-time">{{ time }}</p>
    </div>
  </div>

  <!-- 나 -->
  <div v-else class="me the-chat-room-chat-item">
    <div class="the-chat-room-chat-item-message">
      <p>{{ chatLog.message }}</p>
    </div>
    <p class="the-chat-room-chat-item-time">{{ time }}</p>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { Chat } from "../../interface";
import { calcDateDelta2 } from "../../functions/date";
import { useStore } from "vuex";
import RoundImage from "@/components/basics/RoundImage.vue";
const store = useStore();

const props = defineProps<{
  chatLog: Chat;
}>();

// 보낸 시간
const time = computed(() => {
  const t = new Date(props.chatLog.timestamp.seconds * 1000).toString();
  return calcDateDelta2(t);
  return;
});

// 상대방 정보
const opponent = computed(() => store.getters["chatRoom/getCurrentChatRoom"])
  .value.opponent;

// 내 userId
const myUserId = computed(
  () => store.getters["personalInfo/getUserInfoUserId"]
);

// 나인지 아닌지 확인
const isMine = computed(() => {
  return props.chatLog.sender === Number(myUserId.value);
});
</script>

<style scoped lang="scss">
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translate3d(0, 50%, 0);
  }
  to {
    opacity: 1;
    transform: translateZ(0);
  }
}
.the-chat-room-chat-item {
  animation: fadeIn 0.5s;
  .the-chat-room-chat-item-image {
    width: 40px;
    height: 40px;
    flex-shrink: 0;
  }

  .the-chat-room-chat-item-message {
    padding: 10px 16px;
    @include font($fs-md, $fw-regular);
  }
  .the-chat-room-chat-item-time {
    @include font($fs-sm, $fw-medium);
    color: $gray100;
  }
}

.me {
  @include flex-xy(center, flex-end);
  flex-direction: column;

  .the-chat-room-chat-item-message {
    border-radius: 10px 10px 0px 10px;
    background: $red300p;
    max-width: 70%;
  }
}
.you {
  @include flex-xy(flex-start, flex-start);
  gap: 8px;
  .the-chat-room-chat-item-content {
    @include flex-xy(center, flex-start);
    gap: 4px;
    flex-direction: column;
    max-width: 70%;

    .the-chat-room-chat-item-name {
      @include font($fs-sm, $fw-medium);
    }
    .the-chat-room-chat-item-message {
      border-radius: 10px 10px 10px 0px;
      background-color: $white200;
    }
  }
}
</style>
