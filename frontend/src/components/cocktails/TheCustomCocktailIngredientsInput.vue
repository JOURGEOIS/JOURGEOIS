<template>
  <form class="custom-cocktail-search" @submit.self.prevent="submitSearchValue">
    <label for="custom-cocktail-search-input">
      재료 (최대 10개) <span> *</span>
    </label>
    <the-search-input
      :data="customCocktailSearchData"
      v-model="customCocktailSearchValue"
      @click-close-icon="resetInputValue"
    ></the-search-input>
    <div class="custom-cocktail-search-list">
      <div
        class="custom-cocktail-search-item"
        v-for="(item, index) in searchIngredientsList"
        :key="`${item}-${index}`"
        @click="clickSearchValue(item)"
      >
        {{ item }}
      </div>
    </div>
    <div class="custom-cocktail-search-ingredients">
      <div v-for="(item, index) in customCocktailIngredients" :key="index">
        {{ item }}
        <span class="material-icons" @click="deleteSearchIngredients(item)">
          close
        </span>
      </div>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref, watch, computed } from "vue";
import { useStore } from "vuex";
import TheSearchInput from "@/components/cocktails/TheSearchInput.vue";
const store = useStore();

const customCocktailSearchData = {
  button: true,
  id: "custom-cocktail-search-input",
  placeholder: "재료를 입력해주세요",
  maxlength: 20,
};

// 오리지널 재료 리스트
const customCocktailIngredients = computed(
  () => store.getters["customCocktail/getOriginalCocktailIngredients"]
);

// 검색 input
const customCocktailSearchValue = ref("");

// 검색어 자동완성 (디바운스와 쓰로슬링)
let debounce: ReturnType<typeof setTimeout>;
let throttle: ReturnType<typeof setTimeout> | null;
watch(customCocktailSearchValue, () => {
  if (debounce) {
    clearTimeout(debounce);
  }
  debounce = setTimeout(
    () =>
      store.dispatch(
        "customCocktail/searchIngredients",
        customCocktailSearchValue.value
      ),
    200
  );

  // if (!throttle) {
  //   throttle = setTimeout(() => {
  //     throttle = null;
  //     store.dispatch(
  //       "customCocktail/searchIngredients",
  //       customCocktailSearchValue.value
  //     );
  //   }, 500);
  // }
});

// 자동완성 리스트
const searchIngredientsList = computed(
  () => store.getters["customCocktail/getSearchIngredientsList"]
);

// x버튼 클릭시, 모든 input 데이터가 삭제된다.
const resetInputValue = () => {
  customCocktailSearchValue.value = "";
  store.dispatch("customCocktail/resetSearchIngredients");
};

// 재료의 x버튼을 클릭 시, 재료가 삭제 된다.
const deleteSearchIngredients = (item: string) =>
  store.dispatch("customCocktail/deleteIngredients", item);

// 검색어 제출
const submitSearchValue = () => {
  store.dispatch(
    "customCocktail/addIngredients",
    customCocktailSearchValue.value
  );
  store.dispatch("customCocktail/resetSearchIngredients");
  customCocktailSearchValue.value = "";
};

// 검색어 자동완성 클릭
const clickSearchValue = (item: string) => {
  store.dispatch("customCocktail/addIngredients", item);
  store.dispatch("customCocktail/resetSearchIngredients");
  customCocktailSearchValue.value = "";
};
</script>

<style scoped lang="scss">
.custom-cocktail-search {
  @include flex(column);
  gap: 16px;

  label {
    @include font($fs-md, $fw-medium);
    color: $label-color;
    span {
      @include font($fs-md, $fw-bold);
      color: $danger-color;
    }
  }
  .custom-cocktail-search-ingredients {
    @include flex-xy(flex-start, center);
    flex-wrap: wrap;
    gap: 4px;

    div {
      position: relative;
      padding: 0.5em 1.2em;
      margin: 0 5px;
      border-radius: 10px;
      @include font($fs-md, $fw-regular);
      background-color: $navy500;
      color: $white;
      span {
        position: absolute;
        top: -4px;
        right: -4px;
        padding: 8px;
        color: $white;
        @include font($fs-sm, $fw-bold);
        cursor: pointer;
      }
    }
  }
  .custom-cocktail-search-list {
    @include flex-xy(flex-start, center);
    flex-wrap: wrap;
    gap: 8px;
    margin: 0px 8px;
    @include font(13px, $fw-regular);

    .custom-cocktail-search-item {
      cursor: pointer;
    }
  }
}
</style>
