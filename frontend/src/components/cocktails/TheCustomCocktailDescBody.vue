<template>
  <section class="the-custom-cocktail-desc-body">
    <!-- 칵테일 제목 섹션 -->
    <article class="cocktail-title-section">
      <h1 class="cocktail-title">{{ title }}</h1>
      <div
        class="base-cocktail-line"
        v-if="baseCocktail"
        @click="clickBaseCocktail"
      >
        <span class="base-cocktail-title">
          <span class="material-icons-outlined cocktail-icon"> local_bar </span>
          <span class="base-cocktail">베이스 칵테일</span>
        </span>
        <span class="base-cocktail-name">{{ baseCocktailName }}</span>
      </div>
    </article>
    <!-- 칵테일 이미지 섹션 -->
    <article
      class="cocktail-image"
      :style="{ backgroundImage: `url(${imgLink})` }"
    ></article>
    <!-- 작성자 정보 섹션 -->
    <post-user-section
      :userInfo="customCocktailInfo?.followerDTO"
      :date="customCocktailInfo?.customCocktail?.createTime"
    ></post-user-section>
    <!-- 칵테일 설명 섹션 -->
    <article class="cocktail-description" v-if="description">
      <h1 class="title1">설명</h1>
      <p class="normal-paragraph">{{ description }}</p>
    </article>
    <!-- 칵테일 재료 섹션 -->
    <article class="cocktail-ingredients">
      <h1 class="title1">재료</h1>
      <p class="normal-paragraph">
        {{ ingredientString }}
      </p>
    </article>
    <!-- 칵테일 레시피 섹션 -->
    <article class="cocktail-recipe">
      <h1 class="title1">제작</h1>
      <div class="recipe-line" v-for="(txt, idx) in recipeList" :key="idx">
        <h2 class="title2">{{ idx + 1 }}단계</h2>
        <p class="normal-paragraph">{{ txt }}</p>
      </div>
    </article>
    <!-- 수정 삭제 섹션 -->
    <article class="cocktail-host-section" v-if="isMine">
      <div class="btn edit-btn" @click="clickEdit">수정</div>
      <div class="btn delete-btn" @click="clickDelete">삭제</div>
    </article>
  </section>
  <!-- 게시글 삭제 확인 모달 -->
  <cocktail-feeds-delete-modal
    v-if="deleteModalStatus"
    :postId="postId"
    @off-modal="offDeleteModal"
  ></cocktail-feeds-delete-modal>
</template>

<script setup lang="ts">
import PostUserSection from "@/components/basics/PostUserSection.vue";
import CocktailFeedsDeleteModal from "@/components/modals/CocktailFeedsDeleteModal.vue";
import { CustomCocktail } from "../../interface";
import { reactive, computed, toRefs, onBeforeMount, watchEffect } from "vue";
import { useStore } from "vuex";
import { useRouter, useRoute } from "vue-router";
const router = useRouter();
const route = useRoute();
const store = useStore();

const customCocktailInfo = computed(() => {
  return store.getters["customCocktailInfo/getCustomCocktailDetail"];
});

const postId = computed(
  () => customCocktailInfo?.value?.customCocktail?.postId
);
const imgLink = computed(
  () => customCocktailInfo?.value?.customCocktail?.imgLink
);
const description = computed(
  () => customCocktailInfo?.value?.customCocktail?.description
);
const like = computed(() => customCocktailInfo?.value?.customCocktail?.like);
const title = computed(() => customCocktailInfo?.value?.customCocktail?.title);
const baseCocktail = computed(
  () => customCocktailInfo?.value?.customCocktail?.baseCocktail
);
const baseCocktailName = computed(
  () => customCocktailInfo?.value?.customCocktail?.baseCocktailName
);
const ingredients = computed(
  () => customCocktailInfo?.value?.customCocktail?.ingredients
);
const recipe = computed(
  () => customCocktailInfo?.value?.customCocktail?.recipe
);

const isFollowed = computed(
  () => customCocktailInfo?.value?.followerDTO?.isFollowed
);
const isMine = computed(() => {
  return isFollowed.value === -1;
});

// 레시피 string -> string[]
const recipeList = computed(() => recipe.value.split(" <> "));

// 재료 ingredients string[] -> string
const ingredientString = computed(() => ingredients.value.join(", "));

// 베이스칵테일 구역 누르면 베이스칵테일로 이동
const clickBaseCocktail = () => {
  router.push({
    name: "TheCocktailDescView",
    params: { cocktailId: baseCocktail.value },
  });
};

// 수정 클릭
const clickEdit = () => {
  // 커스텀 칵테일인 경우
  if (baseCocktail.value) {
    router.push({
      name: "TheCustomCocktailUpdateFormView",
      params: {
        cocktailId: route.params.cocktailId,
        feedId: route.params.feedId,
      },
    });
  }
  // 슈퍼 커스텀 칵테일인 경우
  else {
    router.push({
      name: "TheSuperCustomCocktailUpdateFormView",
      params: {
        feedId: route.params.feedId,
      },
    });
  }
};

const deleteModalStatus = computed(
  () => store.getters["customCocktailInfo/getDeleteModalStatus"]
);

const offDeleteModal = () => {
  store.dispatch("customCocktailInfo/toggleDeleteModal", false);
};

// 삭제 클릭
const clickDelete = () => {
  store.dispatch("customCocktailInfo/toggleDeleteModal", true);
};
</script>

<style scoped lang="scss">
.the-custom-cocktail-desc-body {
  @include flex(column);
  width: 100%;
  // gap: 10px;

  .cocktail-title-section {
    .cocktail-title {
      // margin: 10px 0;
      @include font(20px, $fw-medium);
    }
    .base-cocktail-line {
      @include flex-xy(flex-start, center);
      gap: 10px;
      margin: 5px 0;
      @include for-click;

      .base-cocktail-title {
        @include flex-xy(flex-start, flex-end);
        gap: 2px;
        .cocktail-icon {
          @include font-size-navy(15px);
        }
        .base-cocktail {
          @include font-size-navy(12px);
        }
      }
      .base-cocktail-name {
        @include font(12px);
      }
    }
  }

  .cocktail-image {
    width: calc(100% + 32px);
    margin: 0 -16px;
    aspect-ratio: 1/1;
    background : {
      size: cover;
      position: center center;
    }
  }

  .cocktail-host-section {
    width: 100%;
    @include flex-xy(flex-end, center);
    @include font-size-sub(15px);

    .btn {
      padding: 10px;
      @include for-click;
    }
  }
}

.recipe-line {
  margin: 20px 0;
}

.title1 {
  @include font(15px, $fw-bold);
  color: $navy600;
  margin: 10px 0;
  user-select: none;
}

.title2 {
  @include font(13px, $fw-medium);
  color: $navy500;
  user-select: none;
}

.ingredients-title {
  @include font(13px, $fw-bold);
  color: $navy600;
}

.ingredients-description {
  @include font(15px, $fw-medium);
}

.normal-paragraph {
  @include font-size-sub(15px);
}

article {
  margin: 20px 0;
}
</style>
