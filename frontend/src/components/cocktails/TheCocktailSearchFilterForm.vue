<template>
  <form>
    <div>
      <div class="cocktail-search-filter-radio">
        <label for="filter-alcohol">
          알코올
          <div>
            <span class="material-icons" v-show="statusAlcohol"> done </span>
          </div>
        </label>
        <label for="filter-none-alcohol">
          논 알코올
          <div>
            <span class="material-icons" v-show="!statusAlcohol"> done </span>
          </div>
        </label>
        <input
          type="radio"
          name="alcohol"
          id="filter-alcohol"
          :value="trueValue"
          v-model="statusAlcohol"
        />
        <input
          type="radio"
          name="alcohol"
          id="filter-none-alcohol"
          :value="falseValue"
          v-model="statusAlcohol"
        />
      </div>
    </div>
    <div v-show="statusAlcohol">
      <p>도수</p>
      <!-- 멀티 레인지 슬라이더 -->
      <the-cocktail-search-filter-slider></the-cocktail-search-filter-slider>
      <div class="class-search-filter-alcohol-info">
        <div>
          <p>
            <span class="material-icons-outlined"> info </span>
            <span>일반적인 소주의 도수는 16.9도예요!</span>
          </p>
          <p>
            <span class="material-icons-outlined"> info </span>
            <span>일반적인 맥주의 도수는 5도예요!</span>
          </p>
        </div>
      </div>
    </div>
    <div>
      <p class="cocktail-search-filter-materials">재료</p>
    </div>
  </form>
</template>

<script setup lang="ts">
import TheCocktailSearchFilterSlider from "@/components/cocktails/TheCocktailSearchFilterSlider.vue";
import { ref } from "vue";

const statusAlcohol = ref(true);
const trueValue = ref(true);
const falseValue = ref(false);
</script>

<style scoped lang="scss">
form {
  @include flex(column);
  align-items: center;
  gap: 40px;

  > div {
    @include flex(column);
    gap: 16px;
    width: 100%;

    input[type="radio"] {
      display: none;
    }
    > p {
      @include font($fs-main, $fw-medium);
      color: $main-color;
    }
    .cocktail-search-filter-radio {
      @include flex;
      gap: 36px;

      label {
        @include flex;
        gap: 16px;
        @include font($fs-main, $fw-medium);

        div {
          @include shadow-modal-2;
          width: 25px;
          height: 25px;
          background-color: $white100;
          border-radius: 5px;

          span {
            color: $primary-color;
            font-weight: 800;
          }
        }
      }
    }
    .class-search-filter-alcohol-info {
      @include flex(column);
      align-items: flex-end;

      div {
        @include flex(column);
        align-items: flex-start;

        p {
          @include flex-xy(center, flex-end);
          @include font($fs-sm, $fw-medium);
          color: $sub-color;

          .material-icons-outlined {
            font-size: 16px;
          }
        }
      }
    }
  }
}
</style>
