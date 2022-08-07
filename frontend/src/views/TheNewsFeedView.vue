<template>
  <div class="the-news-feed-view">
    <!-- 헤더 -->
    <header-basic :success="false" @prevClicked="$router.go(-1)">
      뉴스피드
    </header-basic>
    <section>
      <the-news-feed-list></the-news-feed-list>
    </section>
  </div>
  <!-- navbar -->
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import TheNewsFeedList from "@/components/feeds/TheNewsFeedList.vue";
import { useStore } from "vuex";
import { onBeforeMount, onUnmounted, onMounted, computed } from "vue";
const store = useStore();

const handleScroll = (event: any) => {
  const data = {
    event,
    action: "newsFeed/getNewsFeedListData",
  };
  store.dispatch("scroll/handleScroll", data);
};

onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch("newsFeed/getNewsFeedListData");
});

// 저장된 스크롤 높이로 이동
onMounted(() => {
  const scrollY = computed(() => store.getters["newsFeed/getNewsFeedScrollY"]);
  setTimeout(() => {
    window.scrollTo(0, scrollY.value);
  }, 1000);
});

// 스크롤 높이 저장
onUnmounted(() => {
  store.dispatch("newsFeed/setNewsFeedScrollY", window.scrollY);
  // store.dispatch("newsFeed/removeNewsFeedListData");
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
      width: 60%;
    }

    @media #{$pc} {
      width: 50%;
    }
  }
}
</style>
