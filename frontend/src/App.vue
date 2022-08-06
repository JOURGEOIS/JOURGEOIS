<template>
  <router-view />
  <the-create-post-modal v-if="createFeedModalStatus"></the-create-post-modal>
  <share-modal v-if="shareModalStatus"></share-modal>
</template>

<script setup lang="ts">
import TheCreatePostModal from "@/components/feeds/TheCreatePostModal.vue";
import ShareModal from "@/components/basics/ShareModal.vue";
import { useRoute, useRouter } from "vue-router";
import { computed } from "vue";
import { useStore } from "vuex";
const router = useRouter();
const route = useRoute();
const store = useStore();

const createFeedModalStatus = computed(
  () => store.getters["createFeed/getCreateFeedModalStatus"]
);

const shareModalStatus = computed(
  () => store.getters["share/getShareModalStatus"]
);
</script>

<style lang="scss">
#app {
  display: flex;
  justify-content: center;
  margin-left: $margin-mobile;
  margin-right: $margin-mobile;
}
</style>
