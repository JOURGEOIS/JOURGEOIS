<template>
  <div class="the-contest-form-view">
    <!-- 헤더 -->
    <header-success
      formId="contest-cocktail-form"
      @prevClicked="$router.go(-1)"
    >
      칵테일 어워즈 참여하기
    </header-success>
    <section class="top-view">
      <the-cocktail-awards-form
        id="contest-cocktail-form"
      ></the-cocktail-awards-form>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderSuccess from "@/components/basics/HeaderSuccess.vue";
import { useStore } from "vuex";
import TheCocktailAwardsForm from "@/components/cocktailAwards/TheCocktailAwardsForm.vue";
import { onBeforeMount, onUnmounted } from "vue";
const store = useStore();

onBeforeMount(() => {
  store.dispatch("modal/toggleErrorModalAppStatus", false);
  store.dispatch("modal/changeErrorModalMessage", "");
});

// vuex 초기화
onUnmounted(() => {
  store.dispatch("customCocktail/resetCocktailData");
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
