<template>
  <!-- 상대방 -->
  <div v-if="!isMine" class="you the-chat-room-chat-item">
    <round-image
      :round-image="{ image: opponent.img }"
      @click="clickProfileImage"
      class="the-chat-room-chat-item-image"
    ></round-image>
    <div class="the-chat-room-chat-item-content">
      <p class="the-chat-room-chat-item-name" @click="clickProfileImage">
        {{ opponent.nickname }}
      </p>
      <div class="the-chat-room-chat-item-part">
        <div class="the-chat-room-chat-item-message">
          <p>{{ chatLog.message }}</p>
        </div>
        <p class="the-chat-room-chat-item-time">{{ time }}</p>
      </div>
    </div>
  </div>

  <!-- 나 -->
  <div v-else class="me the-chat-room-chat-item">
    <p class="the-chat-room-chat-item-time">{{ time }}</p>
    <div class="the-chat-room-chat-item-message">
      <p>{{ chatLog.message }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { Chat } from "../../interface";
import { calcDateDelta2 } from "../../functions/date";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";
import RoundImage from "@/components/basics/RoundImage.vue";
const store = useStore();
const router = useRouter();
const route = useRoute();

const props = defineProps<{
  chatLog: Chat;
}>();

// 보낸 시간
const time = computed(() => {
  const t = new Date(props.chatLog.timestamp.seconds * 1000);
  // const now = new Date().getTime();
  // if (now - props.chatLog.timestamp.seconds * 1000 < 1000) {
  // return t;
  // }
  // return calcDateDelta2(t.toString());
  const t2 = t.toLocaleTimeString();
  // const mm = new Date(t).getMinutes();
  // const hh = new Date(t).getHours();
  // return `${hh}:${mm}`;
  return t2.slice(0, t2.length - 3);
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

// 프로필 이미지 클릭 시, 프로필 페이지로 이동
const clickProfileImage = () => {
  router.push({
    name: "TheUserProfileView",
    params: {
      userId: route.params.userId,
    },
  });
};
</script>

<style scoped lang="scss">
// @keyframes fadeIn {
//   from {
//     opacity: 0;
//     transform: translate3d(0, 50%, 0);
//   }
//   to {
//     opacity: 1;
//     transform: translateZ(0);
//   }
// }
.the-chat-room-chat-item {
  animation: fadeIn 0.5s;
  .the-chat-room-chat-item-image {
    width: 40px;
    height: 40px;
    flex-shrink: 0;
    cursor: pointer;
  }
  .the-chat-room-chat-item-message {
    padding: 10px 16px;
    @include font($fs-md, $fw-regular);
  }
  .the-chat-room-chat-item-time {
    @include font($fs-sm, $fw-regular);
    color: $gray100;
  }
}

.me {
  @include flex-xy(flex-end, flex-end);
  gap: 8px;

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

    .the-chat-room-chat-item-name {
      @include font($fs-sm, $fw-medium);
      cursor: pointer;
    }
    .the-chat-room-chat-item-part {
      @include flex-xy(flex-start, flex-end);
      gap: 8px;
      .the-chat-room-chat-item-message {
        border-radius: 10px 10px 10px 0px;
        background-color: $white200;
        max-width: 70%;
      }
    }
  }
}
</style>
