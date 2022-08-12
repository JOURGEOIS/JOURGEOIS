<template>
  <!-- 알림 리스트 -->
  <div class="the-notice-list-container">
    <article v-for="item in noticeList" :key="item.key">
      <the-notice-list-item :noticeData="item"></the-notice-list-item>
      <hr />
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

<style scoped lang="scss">
.the-notice-list-container {
  @include flex(column);

  article {
    hr {
      height: 1px;
      border: 0;
      background-color: $unchecked-color;
      margin: 0;
    }
  }
}
</style>
