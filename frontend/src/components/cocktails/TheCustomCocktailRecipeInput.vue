<template>
  <form class="custom-cocktail-recipe" @submit.self.prevent="">
    <p>제작 (최대 10개) <span> *</span></p>
    <div
      v-for="(item, index) in customCocktailRecipeList"
      :key="index"
      class="custom-cocktail-recipe-item"
    >
      <div class="custom-cocktail-recipe-title">
        <label :for="`recipe-input-${index}`">{{ index + 1 }}단계 </label>
        <div class="custom-cocktail-recipe-delete" @click="deleteRecipe(index)">
          삭제
        </div>
      </div>
      <input
        type="text"
        :id="`recipe-input-${index}`"
        maxlength="40"
        v-model.trim="customCocktailRecipeList[index]"
      />
    </div>
  </form>
  <div @click="addRecipeStep" class="custom-cocktail-recipe-add">
    <p><span class="material-icons"> add_circle </span> 단계 추가하기</p>
  </div>
</template>

<script setup lang="ts">
import { computed, reactive } from "vue";
import { useStore } from "vuex";
import InputBasic from "@/components/basics/InputBasic.vue";
const store = useStore();

const customCocktailRecipeList = computed(
  () => store.getters["customCocktail/getOriginalCocktailRecipe"]
);

// 단계 추가하기
const addRecipeStep = () => {
  store.dispatch("customCocktail/addRecipeStep");
};

// 레시피 삭제하기
const deleteRecipe = (index: number) => {
  store.dispatch("customCocktail/deleteRecipeStep", index);
};
</script>

<style scoped lang="scss">
.custom-cocktail-recipe {
  @include flex(column);
  gap: 16px;

  > p {
    @include font($fs-md, $fw-medium);
    color: $label-color;
    span {
      @include font($fs-md, $fw-bold);
      color: $danger-color;
    }
  }
  .custom-cocktail-recipe-item {
    @include flex(column);
    gap: 8px;
    margin-bottom: 8px;

    .custom-cocktail-recipe-title {
      @include flex-xy(space-between, baseline);
      gap: 8px;
      @include font($fs-sm, $fw-medium);

      label {
        color: $label-color;
      }
      .custom-cocktail-recipe-delete {
        padding: 0 4px;
        color: $danger-color;
      }
    }

    input {
      height: 40px;
      border: none;
      @include font(14px, $fw-regular);
      border-radius: 10px;
      background-color: $white150;
      color: $main-color;
      padding: 5px 10px;
      transition: 0.3s;

      &:focus {
        outline: 1px solid $main-color;
      }
    }
  }
}
.custom-cocktail-recipe-add {
  @include flex-center;
  margin-top: -8px;

  p {
    @include flex-center;
    gap: 4px;
    @include font($fs-main, $fw-medium);
    span {
      color: $navy500;
    }
  }
}
</style>
