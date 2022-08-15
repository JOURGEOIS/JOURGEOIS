<template>
  <div class="the-item-container">
    <the-list-item-chat-room
      v-for="item in chatRoomList"
      :key="`chatRoom-${item.chatRoomId}`"
      :data="item"
      @click="clickChatRoom(item)"
    ></the-list-item-chat-room>
  </div>
</template>

<script setup lang="ts">
import TheListItemChatRoom from "@/components/chats/TheListItemChatRoom.vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { computed } from "vue";
import { ChatRoom } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

const clickChatRoom = (item: ChatRoom) => {
  console.log(item);
};

const chatRoomList = computed(() => store.getters["chatRoom/getChatRoomList"]);
</script>

<style scoped lang="scss"></style>
