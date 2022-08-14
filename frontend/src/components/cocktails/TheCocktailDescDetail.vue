<!-- 칵테일 상세 페이지 상단 부분 -->
<template>
  <div class="cocktail-desc-detail">
    <!-- 칵테일 제목 -->
    <div class="cocktail-desc-detail-title">
      <p>{{ cocktailData.nameKR }}</p>
      <p>{{ cocktailData.name }}</p>
    </div>
    <div
      class="image"
      :style="{ backgroundImage: `url(${cocktailData.img})` }"
    ></div>

    <!-- 칵테일 태그 -->
    <div class="cocktail-desc-tag">
      <tag-basic
        :tagStyle="['light-primary', '12px', '5px']"
        v-for="(tag, index) in cocktailTags"
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
          v-if="!cocktailData.status"
          @click="clickBookMark"
        >
          bookmark_border
        </span>
        <span class="material-icons bookmark" v-else @click="clickBookMark">
          bookmark
        </span>
        <p>북마크</p>
        <!-- 북마크 수: 클릭 시 팔로우한 유저들을 보여주는 목록으로 이동 -->
        <router-link :to="`/cocktail/${cocktailId}/bookmark`">
          <p class="bookmark">{{ cocktailData.count }}</p>
        </router-link>
      </div>
      <!-- 2. 베이스 술 -->
      <div>
        <span class="material-icons"> local_bar </span>
        <p>베이스 술</p>
        <p>
          {{ cocktailData.baseLiquor ? cocktailData.baseLiquor : "무알콜" }}
        </p>
      </div>
      <!-- 3. 도수 -->
      <div>
        <span class="material-icons-outlined"> bubble_chart </span>
        <p>도수</p>
        <p>{{ cocktailData.alcohol ? cocktailData.alcohol + "도" : "0도" }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import TagBasic from "@/components/basics/TagBasic.vue";
import RoundImage from "@/components/basics/RoundImage.vue";
import { ref, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
const router = useRouter();
const store = useStore();

const cocktailData = computed(
  () => store.getters["cocktailDesc/getCurrentCocktailData"]
);
const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);

// 북마크
const clickBookMark = () => {
  if (!isLoggedIn.value) {
    router.push({ name: "TheLoginView" });
    return;
  }
  store.dispatch("cocktailDesc/clickBookMark");
};

// 칵테일 id
const cocktailId = computed(() => cocktailData.value.id);

// 칵테일 태그
const tags = computed(() => cocktailData?.value?.tag?.split(","));
const category = computed(() => cocktailData?.value?.category);

let cocktailTags = computed(() => {
  if (tags?.value?.length < 4) {
    if (!tags.value[0]) {
      return Array(category.value);
    } else {
      const result = tags?.value?.concat(Array(category.value));
      return result;
    }
  } else {
    const result = tags?.value?.slice(0, 3)?.concat(Array(category.value));
    return result;
  }
});
</script>

<style scoped lang="scss">
.cocktail-desc-detail {
  @include flex(column);
  align-items: center;
  justify-content: center;
  gap: 16px;
  width: 100%;
  .cocktail-desc-detail-title {
    @include flex(column);
    align-items: center;
    justify-content: center;
    gap: 4px;
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

.material-icons-outlined {
  font-weight: 500;
}

a {
  text-decoration: none;
}

.image {
  width: 120px;
  height: 120px;
  border: 0;
  border-radius: 50%;
  background-repeat: no-repeat;
  background-color: $white200;
  background-position: center;
  background-size: cover;
}
</style>
