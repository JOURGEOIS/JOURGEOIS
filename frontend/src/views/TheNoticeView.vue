<template>
  <div class="the-notice-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      알림
    </header-basic>
    <section class="the-notice-section top-view-no-margin">
      <p>모두 읽기</p>
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

onMounted(() => {
  store.dispatch("notice/getNoticeList");
});
</script>

<style scoped lang="scss">
.the-notice-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  gap: 40px;
  @include accountLayOut;

  section {
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
