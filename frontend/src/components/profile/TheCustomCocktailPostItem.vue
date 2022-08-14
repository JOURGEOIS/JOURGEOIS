<template>
  <article class="list-item-custom-cocktail" @click="clickPost">
    <div class="item-header">
      <div class="user-part">
        <div class="user-info">
          <round-image :round-image="{image: profileImg}"></round-image>
          <div class="user-nick-time">
            <div class="user-nickname">{{ nickname }}</div>
            <div class="created-at">{{ createTimeDelta }}</div>
          </div>
        </div>
      </div>
      <div class="cocktail-liked">
        <span class="material-icons unliked" v-if="!ilike"> favorite </span>
        <span class="material-icons liked" v-if="ilike"> favorite </span>
        {{ ilike }}
      </div>
    </div>
    <div class="item-content-container">
      <div
        class="item-img-part"
        :style="{ backgroundImage: `url(${postImg})` }"
      ></div>
      <div class="item-text-part">
        <h1 class="cocktail-name"><span class="material-icons"> local_bar </span>{{ title }}</h1>
        <p class="cocktail-ingredients">재료: {{ ingredients }}</p>
        <p class="cocktail-description">
          {{ description }}
        </p>
      </div>
    </div>
  </article>
</template>

<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import RoundImage from "@/components/basics/RoundImage.vue";
import { calcDateDelta, calcDateDelta2 } from "../../functions/date";
import { userCustomPostData } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

const props = defineProps<{
  post: userCustomPostData;
}>();

const baseCocktail = props.post.baseCocktail
const postId = props.post.postId

const profileImg = props.post.profileImg
const nickname = props.post.nickname
const createTime = props.post.createTime;
const createTimeDelta = calcDateDelta2(createTime);
const postImg = props.post.postImg
const ilike = props.post.iLike
const likes = props.post.likes
const title = props.post.title
const ingredients = props.post.ingredients
const description = props.post.description

const clickPost = () => {
  if (!baseCocktail) {
    router.push({
      name: "TheSuperCustomCocktailDescView",
      params: { feedId: postId }
    })
  } else {
    router.push({
      name: "TheCustomCocktailDescView",
      params: {
        cocktailId: baseCocktail,
        feedId: postId,
      }
    })
  }
}
</script>

<style scoped lang="scss">
.list-item-custom-cocktail {
  height: 181px;
  width: 100%;
  padding: 20px;
  border: 1px solid $white150;
  border-radius: 10px;
  @include flex(column);
  gap: 10px;
  cursor: pointer;

  &:hover {
    @include shadow-popup1;
    transition: all 0.3s;
  }

  @include shadow-feed;
  .item-header {
    @include flex-xy(space-between, flex-start);
    gap: 10px;
    margin-bottom: 5px;

    .user-part {
      @include flex-xy(flex-start, center);
      gap: 10px;

      .user-info {
        @include flex-xy(flex-start, center);
        gap: 10px;
        
        .round-image {
          width: 40px;
          height: 40px;
        }

        .user-nick-time{
          @include flex(column);
          .user-nickname {
            @include font(17px);
          }
          .created-at {
            @include font-size-placeholder(13px);
          }
        }
      }
    }

    .cocktail-liked {
      @include flex-xy(flex-start, center);
      @include font-size-sub(13px);
      padding-top: 5px;
      gap: 4px;

      .material-icons {
        @include font(15px);
      }
    }
  }
  .item-content-container {
    @include flex;
    gap: 10px;
    .item-img-part {
      height: 74.8px;
      width: 74.8px;
      aspect-ratio: 1/1;
      border-radius: 5px;
      background : {
        image: url("https://img.freepik.com/premium-photo/two-glasses-with-purple-cocktail-pumpkin-for-halloween-party_79919-1321.jpg");
        size: cover;
        position: center center;
      }
    }
    .item-text-part {
      @include text-overflow-ellipsis;

      .cocktail-name {
        @include flex-xy(flex-start, baseline);
        @include font(18px, $fw-medium);
        .material-icons{
          @include font(16px);
          margin-top: 5px;
        }
      }

      .cocktail-ingredients {
        margin-bottom: 10px;
        @include font-size-navy(12px);
      	@include text-overflow-ellipsis;
      }

      .cocktail-description {
        @include font-size-sub(13px);
      	@include text-overflow-ellipsis;
      }
    }
  }
}

.unliked {
  color: $placeholder-color;
}

.liked {
  color: $red300;
}
</style>
