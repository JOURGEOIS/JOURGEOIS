<template>
  <div class="the-notice-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      알림
    </header-basic>
    <section class="the-notice-section top-view-no-margin">
      <the-notice-list></the-notice-list>
    </section>
  </div>
</template>

<script setup lang="ts">
import TheNoticeList from "@/components/feeds/TheNoticeList.vue";
</script>

<style scoped lang="scss">
.the-notice-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  section {
    width: 100%;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }
  }
}
</style>
