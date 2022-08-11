<template>
  <div class="the-notice-list-container">
    <article v-for="item in noticeList" :key="item.key">
      <the-notice-list-item :noticeData="item"></the-notice-list-item>
    </article>
  </div>
</template>

<script setup lang="ts">
import TheNoticeListItem from "@/components/feeds/TheNoticeListItem.vue";
import { useStore } from "vuex";
import { computed } from "vue";
const store = useStore();

const noticeList = computed(() => store.getters["notice/getNoticeList"]);
</script>

<style scoped lang="scss"></style>
