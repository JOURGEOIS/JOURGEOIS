<template>
  <section class="the-chat-room-form">
    <form @submit.prevent="submitTheChatRoomForm">
      <input
        type="text"
        maxlength="40"
        placeholder="메세지를 입력해주세요"
        v-model="chatInputValue"
      />
      <!-- emoji 버튼 -->
      <div class="the-chat-room-form-emoji" @click="changeEmojiStatus">
        <span class="material-icons-outlined"> add_reaction </span>
      </div>

      <!-- submit 버튼 -->
      <button class="the-chat-room-form-submit">
        <span class="material-icons"> send </span>
      </button>
    </form>
    <div class="the-chat-room-emoji" v-if="chatRoomStatus">
      <div
        v-for="(emoji, index) in emojiList"
        :key="`emoji-${index}`"
        @click="clickEmoji(emoji)"
      >
        {{ emoji }}
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import { useRoute } from "vue-router";
import store from "../../store";
const route = useRoute();

// 이모지
const emojiList = reactive([
  "😀",
  "😁",
  "😂",
  "🤣",
  "😃",
  "😄",
  "😅",
  "😆",
  "😉",
  "😊",
  "😋",
  "😎",
  "😍",
  "😘",
  "🥰",
  "😗",
  "😙",
  "😚",
  "🙂",
  "🤗",
  "🤩",
  "🤔",
  "🤨",
  "😐",
  "😑",
  "😶",
  "🙄",
  "😏",
  "😣",
  "😥",
  "😮",
  "🤐",
  "😯",
  "😪",
  "😫",
]);

// 이모지 form 상태
const chatRoomStatus = ref(false);

// 이모지 클릭
const clickEmoji = (item: string) => {
  chatInputValue.value = chatInputValue.value + item;
};

// 이모지 on, off
const changeEmojiStatus = () => {
  chatRoomStatus.value = !chatRoomStatus.value;
};

// 채팅 input
const chatInputValue = ref("");

// 채팅 submit
const submitTheChatRoomForm = () => {
  const data = {
    receiver: Number(route.params.userId),
    message: chatInputValue.value,
  };
  store.dispatch("chatRoom/sendNewChat", data);
  chatInputValue.value = "";
};
</script>

<style scoped lang="scss">
.the-chat-room-form {
  @include flex(column);
  gap: 16px;
  position: fixed;
  bottom: 80px;
  width: calc(100% - 36px);
  max-width: 600px;

  form {
    @include flex-xy(flex-start, center);
    gap: 12px;
    height: 2em;
    padding: 4px 8px;
    border: 1px solid $unchecked-color;
    border-radius: 1em;
    background-color: $white;

    input {
      width: 80%;
      height: 2em;
      border: 0;
      @include font($fs-md, $fw-medium);
      flex-grow: 1;

      &:focus {
        outline: none;
      }

      &::placeholder {
        color: $sub-color;
      }
    }
    .the-chat-room-form-emoji {
      @include flex-center;
      width: 32px;
      height: 32px;
      color: $white400;
      padding: 0;
      margin-top: 5px;
      cursor: pointer;
    }

    .the-chat-room-form-submit {
      @include flex-center;
      width: 32px;
      height: 32px;
      background-color: $white;
      padding: 0;
      color: $primary600;
      margin-top: 5px;
    }
  }

  .the-chat-room-emoji {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(14%, auto));
    justify-items: center;
    justify-content: center;
    align-items: center;
    align-content: center;
    row-gap: 16px;
    width: 100%;

    > div {
      font-size: $fs-lg;
    }
  }
}
</style>
