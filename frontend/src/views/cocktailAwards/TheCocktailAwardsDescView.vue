<template>
  <div class="the-cocktail-awards-desc-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      칵테일 어워즈
    </header-basic>
    <section class="the-cocktail-awards-desc-section top-view-no-margin">
      <div
        :style="{ backgroundImage: `url(${cocktailAwardsDescData.imgLink})` }"
      ></div>
      <article>
        <p class="the-cocktail-award-desc-name">
          {{ cocktailAwardsDescData?.title }}
        </p>
        <p>{{ cocktailAwardsDescData?.description }}</p>
      </article>
      <button
        v-if="!cocktailAwardsDescData.like"
        class="cocktail-awards-desc-item-btn vote"
        @click="clickVote"
      >
        투표하기
      </button>
      <button
        v-else
        class="cocktail-awards-desc-item-btn cancel"
        @click="clickVote"
      >
        투표취소
      </button>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { onBeforeMount, onUnmounted, computed } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import { tsNullKeyword } from "@babel/types";
const store = useStore();
const route = useRoute();

// 데이터
const cocktailAwardsDescData = computed(
  () => store.getters["cocktailAwards/getCocktailAwardsDetail"]
);

// 데이터 fetch
onBeforeMount(() => {
  store.dispatch("cocktailAwards/fetchCocktailAwardsDesc", {
    postId: route.params.feedId,
  });
});

// 데이터 reset
onUnmounted(() => {
  store.dispatch("cocktailAwards/resetCocktailAwardsDesc");
});

// 투표하기
const clickVote = () => {
  const data = {
    postId: cocktailAwardsDescData.value.postId,
    index: null,
  };
  store.dispatch("cocktailAwards/voteCocktail", data);
};
</script>

<style scoped lang="scss">
.the-cocktail-awards-desc-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  section {
    @include flex(column);
    gap: 32px;
    width: 100%;
    margin-top: 8px;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 60%;
    }

    div {
      width: calc(100% + 32px);
      aspect-ratio: 1/1;
      margin: 0 -16px;
      background-color: $white150;
      background : {
        size: cover;
        position: center center;
      }
    }

    article {
      @include flex(column);
      gap: 8px;

      p {
        @include font($fs-md, $fw-regular);
      }

      .the-cocktail-award-desc-name {
        @include font($fs-lg, $fw-medium);
        color: $navy600;
      }
    }
  }
}

.cocktail-awards-desc-item-btn {
  width: 100%;
  padding: 0.5em;
  border-radius: 6px;
  text-align: center;
  margin-top: 40px;
  @include font($fs-md, $fw-medium);
}
.vote {
  background-color: $red500;
  color: $white;
}

.cancel {
  background-color: $white150;
  color: $sub-color;
}
</style>
