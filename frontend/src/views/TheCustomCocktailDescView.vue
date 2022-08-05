<template>
  <div class="the-custom-cocktail-desc-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      커스텀 칵테일
    </header-basic>
    <!-- 제목, 사진, 제조법, 설명 등 커스텀 칵테일 내용 -->
    <the-custom-cocktail-desc-body
      :data="customCocktailInfo"
    ></the-custom-cocktail-desc-body>
    <!-- 댓글 좋아요 공유 부분 -->
    <like-comment-share></like-comment-share>
    <!-- 댓글 부분 -->
    <comment-form></comment-form>
  </div>

  <!-- navbar -->
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheCustomCocktailDescBody from "@/components/cocktails/TheCustomCocktailDescBody.vue";
import TheLikeCommentShare from "@/components/basics/LikeCommentShare.vue";
import CommentForm from "@/components/basics/CommentForm.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { reactive, computed, onBeforeMount } from "vue";
import { useStore } from "vuex";
import { CustomCocktail } from "../interface";

const customCocktailInfo: CustomCocktail = {
  customCocktail: {
    postId: 1,
    imgLink:
      "https://cphoto.asiae.co.kr/listimglink/1/2020051809523742212_1589763157.jpg",
    description:
      "\“낮져밤이 마가리타\”빨간색과 파란색이 음양의 조화를 이룹니다. 완전 이쁘죠. 레몬까지 더해서 빨노파 붉은색 푸른색 그 사이 한 조각 레몬이 샤샤샤 아주 조화롭습니다. 뜨거운 낮과 차가운 밤을 표현해보았습니다.",
    createTime: [2022, 8, 3, 22, 15, 12, 83204000],
    lastUpdateTime: [2022, 8, 4, 9, 15, 12, 83204000],
    isUpdated: 1,
    like: 382,
    title: "마!가린",
    baseCocktail: 17216,
    baseCocktailName: "토미스 마가리타",
    ingredients: ["데킬라", "마가린", "치즈", "레몬"],
    recipe:
      "부어요. <> 막 부어요. <> 몰라 그냥 부어요. <> 냅다 부어요 그냥. <> 섞어요 엄청 섞어요",
  },
  followerDTO: {
    uid: 2,
    nickname: "우영우김밥하나요",
    profileImg:
      "https://t1.daumcdn.net/news/202206/14/SPORTSSEOUL/20220614092528280afnr.jpg",
    isFollowed: 1,
  },
};
</script>

<style scoped lang="scss">
.the-custom-cocktail-desc-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
}
</style>
