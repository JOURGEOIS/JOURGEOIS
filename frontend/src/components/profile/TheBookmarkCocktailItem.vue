<template>
  <!-- 카드 -->
  <div class="cocktail-bookmark-item-card">
    <!-- 이미지 -->
    <div
      class="cocktail-bookmark-item-image"
      :style="{ backgroundImage: originalCocktailImg }"
    ></div>

    <!-- 제목 -->
    <p>{{ nameKR }}</p>

    <!-- 상세보기 -->
    <button
      class="cocktail-bookmark-item-btn detail"
      @click="clickDetail"
    >
      상세보기
    </button>
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
  post: userBookmarkData;
}>();

const cocktailId = props.post.cocktailId

const originalCocktailImg = props.post.img
const nameKR = props.post.nameKR

const clickDetail = () => {
  router.push({
    name: "TheCocktailDescView",
    params: {
      feedId: cocktailId,
    },
  });
};

</script>

<style scoped lang="scss">
.cocktail-bookmark-item-card {
  @include flex-xy(center, flex-start);
  flex-wrap: wrap;
  width: 100%;
  height: 100%;
  border-radius: 12px;
  @include feed;
  padding: 0px;
  padding-bottom: 10px;
  cursor: pointer;

  .cocktail-bookmark-item-image {
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

  .cocktail-bookmark-item-btn {
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