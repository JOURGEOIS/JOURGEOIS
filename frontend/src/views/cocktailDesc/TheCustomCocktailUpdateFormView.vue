<!--칵테일 상세 페이지:  커스텀 칵테일 수정 페이지 -->
<template>
  <div class="the-custom-cocktail-form-view">
    <!-- 헤더 -->
    <header-success
      formId="custom-cocktail-update-form"
      @prevClicked="$router.go(-1)"
    >
      커스텀 칵테일 수정
    </header-success>
    <section class="top-view">
      <the-custom-cocktail-update-form
        id="custom-cocktail-update-form"
      ></the-custom-cocktail-update-form>
    </section>
  </div>
  <failure-pop-up v-if="errorStatus" @off-modal="offFailurePopUp">
    {{ errorMessage }}
  </failure-pop-up>
  <loading-basic v-if="loadingStatus"></loading-basic>
</template>

<script setup lang="ts">
import HeaderSuccess from "@/components/basics/HeaderSuccess.vue";
import TheCustomCocktailUpdateForm from "@/components/cocktails/TheCustomCocktailUpdateForm.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
import LoadingBasic from "@/components/basics/LoadingBasic.vue";
import { useStore } from "vuex";
import { onBeforeMount, computed, watch, onUnmounted } from "vue";
import { useRoute } from "vue-router";
const route = useRoute();
const store = useStore();

const errorStatus = computed(
  () => store.getters["customCocktail/getAlertStatus"]
);

const errorMessage = computed(
  () => store.getters["customCocktail/getErrorMessage"]
);

const loadingStatus = computed(
  () => store.getters["customCocktail/getLoadingStatus"]
);

// 시간제 모달
watch(errorStatus, () => {
  if (errorStatus.value) {
    setTimeout(() => offFailurePopUp(), 2000);
  }
});

const offFailurePopUp = () => {
  store.dispatch("customCocktail/changeAlertStatus", false);
};

// 모달 초기화 및 db 요청
onBeforeMount(() => {
  store.dispatch("customCocktail/getCustomCocktailData", route.params.feedId);
  store.dispatch("customCocktail/changeAlertStatus", false);
  store.dispatch("customCocktail/toggleLoadingStatus", false);
});

onUnmounted(() => {
  store.dispatch("customCocktail/resetCocktailData");
});
</script>

<style scoped lang="scss">
.the-custom-cocktail-form-view {
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
