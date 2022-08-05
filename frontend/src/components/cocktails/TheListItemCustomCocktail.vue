<template>
  <article class="list-item-custom-cocktail">
    <div class="item-header">
      <div class="user-part">
        <div class="user-info">
          <round-image :round-image="profileImage"></round-image>
          <div class="user-nickname">칵테일조아</div>
        </div>
        <div class="created-at">방금 전</div>
      </div>
      <div class="cocktail-liked">
        <span class="material-icons"> favorite </span>
        82
      </div>
    </div>
    <div class="item-content-container">
      <div class="item-img-part"></div>
      <div class="item-text-part">
        <h1 class="cocktail-name">마! 가린</h1>
        <p class="cocktail-ingredients">재료: 데킬라, 라임, 버터</p>
        <p class="cocktail-description">
          "낮져밤이 마가리타" 빨간색과 파란색이 음양의 조화를 이루는 뭐시기
          저시기 요시기 호시기
        </p>
      </div>
    </div>
  </article>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { reactive } from "vue";

// 이미지
const profileImage = reactive({
  image:
    "https://file.mk.co.kr/meet/neds/2021/07/image_readmed_2021_659579_16257086594710103.jpg",
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
    margin: 10px 0;

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
      gap: 2px;

      .material-icons {
        @include font(15px);
        color: $red300;
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
</style>
