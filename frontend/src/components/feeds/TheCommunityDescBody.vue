<template>
  <!-- 작성자 정보 섹션 -->
  <post-user-section
    :userInfo="feedDescInfo.followerDTO"
    :date="feedDescInfo.customCocktail.createTime"
  ></post-user-section>

  <section>
    <!-- 이미지 섹션 -->
    <article
      class="image"
      :style="{ backgroundImage: `url(${imgLink})` }"
    ></article>
  </section>
</template>

<script setup lang="ts">
import PostUserSection from '@/components/basics/PostUserSection.vue'
import { CustomCocktail } from '../../interface'
import { computed } from 'vue'
import { useStore } from 'vuex'
const store = useStore()
const feedDescInfo = computed(() => {
  return store.getters['feedDescInfo/getCommunityDetail']
})

const postId = computed(() => feedDescInfo?.value?.customCocktail?.postId)
const imgLink = computed(() => feedDescInfo?.value?.customCocktail?.imgLink)
const description = computed(
  () => feedDescInfo?.value?.customCocktail?.description,
)

const isFollowed = computed(() => feedDescInfo?.value?.followerDTO?.isFollowed)
const isMine = computed(() => {
  return isFollowed.value === -1
})
</script>

<style scoped lang="scss"></style>
