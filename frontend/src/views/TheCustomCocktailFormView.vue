<!--칵테일 상세 페이지:  커스텀 칵테일 제작 페이지 -->
<template>
  <div class="the-custom-cocktail-form-view">
    <!-- 헤더 -->
    <header-basic
      :prev="true"
      :success="true"
      formId="custom-cocktail-form"
      @prevClicked="$router.go(-1)"
    >
      커스텀 칵테일 제작
    </header-basic>
    <section>
      <the-custom-cocktail-form
        id="custom-cocktail-form"
      ></the-custom-cocktail-form>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheCustomCocktailForm from "@/components/cocktails/TheCustomCocktailForm.vue";
import { useStore } from "vuex";
import { onBeforeMount } from "vue";
import { useRoute } from "vue-router";
const route = useRoute();
const store = useStore();

onBeforeMount(() => {
  store.dispatch(
    "customCocktail/getOriginalCocktailData",
    route.params.cocktailId
  );
});
</script>

<style scoped lang="scss">
.the-custom-cocktail-form-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  section {
    @include flex(column);
    width: 100%;
    gap: 36px;
    margin-top: 2rem;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }
  }
}
</style>
