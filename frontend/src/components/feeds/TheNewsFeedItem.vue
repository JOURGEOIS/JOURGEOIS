<template>
  <div class="the-news-feed-container">
    <div class="the-news-feed-header">
      <round-image
        :round-image="{ image: newsFeedData.profileImg }"
      ></round-image>
      <div>
        <p>{{ newsFeedData.nickname }}</p>
        <p>{{ calcDateDelta(newsFeedData.createTime) }}</p>
      </div>
    </div>
    <div
      class="the-news-feed-image"
      :style="{ backgroundImage: `url(${newsFeedData.postImg})` }"
    ></div>
    <div class="the-news-feed-cocktail" v-if="newsFeedData.type === 'cocktail'">
      <span class="material-icons"> local_bar </span>
      <p v-if="!newsFeedData.isSuperCustomCocktail">
        [{{ newsFeedData.baseCocktailName }}]
      </p>
      <p>
        {{ newsFeedData.cocktailTitle }}
      </p>
    </div>
    <div class="the-news-feed-content">
      <p>{{ newsFeedData.description }}</p>
    </div>
    <like-comment @clickLike="clickLike" :data="{ isLiked: isLiked }">
      <template #like>{{ likeCount }}</template>
      <template #comment>{{ newsFeedData.reviewCount }}</template>
    </like-comment>
  </div>
</template>

<script setup lang="ts">
import LikeComment from "@/components/basics/LikeComment.vue";
import RoundImage from "@/components/basics/RoundImage.vue";
import { calcDateDelta } from "../../functions/date";
import { ref } from "vue";
import { NewsFeed } from "../../interface.js";
import axios from "axios";

const props = defineProps<{
  newsFeedData: NewsFeed;
}>();

const isLiked = ref(props.newsFeedData.isLiked);
const likeCount = ref(props.newsFeedData.likeCount);

const clickLike = () => {
  if (isLiked.value === 0) {
    isLiked.value = 1;
    likeCount.value = likeCount.value + 1;
  } else {
    isLiked.value = 0;
    likeCount.value = likeCount.value - 1;
  }
};
</script>

<style scoped lang="scss">
.the-news-feed-container {
  @include flex(column);
  gap: 16px;
  padding: 16px 16px 0px 16px;
  background-color: $white;
  @include shadow-feed;
  .the-news-feed-header {
    @include flex-xy(flex-start, center);
    gap: 8px;

    .round-image {
      width: 59px;
      height: 59px;
    }

    div {
      p {
        &:first-child {
          @include font($fs-md, $fw-medium);
        }

        &:nth-child(2) {
          @include font($fs-sm, $fw-medium);
          color: $sub-color;
        }
      }
    }
  }
  .the-news-feed-image {
    width: calc(100% + 32px);
    margin-left: -16px;
    padding-bottom: 100%;
    background-color: $white150;
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
    aspect-ratio: 1/1;
  }

  .the-news-feed-cocktail {
    @include flex-xy(flex-start, center);
    gap: 4px;
    @include font($fs-md, $fw-medium);
    color: $gray200;

    .material-icons {
      color: $primary500p;
    }
  }
  .the-news-feed-content {
    @include font($fs-md, $fw-medium);

    p {
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      overflow: hidden;
      span {
        color: $white400;
      }
    }
  }
}

.like-comment-share {
  margin-left: -16px;
}
</style>
