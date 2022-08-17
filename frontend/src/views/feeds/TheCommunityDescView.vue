<template>
  <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
    {{ nickname }}
  </header-basic>
  <div class="community-desc-view top-view-no-margin">
    <div class="the-community-desc-container">
      <!-- 포스트 내용 불러오기 -->
      <the-community-desc-body></the-community-desc-body>
      <!-- 댓글 좋아요 공유 부분 -->
      <like-comment-share @clickLike="clickLike" :data="{ isLiked: isLiked }">
        <template #like>{{ likeCount }}</template>
        <template #comment>{{ commentCount }}</template>
      </like-comment-share>
      <section class="the-community-desc-comment">
        <!-- 댓글 불러오기 -->
        <comment-form :page-id="feedId"></comment-form>
        <comment-list :page-id="feedId"></comment-list>
      </section>
    </div>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import TheCommunityDescBody from "@/components/feeds/TheCommunityDescBody.vue";
import LikeCommentShare from "@/components/basics/LikeCommentShare.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import CommentForm from "@/components/basics/CommentForm.vue";
import CommentList from "@/components/basics/CommentList.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { reactive, computed, onMounted, onUnmounted } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import { CustomCocktail } from "../../interface";
const route = useRoute();
const store = useStore();

// navbar 색깔 부여
store.dispatch("navbar/setNavIconStatus", 0);

const feedId = Number(route.params.feedId);
const nickname = computed(() => feedDescInfo?.value?.followerDTO?.nickname);

const getCommunityDetail = () => {
  store.dispatch("feedDescInfo/getCommunityDetail", {
    feedId,
  });
};

onMounted(() => {
  getCommunityDetail();
});

onUnmounted(() => {
  store.dispatch("feedDescInfo/removeCommunityDetail");
});

const feedDescInfo = computed(() => {
  return store.getters["feedDescInfo/getCommunityDetail"];
});

// 좋아요 개수
const likeCount = computed(() => feedDescInfo?.value?.customCocktail?.like);

// 좋아요 상태 확인
const isLiked = computed(() => feedDescInfo?.value?.customCocktail?.ilike);

// 좋아요를 누른 경우
const clickLike = () => {
  const params = {
    postId: feedId,
    func: "feedDescInfo/getCommunityDetail",
    data: { feedId },
  };
  store.dispatch("post/toggleLike", params);
};

// 댓글 개수
const commentCount = computed(() => store.getters["comment/getCommentCount"]);
</script>

<style scoped lang="scss">
.community-desc-view {
  @include flex(column);
  justify-content: center;
  align-items: center;
  position: relative;
  @include accountLayOut;

  .the-community-desc-container {
    width: 100%;
    margin-top: 8px;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 60%;
    }
  }

  .the-community-desc-comment {
    @include flex(column);
    width: 100%;
  }
}
</style>
