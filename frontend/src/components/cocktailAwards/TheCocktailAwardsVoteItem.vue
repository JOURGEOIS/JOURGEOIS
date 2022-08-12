<template>
  <div class="cocktail-awards-vote-item-card">
    <div
      class="cocktail-awards-vote-item-image"
      :style="{ backgroundImage: imageUrl }"
    ></div>
    <p>{{ cocktailAwardsVoteItem.title }}</p>
    <button
      v-if="!cocktailAwardsVoteItem.like"
      class="cocktail-awards-vote-item-btn vote"
      @click="clickVote"
    >
      투표하기
    </button>
    <button
      v-else
      class="cocktail-awards-vote-item-btn cancel"
      @click="clickVote"
    >
      투표취소
    </button>
  </div>
</template>

<script setup lang="ts">
import { ContestCocktail } from "../../interface";
import { computed } from "vue";
import store from "../../store";

const props = defineProps<{
  cocktailAwardsVoteItem: ContestCocktail;
  cocktailAwardsVoteRank: number;
}>();

const imageUrl = computed(() => {
  return `url(${props.cocktailAwardsVoteItem.imgLink})`;
});

const clickVote = () => {
  const data = {
    index: props.cocktailAwardsVoteRank,
    postId: props.cocktailAwardsVoteItem.postId,
  };
  store.dispatch("cocktailAwards/voteCocktail", data);
};
</script>

<style scoped lang="scss">
.cocktail-awards-vote-item-card {
  @include flex-xy(center, flex-start);
  flex-wrap: wrap;
  width: 100%;
  height: 100%;
  border-radius: 12px;
  @include feed;
  padding: 0px;
  padding-bottom: 10px;

  .cocktail-awards-vote-item-image {
    width: 100%;
    aspect-ratio: 1/1;
    border-radius: 12px 12px 0px 0px;
    background : {
      size: cover;
      position: center center;
    }
  }

  p {
    overflow: hidden;
    text-overflow: ellipsis;
    width: 90%;
    white-space: nowrap;
    text-align: center;
    @include font($fs-main, $fw-regular);
  }

  .cocktail-awards-vote-item-btn {
    width: 90%;
    padding: 0.4em;
    border-radius: 6px;
    text-align: center;
    @include font($fs-sm, $fw-medium);
  }

  .vote {
    background-color: $red500;
    color: $white;
  }

  .cancel {
    background-color: $white150;
    color: $sub-color;
  }
}
</style>
