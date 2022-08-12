<template>
  <div class="the-news-feed-view">
    <!-- 헤더 -->
    <header-notice :success="false" @prevClicked="$router.go(-1)">
      뉴스피드
    </header-notice>
    <section class="top-view-no-margin">
      <the-news-feed-list></the-news-feed-list>
    </section>
  </div>
  <!-- navbar -->
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderNotice from "@/components/basics/HeaderNotice.vue";
import NavBar from "@/components/basics/NavBar.vue";
import TheNewsFeedList from "@/components/feeds/TheNewsFeedList.vue";
import { useStore } from "vuex";
import { onBeforeMount, onBeforeUnmount, onMounted, computed } from "vue";
const store = useStore();

const handleScroll = (event: any) => {
  const data = {
    event,
    action: "newsFeed/getNewsFeedListData",
  };
  store.dispatch("scroll/handleScroll", data);
};

// db 받아오기
onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch("newsFeed/getNewsFeedListData");
});

// 저장된 스크롤 높이로 이동
onMounted(() => {
  const scrollY = computed(() => store.getters["newsFeed/getNewsFeedScrollY"]);
  setTimeout(() => {
    window.scrollTo({ left: 0, top: scrollY.value });
  }, 0);

  // 알림 상태 확인하기
  store.dispatch("notice/checkNotice");
});

// 스크롤 높이 저장
onBeforeUnmount(() => {
  // 스크롤 위치 기억하기
  store.dispatch("newsFeed/setNewsFeedScrollY", window.scrollY);
});
</script>

<style scoped lang="scss">
.the-news-feed-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  section {
    width: 100%;
    margin-top: 8px;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 60%;
    }
  }
}
</style>
