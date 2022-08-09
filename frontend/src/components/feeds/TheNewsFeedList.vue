<template>
  <div class="news-feed-list-container">
    <article v-for="item in newsFeedList" :key="item.postId">
      <the-news-feed-item :news-feed-data="item"></the-news-feed-item>
    </article>
  </div>
</template>

<script setup lang="ts">
import TheNewsFeedItem from "@/components/feeds/TheNewsFeedItem.vue";
import { useStore } from "vuex";
import { computed } from "vue";
const store = useStore();

const newsFeedList = computed(
  () => store.getters["newsFeed/getNewsFeedListData"]
);
</script>

<style scoped lang="scss">
.news-feed-list-container {
  @include flex(column);
  gap: 40px;
  width: calc(100% + 32px);
  margin-left: -16px;
  background-color: $white150;
}
</style>
