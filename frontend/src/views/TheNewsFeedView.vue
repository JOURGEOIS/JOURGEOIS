<template>
  <div class="the-news-feed-view">
    <!-- 헤더 -->
    <header-basic :success="false" @prevClicked="$router.go(-1)">
      뉴스피드
    </header-basic>
  </div>
  <!-- navbar -->
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from '@/components/basics/HeaderBasic.vue'
import NavBar from '@/components/basics/NavBar.vue'
</script>

<style scoped lang="scss">
.the-news-feed-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
}
</style>
