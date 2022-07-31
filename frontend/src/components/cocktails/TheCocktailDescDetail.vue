<!-- 칵테일 상세 페이지 상단 부분 -->
<template>
  <div class="cocktail-desc-detail">
    <!-- 칵테일 제목 -->
    <div class="cocktail-desc-detail-title">
      <p>롱 아일랜드 아이스 티</p>
      <p>Long Island Iced Tea</p>
    </div>
    <round-image :round-image="cocktailImage"></round-image>

    <!-- 칵테일 태그 -->
    <div class="cocktail-desc-tag">
      <tag-basic
        :tagStyle="['light-primary', '12px', '5px']"
        v-for="(tag, index) in tags"
        :key="index"
      >
        {{ tag }}
      </tag-basic>
    </div>

    <!-- 칵테일 북마크, 베이스 술, 도수 표현  -->
    <div class="cocktail-desc-detail-content">
      <!-- 1. 북마크-->
      <div>
        <span
          class="material-icons-outlined bookmark"
          v-if="!isBookmarked"
          @click="clickBookMark"
        >
          bookmarks
        </span>
        <span
          class="material-icons bookmark"
          v-if="isBookmarked"
          @click="clickBookMark"
        >
          bookmarks
        </span>
        <p>북마크</p>
        <!-- 북마크 수: 클릭 시 팔로우한 유저들을 보여주는 목록으로 이동 -->
        <router-link :to="`/cocktail/${cocktailId}/bookmark`">
          <p class="bookmark">999+</p>
        </router-link>
      </div>
      <!-- 2. 베이스 술 -->
      <div>
        <span class="material-icons-outlined"> local_bar </span>
        <p>베이스 술</p>
        <p>데킬라</p>
      </div>
      <!-- 3. 데킬라 -->
      <div>
        <span class="material-icons-outlined"> bubble_chart </span>
        <p>도수</p>
        <p>무알콜</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import TagBasic from "@/components/basics/TagBasic.vue";
import RoundImage from "@/components/basics/RoundImage.vue";
import { ref } from "vue";

// 칵테일 이미지
const cocktailImage = {
  image:
    "https://www.thecocktaildb.com/images/media/drink/wx7hsg1504370510.jpg",
  width: "120px",
};

// 칵테일 태그
const tags = ["IBA", "ContemporaryClassic", "Strong", "Ordinary Drink"];

// 북마크
const isBookmarked = ref(false);

const clickBookMark = () => {
  isBookmarked.value = !isBookmarked.value;
};

// 칵테일 id
const cocktailId = ref(3);
</script>

<style scoped lang="scss">
.cocktail-desc-detail {
  @include flex(column);
  align-items: center;
  justify-content: center;
  gap: 24px;
  width: 100%;
  .cocktail-desc-detail-title {
    @include flex(column);
    align-items: center;
    justify-content: center;
    gap: 8px;
    p {
      &:first-child {
        @include font($fs-xl, $fw-bold);
      }
      &:last-child {
        @include font($fs-md, $fw-medium);
      }
    }
  }
  .cocktail-desc-tag {
    @include flex-center;
    gap: 6px;
    margin-bottom: 16px;
  }

  .cocktail-desc-detail-content {
    @include flex-center;
    gap: 48px;

    div {
      @include flex(column);
      align-items: center;

      span {
        color: $navy500;
        font-size: $fs-xxl;
        margin-bottom: 8px;
      }

      p {
        &:nth-child(2) {
          @include font($fs-md, $fw-regular);
          color: $navy500;
        }

        &:last-child {
          @include font($fs-main, $fw-bold);
          color: $navy700;
        }
      }
    }

    @media #{$tablet} {
      gap: 64px;
    }

    @media #{$pc} {
      gap: 96px;
    }
  }
}
.bookmark {
  cursor: pointer;
}

a {
  text-decoration: none;
}
</style>
