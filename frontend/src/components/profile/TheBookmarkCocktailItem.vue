<template>
  <!-- 카드 -->
  <div class="bookmark-item-card" @click="clickDetail">
    <!-- 이미지 -->
    <div
      class="bookmark-item-image"
      :style="{ backgroundImage: `url(${originalCocktailImg})` }"
    ></div>

    <!-- 제목 -->
    <p>{{ nameKR }}</p>

    <!-- 상세보기 -->
    <button class="bookmark-item-btn detail">상세보기</button>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import RoundImage from "@/components/basics/RoundImage.vue";
import { calcDateDelta, calcDateDelta2 } from "../../functions/date";
import { userBookmarkData } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

const props = defineProps<{
  bookmark: userBookmarkData;
}>();

const cocktailId = props.bookmark.cocktailId;

const originalCocktailImg = props.bookmark.img;
const nameKR = props.bookmark.nameKR;

const clickDetail = () => {
  router.push({
    name: "TheCocktailDescView",
    params: {
      cocktailId,
    },
  });
};
</script>

<style scoped lang="scss">
.bookmark-item-card {
  @include flex-xy(center, flex-start);
  flex-wrap: wrap;
  width: 100%;
  height: 100%;
  border-radius: 12px;
  @include feed;
  padding: 0px;
  padding-bottom: 10px;
  cursor: pointer;

  .bookmark-item-image {
    width: 100%;
    aspect-ratio: 1/1;
    border-radius: 12px 12px 0px 0px;
    background : {
      size: cover;
      position: center center;
    }
  }

  p {
    overflow: hidden;
    text-overflow: ellipsis;
    width: 90%;
    white-space: nowrap;
    text-align: center;
    @include font($fs-main, $fw-regular);
  }

  .bookmark-item-btn {
    width: 90%;
    padding: 0.4em;
    border-radius: 6px;
    text-align: center;
    @include font($fs-sm, $fw-medium);
  }

  .detail {
    background-color: $primary400;
    color: $white;
  }
}
</style>
