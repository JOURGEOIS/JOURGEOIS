<template>
  <div class="the-chat-room-chat-list">
    <article
      v-for="log in currentChatLogs"
      :key="`${log.timestamp.seconds}-${log.timestamp.nanoseconds}`"
    >
      <the-chat-room-chat-list-item
        :chat-log="log"
      ></the-chat-room-chat-list-item>
    </article>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";
import TheChatRoomChatListItem from "@/components/chats/TheChatRoomChatListItem.vue";
const store = useStore();

const currentChatLogs = computed(() => store.getters["chatRoom/getChatLogs"]);
</script>

<style scoped lang="scss">
.the-chat-room-chat-list {
  display: flex;
  flex-direction: column-reverse;
  gap: 16px;
  width: 100%;
  height: 100%;
  overflow-y: scroll;

  scrollbar-width: none;

  &::-webkit-scrollbar {
    display: none;
  }

  article {
    width: 100%;
  }
}
</style>
