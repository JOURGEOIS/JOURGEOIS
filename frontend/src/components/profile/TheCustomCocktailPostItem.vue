<template>
  <article class="list-item-custom-cocktail">
    <div class="item-header">
      <div class="user-part">
        <div class="user-info">
          <round-image :round-image="{image: profileImg}"></round-image>
          <div class="user-nickname">{{ nickname }}</div>
        </div>
        <div class="created-at">{{ createTimeDelta }}</div>
      </div>
      <!-- <div class="cocktail-liked">
        <span class="material-icons unliked" v-if="!ilike"> favorite </span>
        <span class="material-icons liked" v-if="ilike"> favorite </span>
        {{ like }}
      </div> -->
    </div>
    <div class="item-content-container">
      <div
        class="item-img-part"
        :style="{ backgroundImage: `url(${postImg})` }"
      ></div>
      <div class="item-text-part">
        <!-- <h1 class="cocktail-name">{{ title }}</h1> -->
        <h1 class="cocktail-name">제목이다</h1>
        <!-- <p class="cocktail-ingredients">재료: {{ ingredientsString }}</p> -->
        <p class="cocktail-ingredients">재료: 재료다</p>
        <p class="cocktail-description">
          {{ description }}
        </p>
      </div>
    </div>
  </article>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useStore } from "vuex";
import RoundImage from "@/components/basics/RoundImage.vue";
import { calcDateDelta, calcDateDelta2 } from "../../functions/date";
import { userPostData } from "../../interface"
const route = useRoute();
const store = useStore();

const props = defineProps<{
  post: userPostData;
}>();

const profileImg = props.post.profileImg
const nickname = props.post.nickname
const createTime = props.post.createTime
const createTimeDelta = calcDateDelta2(createTime);
const postImg = props.post.postImg
// const title = customPost.value.title
// const ingredients = customPost.value.ingredients
// const ingredientsString = ingredients.join(", ");
const description = props.post.description

</script>

<style scoped lang="scss">
.list-item-custom-cocktail{
  @include flex(column);
  width: 100%;
  gap: 10px;
  .item-header{
    @include flex;
    width:100%;
    .user-part{
      @include flex;
      align-items: center;
      width:100%;
      gap: 10px;

      .user-info{
        @include flex-xy(flex-start,center);
        gap: 10px;
        .round-image {
          width: 40px;
          height: 40px;
        }
      }
    }
  }
  .item-content-container{
    @include flex;
    gap: 10px;
    .item-img-part{
      position: relative;
      width: calc(20%);
      aspect-ratio: 1/1;
      background-color: $white150;
      background : {
        size: cover;
        position: center center;
      }
    }
    .item-text-part{
      @include flex(column);
      @include flex-center;
      gap: 10px;
    }
  }
}
</style>