<template>
  <section class="cocktail-search-header">
    <span class="material-icons back-icon icon" @click="$router.go(-1)">
      arrow_back_ios_new
    </span>
    <the-search-input
      :data="searchInputData"
      v-model="searchInputValue"
      @clickCloseIcon="clickCloseIcon"
    ></the-search-input>
    <span
      class="material-icons filter-icon icon"
      v-if="!searchInputValue"
      @click="clickFilterIcon"
    >
      tune
    </span>
  </section>
</template>

<script setup lang="ts">
import TheSearchInput from "@/components/cocktails/TheSearchInput.vue";
import { reactive, ref, computed, watchEffect } from "vue";
import { useStore } from "vuex";
const store = useStore();

// vuex의 저장 입력값 초기에 넣어주기
const searchInputValue = ref(
  store.getters["cocktailSearch/getSearchInputValue"]
);

watchEffect(() => {
  store.dispatch("cocktailSearch/setSearchInputValue", searchInputValue.value);
});

const searchInputData: object = reactive({
  button: true,
  id: "search-input",
  placeholder: "칵테일, 재료, 유저 검색",
  maxlength: 20,
});

// 검색 창 섹션
// filter 아이콘 클릭
const clickFilterIcon = () => {
  store.dispatch("cocktailSearch/changeFilterClass", "start");
  store.dispatch("cocktailSearch/toggleFilter", true);
};

// x 버튼 클릭
const clickCloseIcon = () => {
  searchInputValue.value = "";
};
</script>

<style scoped lang="scss">
.cocktail-search-header {
  @include flex-xy(flex-start, center);
  gap: 10px;
  margin: 10px 0;
  margin-bottom: 30px;

  .back-icon {
    @include flex;
    flex-grow: 0;
    user-select: none;
    color: $sub-color;

    &:hover {
      color: $main-color;
    }
  }

  .filter-icon {
    user-select: none;
    color: $primary-color;
    @include font($fs-xl);
  }
}

.material-icons {
  cursor: pointer;
}
</style>
