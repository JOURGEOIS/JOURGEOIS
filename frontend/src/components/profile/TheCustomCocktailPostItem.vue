<template>
  <article class="list-item-custom-cocktail">
    <div class="item-header">
      <!-- <div class="user-part">
        <div class="user-info">
          <round-image :round-image="{image: post.profileImg}"></round-image>
          <div class="user-nickname">{{ post.nickname }}</div>
        </div>
        <div class="created-at">{{ createTimeDelta }}</div>
      </div> -->
      <!-- <div class="cocktail-liked">
        <span class="material-icons unliked" v-if="!ilike"> favorite </span>
        <span class="material-icons liked" v-if="ilike"> favorite </span>
        {{ ilike }}
      </div> -->
    </div>
    <div class="item-content-container">
      <!-- <div
        class="item-img-part"
        :style="{ backgroundImage: `url(${post.postImg})` }"
      ></div> -->
      <div class="item-text-part">
        <!-- <h1 class="cocktail-name">{{ title }}</h1> -->
        <!-- <h1 class="cocktail-name"><span class="material-icons"> local_bar </span>제목이다제목이다제목이다</h1> -->
        <!-- <p class="cocktail-ingredients">재료: {{ ingredientsString }}</p> -->
        <!-- <p class="cocktail-ingredients">재료: 재료다</p> -->
        <p class="cocktail-description">
          {{ post?.description }}
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

// const profileImg = props.post.profileImg
// const nickname = props.post.nickname
// const createTime = props.post.createTime;
// const createTimeDelta = calcDateDelta2(createTime);
// const postImg = props.post.postImg
// const baseCocktail = props.post.baseCocktail
// const postId = props.post.postId
// const ilike = props.post.iLike
// const likes = props.post.likes
// const ingredients = props.post.ingredients
// const ingredientsString = ingredients.join(", ");
// const description = props.post.description

// const clickPost = () => {
//   router.push({
//     name: "TheCommunityDescView",
//     params: { feedId: postId }
//   })
// }
</script>

<style scoped lang="scss">
.list-item-custom-cocktail {
  @include flex(column);
  width: 100%;
  gap: 10px;
  border: 1px solid $white100;
  border-radius: 8px;
  padding: 20px 16px;
  background: $white;
  box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.1);

  .item-header {
    @include flex;
    width: 100%;
    .user-part {
      @include flex;
      align-items: center;
      width: 100%;
      gap: 10px;

      .user-info {
        @include flex-xy(flex-start, center);
        gap: 10px;
        .round-image {
          width: 40px;
          height: 40px;
        }
      }
    }
  }
  .item-content-container {
    @include flex-center;
    gap: 10px;
    .item-img-part {
      position: relative;
      height: calc(100%);
      aspect-ratio: auto 1/1;
      background-color: $white;
      background : {
        size: cover;
        position: center center;
      }
    }
    .item-text-part {
      @include flex(column);
      gap: 10px;
      h1 {
        @include flex-xy(flex-start, end);
        .material-icons {
          align-items: baseline;
        }
      }
      p {
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
        overflow: hidden;
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
}
</style>
