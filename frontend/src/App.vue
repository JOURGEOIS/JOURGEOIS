<template>
  <router-view />
  <failure-pop-up-app v-if="failModalAppStatus">{{
    failMessage
  }}</failure-pop-up-app>
  <the-create-post-modal v-if="createFeedModalStatus"></the-create-post-modal>
  <share-modal v-if="shareModalStatus"></share-modal>
  <top-button></top-button>
</template>

<script setup lang="ts">
import FailurePopUpApp from "@/components/modals/FailurePopUpApp.vue";
import TheCreatePostModal from "@/components/feeds/TheCreatePostModal.vue";
import ShareModal from "@/components/basics/ShareModal.vue";
import TopButton from "@/components/basics/TopButton.vue";
import { useRoute, useRouter } from "vue-router";
import { computed, onMounted } from "vue";
import { useStore } from "vuex";
const router = useRouter();
const route = useRoute();
const store = useStore();

const failModalAppStatus = computed(
  () => store.getters["modal/getFailModalAppStatus"]
);
const failMessage = "문제가 발생했습니다. 다시 시도해주세요.";

const createFeedModalStatus = computed(
  () => store.getters["createFeed/getCreateFeedModalStatus"]
);

const shareModalStatus = computed(
  () => store.getters["share/getShareModalStatus"]
);

// deviceType 확인
onMounted(() => {
  store.dispatch("navbar/setDeviceType");
});
</script>

<style lang="scss">
#app {
  display: flex;
  justify-content: center;
  margin-left: $margin-mobile;
  margin-right: $margin-mobile;
}

.top-view {
  margin-top: 120px !important;

  @media #{$tablet} {
    margin-top: 152px !important;
  }
}
</style>
