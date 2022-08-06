<template>
  <div class="community-desc-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      도시라솔파미레몬
    </header-basic>
    <!-- 포스트 내용 불러오기 -->
    <the-community-desc-body></the-community-desc-body>
    <!-- 좋아요/댓글/공유기능추가 -->
    <!-- <like-comment-share>
        <template #like>888</template>
        <template #comment>777</template>
      </like-comment-share> -->
    <section class="the-community-desc-comment">
      <!-- 댓글 불러오기 -->
      <comment-form :page-id="communityPostId"></comment-form>
      <comment-list :page-id="communityPostId"></comment-list>
    </section>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import TheCommunityDescBody from '@/components/feeds/TheCommunityDescBody.vue'
import CommentForm from '@/components/basics/CommentForm.vue'
import CommentList from '@/components/basics/CommentList.vue'
import HeaderBasic from '@/components/basics/HeaderBasic.vue'
import NavBar from '@/components/basics/NavBar.vue'
import { reactive, computed, onBeforeMount } from 'vue'
import { useRoute } from 'vue-router'
import { useStore } from 'vuex'
import { CustomCocktail } from '../interface'
const route = useRoute()

// 유저 정보 불러오기
const store = useStore()

// 게시글 id
const communityPostId = Number(route.params.feedId)

const feedDescInfo = computed(() => {
  return store.getters['feedInfo/getCommunityDetail']
})
</script>

<style scoped lang="scss">
.community-desc-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  position: relative;
  @include accountLayOut;
  .the-community-desc-comment {
    @include flex(column);
    gap: 40px;
    width: 100%;
  }
}
</style>
