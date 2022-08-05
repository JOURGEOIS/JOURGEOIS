<template>
  <article class="list-item-custom-cocktail">
    <div class="item-header">
      <div class="user-part">
        <div class="user-info">
          <round-image :round-image="profileImage"></round-image>
          <div class="user-nickname">{{ nickname }}</div>
        </div>
        <div class="created-at">{{ createTimeDelta }}</div>
      </div>
      <div class="cocktail-liked">
        <span class="material-icons unliked" v-if="!ilike"> favorite </span>
        <span class="material-icons liked" v-if="ilike"> favorite </span>
        {{ like }}
      </div>
    </div>
    <div class="item-content-container">
      <div
        class="item-img-part"
        :style="{ backgroundImage: `url(${imgLink})` }"
      ></div>
      <div class="item-text-part">
        <h1 class="cocktail-name">{{ title }}</h1>
        <p class="cocktail-ingredients">재료: {{ ingredientsString }}</p>
        <p class="cocktail-description">
          {{ description }}
        </p>
      </div>
    </div>
  </article>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { CustomCocktail } from "../../interface";
import { calcDateDelta } from "../../functions/date";
import { reactive } from "vue";

const props = defineProps<{
  data: CustomCocktail;
}>();

const { customCocktail, followerDTO } = props.data;
const { nickname, profileImg } = followerDTO;
const { imgLink, description, createTime, like, ilike, title, ingredients } =
  customCocktail;

// props 후처리
const createTimeDelta = calcDateDelta(createTime);
const ingredientsString = ingredients.join(", ");

// 이미지
const profileImage = reactive({
  image: profileImg,
  width: "30px",
});
</script>

<style scoped lang="scss">
.list-item-custom-cocktail {
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
    @include flex-xy(space-between, center);
    gap: 10px;
    margin-bottom: 5px;

    .user-part {
      @include flex-xy(flex-start, center);
      gap: 10px;

      .user-info {
        @include flex-xy(flex-start, center);
        gap: 10px;

        .user-nickname {
          @include font(17px);
        }
      }
      .created-at {
        @include font-size-placeholder(13px);
      }
    }

    .cocktail-liked {
      @include flex-xy(flex-start, center);
      @include font-size-sub(13px);
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
      height: 100%;
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
        @include text-overflow-ellipsis;
        @include font(18px, $fw-medium);
      }

      .cocktail-ingredients {
        margin-bottom: 10px;
        @include font-size-navy(12px);
        @include text-overflow-ellipsis;
      }

      .cocktail-description {
        @include text-overflow-ellipsis;
        @include font-size-sub(13px);
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
