<template>
  <div class="following-container">
    <the-list-item-following
      v-for="(followee, idx) in followees"
      :key="`followee-${idx}`"
      :followee="followee"
      @click="clickUser(followee)"
    ></the-list-item-following>
  </div>
</template>

<script setup lang="ts">
import TheListItemFollowing from "@/components/chats/TheListItemFollowing.vue";
import { computed, onBeforeMount, onUnmounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { User } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

const followees = computed(() => store.getters["follow/getFolloweeUsers"]);
const uid = computed(() => store.getters["personalInfo/getUserInfoUserId"]);

// 계정 클릭 시
const clickUser = (followee: User) => {
  router.push({ name: "TheChatRoomView", params: { userId: followee.uid } });
};

const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "follow/setFolloweeList",
    data: { userId: uid.value },
  };
  store.dispatch("scroll/handleScroll", data);
};

const setFolloweeList = (data: object) => {
  store.dispatch("follow/setFolloweeList", data);
};

onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  setFolloweeList({ userId: uid.value });
  setTimeout(() => {
    setFolloweeList({ userId: uid.value });
  }, 100);
});

// 리셋
onUnmounted(() => {
  store.dispatch("follow/resetFollowUserData");
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped>
.following-container {
  width: 100%;
}
</style>
