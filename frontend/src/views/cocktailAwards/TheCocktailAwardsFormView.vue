<template>
  <div class="the-contest-form-view">
    <!-- 헤더 -->
    <header-basic
      :prev="true"
      :success="true"
      formId="contest-cocktail-form"
      @prevClicked="$router.go(-1)"
    >
      칵테일 어워즈 참여하기
    </header-basic>
    <section class="top-view">
      <the-cocktail-awards-form
        id="contest-cocktail-form"
      ></the-cocktail-awards-form>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import { useStore } from "vuex";
import TheCocktailAwardsForm from "@/components/cocktailAwards/TheCocktailAwardsForm.vue";
import { onBeforeMount } from "vue";
const store = useStore();

onBeforeMount(() => {
  store.dispatch("modal/toggleErrorModalAppStatus", false);
  store.dispatch("modal/changeErrorModalMessage", "");
});
</script>

<style scoped lang="scss">
.the-contest-form-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  position: relative;
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
