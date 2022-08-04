<template>
  <section class="cocktail-search-header">
    <span class="material-icons back-icon icon" @click="clickBackIconInner">
      arrow_back_ios_new
    </span>
    <the-search-input
      :data="searchInputData"
      v-model="searchInputValue"
      @clickInput="clickInputInner"
      @clickCloseIcon="clickCloseIconInner"
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
import { reactive, ref, computed, watch, watchEffect } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
const router = useRouter();
const route = useRoute();
const store = useStore();

const emit = defineEmits<{
  (e: "clickBackIcon"): void;
  (e: "clickInput"): void;
  (e: "clickCloseIcon"): void;
}>();

const clickBackIconInner = () => {
  // 검색 view 인 경우
  if (route.name == "TheCocktailSearchView") {
    // 검색어가 있는 경우
    if (store.getters["cocktailSearch/getSearchInputValue"]) {
      store.dispatch("cocktailSearch/setSearchInputValue", "");
      searchInputValue.value = "";
    }
    // 검색어가 없는 경우
    else {
      router.push({ name: "TheHomeView" });
    }
  }
  // 검색 view가 아닌 경우
  else {
    router.push({
      name: "TheCocktailSearchView",
      params: { searchValue: searchInputValue.value },
    });
  }
};

// vuex의 저장 입력값 초기에 넣어주기
const searchInputValue = ref(
  store.getters["cocktailSearch/getSearchInputValue"]
);

//
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
const clickCloseIconInner = () => {
  searchInputValue.value = "";
  router.push({ name: "TheCocktailSearchView" });
  // emit("clickCloseIcon");
};

// input 버튼 누르면 emit
const clickInputInner = () => {
  emit("clickInput");
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
