<template>
  <div class="chat-room-list-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      채팅
    </header-basic>

    <section class="tab-section top-view">
      <div class="tab-chat-room" :class="`index-${index}`">
        <p @click="clickCocktailTab">채팅</p>
      </div>
      <div class="tab-following" :class="`index-${index}`">
        <p @click="clickFollowingTab">친구</p>
      </div>
    </section>
    <!-- 탭에 따라 변경되는 동적 컴포넌트 -->
    <section class="dynamic-component-section">
      <keep-alive>
        <component :is="currentComponent"></component>
      </keep-alive>
    </section>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import {
  computed,
  onBeforeMount,
  onUnmounted,
  defineAsyncComponent,
} from "vue";
import { ChatRoom } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

// navbar 색깔 부여
store.dispatch("navbar/setNavIconStatus", 0);

const clickChatRoom = (item: ChatRoom) => {
  console.log(item);
};

const chatRoomList = computed(() => store.getters["chatRoom/getChatRoomList"]);

// 현재 탭
const currentTab = computed(() => store.getters["chatRoom/getCurrentTab"]);

// View 입장 후 Mount 전에
onBeforeMount(async () => {
  // 기존 chatList state 초기화
  store.dispatch("chatRoom/setCurrentTab", 0);
  store.dispatch("chatRoom/setChatRoomList");
});

// 탭 클릭
const clickCocktailTab = () => store.dispatch("chatRoom/setCurrentTab", 0);
const clickFollowingTab = () => store.dispatch("chatRoom/setCurrentTab", 1);

// 동적 컴포넌트 (탭)
const componentArray = [
  defineAsyncComponent(
    () => import("@/components/chats/TheChatRoomContainer.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/chats/TheFollowingContainer.vue")
  ),
];

const index = computed(() => store.getters["chatRoom/getCurrentTab"]);
const currentComponent = computed(() => {
  return componentArray[index.value];
});
</script>

<style scoped lang="scss">
.chat-room-list-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
  margin-bottom: 48px;
  .tab-section {
    @include flex-xy(space-between, center);
    width: 100%;

    div {
      @include flex-center;
      position: relative;
      width: 50%;
      border-bottom: 1px solid $white400;

      p {
        color: $white400;
        @include font($fs-main, $fw-medium);
        padding-bottom: 10px;
        cursor: pointer;
      }
    }

    // 탭 활성화 색상 변경
    .tab-chat-room.index-0 {
      border-bottom: 2px solid $primary-color;
      p {
        color: $main-color;
      }
    }

    .tab-following.index-1 {
      border-bottom: 2px solid $primary-color;
      p {
        color: $main-color;
      }
    }
  }
}

.title1 {
  @include font(17px, $fw-medium);
  padding-bottom: 10px;
}
</style>
