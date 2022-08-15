<template>
  <div class="the-cocktail-desc-review">
    <div
      class="the-cocktail-desc-review-content"
      :class="{ blur: !isLoggedIn }"
    >
      <the-cocktail-desc-review-form></the-cocktail-desc-review-form>
      <the-cocktail-desc-review-list></the-cocktail-desc-review-list>
    </div>
    <blur-block v-if="!isLoggedIn"> 로그인하고 후기 보기 🎉</blur-block>
  </div>
</template>

<script setup lang="ts">
import TheCocktailDescReviewForm from "@/components/cocktails/TheCocktailDescReviewForm.vue";
import TheCocktailDescReviewList from "@/components/cocktails/TheCocktailDescReviewList.vue";
import BlurBlock from "@/components/basics/BlurBlock.vue";
import { useStore } from "vuex";
import { computed } from "vue";
const store = useStore();

const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);
</script>

<style scoped lang="scss">
.the-cocktail-desc-review {
  width: 100%;
  position: relative;
  @include flex-center;

  .the-cocktail-desc-review-content {
    width: 100%;
    @include flex(column);
    gap: 24px;
  }
}

.blur {
  min-height: 200px;
  filter: blur(6px);
  -webkit-filter: blur(6px);
}
</style>
