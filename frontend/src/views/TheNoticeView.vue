<template>
  <div class="the-notice-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      알림
    </header-basic>
    <!-- 알림 리스트 -->
    <section class="the-notice-section top-view-no-margin">
      <p @click="readNoticeAll">모두 읽기</p>
      <the-notice-list></the-notice-list>
    </section>
  </div>
</template>

<script setup lang="ts">
import TheNoticeList from "@/components/feeds/TheNoticeList.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import { onMounted } from "vue";
import { useStore } from "vuex";
const store = useStore();

const handleScroll = (event: any) => {
  const data = {
    event,
    action: "notice/getNoticeList",
    data: {},
  };
  store.dispatch("scroll/handleScroll", data);
};

// 초기 데이터, 무한 스크롤 연동
onMounted(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch("notice/getNoticeList");
});

// 모두 읽기
const readNoticeAll = () => {
  store.dispatch("notice/readNoticeAll");
  store.dispatch("notice/readNoticeList");
};
</script>

<style scoped lang="scss">
.the-notice-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  gap: 40px;
  @include accountLayOut;

  section {
    @include flex(column);
    gap: 16px;
    width: 100%;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }

    > p {
      @include font($fs-md, $fw-bold);
      margin-top: 16px;
      text-align: right;
      cursor: pointer;
    }
  }
}
</style>
