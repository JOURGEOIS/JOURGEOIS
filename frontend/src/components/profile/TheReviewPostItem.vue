<template>
  <article class="list-item-custom-cocktail" @click="clickPost">
    <div class="item-header">
      <div class="user-part">
        {{ nameKR }}
      </div>
    </div>
    <div class="item-content-container">
      <div class="item-text-part">
        <p class="cocktail-description">
          {{ comment }}
        </p>
      </div>
    </div>
  </article>
</template>

<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { userPostReviewData } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

const props = defineProps<{
  review: userPostReviewData;
}>();

const cocktailId = props.review.cocktailId

const comment = props.review.comment
const tag = props.review.tag;
const category = props.review.category
const nameKR = props.review.nameKR

const clickPost = () => {
  router.push({
    name: "TheCocktailDescView",
    params: { cocktailId: cocktailId }
  })
}
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
        
        .round-image {
          width: 40px;
          height: 40px;
        }

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
