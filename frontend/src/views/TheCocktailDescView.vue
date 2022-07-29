<template>
  <div class="cocktail-desc-view">
    <the-cocktail-desc></the-cocktail-desc>
    <section class="cocktail-desc-section">
      <div class="cocktail-desc-tab">
        <p @click="clickRecipeTab">레시피</p>
        <p @click="clickReviewTab">후기</p>
        <p @click="clickCustomTab">커스텀</p>
      </div>
      <keep-alive>
        <component :is="currentComponent"></component>
      </keep-alive>
    </section>
  </div>
</template>

<script setup lang="ts">
import { onBeforeMount, defineAsyncComponent, computed } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import TheCocktailDesc from "@/components/cocktails/TheCocktailDesc.vue";
const store = useStore();
const route = useRoute();

// 동적 컴포넌트 (탭)
const componentArray = [
  defineAsyncComponent(
    () => import("@/components/cocktails/TheCocktailDescRecipe.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/cocktails/TheCocktailDescReview.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/cocktails/THeCocktailDescCustom.vue")
  ),
];

const index = computed(() => store.getters["cocktailDesc/getCurrentTab"]);

const currentComponent = computed(() => {
  return componentArray[index.value];
});

const clickRecipeTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 0);
const clickReviewTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 1);
const clickCustomTab = () => store.dispatch("cocktailDesc/changeCurrentTab", 2);

// 동적 라우팅
onBeforeMount(() => {
  console.log(route.params.cocktailId);
});
</script>

<style scoped lang="scss">
.cocktail-desc-view {
  @include accountLayOut;

  section {
    @include flex(column);
    justify-content: center;
    align-items: center;
    .cocktail-desc-tab {
      @include flex-xy(space-between, center);
      width: 100%;
    }
  }
}
</style>
