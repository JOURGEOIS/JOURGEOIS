<template>
  <div class="cocktail-bookmark-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      북마크
    </header-basic>
    <section class="cocktail-bookmark-section">
      <div class="cocktail-bookmark-none">
        <p>해당 칵테일을 북마크한 유저가 없습니다</p>
        <p class="emoji">😥</p>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import { useStore } from "vuex";
const store = useStore();
</script>

<style scoped lang="scss">
.cocktail-bookmark-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
  margin-bottom: 48px;

  section {
    @include flex(column);
    justify-content: center;
    align-items: center;
    gap: 24px;
    width: 100%;
    margin-top: 24px;

    .cocktail-bookmark-none {
      width: 100%;
      margin-top: 120px;
      padding: 64px 16px;
      border-radius: 16px;
      background-color: $white200;
      @include font($fs-main, $fw-bold);
      text-align: center;

      .emoji {
        font-size: $fs-xl;
      }

      @media #{$tablet} {
        @include font($fs-lg, $fw-bold);
        width: 450px;
      }
    }
  }
}
</style>
