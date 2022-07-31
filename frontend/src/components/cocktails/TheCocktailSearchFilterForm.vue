<!-- filter: form -->
<template>
  <form>
    <div>
      <div class="cocktail-search-filter-radio">
        <!-- 알코올, 논 알코올 여부 -->
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
      <the-cocktail-search-filter-slider
        v-model:left-value="leftValue"
        v-model:right-value="rightValue"
      ></the-cocktail-search-filter-slider>
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
    <!-- 재료 선택 -->
    <div class="cocktail-search-filter-ingredients">
      <p>재료</p>
      <div class="cocktail-search-filter-category">
        <div
          v-for="(image, index) in images"
          :key="`image-${index}`"
          @click="clickIngredient(image.id)"
        >
          <div
            class="category-image"
            :style="{ backgroundImage: image.image }"
          ></div>
          <p>{{ image.name }}</p>
        </div>
      </div>
    </div>
    <button-basic-round
      class="cocktail-search-filter-button"
      :button-style="['unchecked', 'long', '']"
    >
      0개의 검색 결과
    </button-basic-round>
  </form>
</template>

<script setup lang="ts">
import TheCocktailSearchFilterSlider from "@/components/cocktails/TheCocktailSearchFilterSlider.vue";
import ButtonBasicRound from "@/components/basics/ButtonBasicRound.vue";
import { ref } from "vue";
import { useStore } from "vuex";
const store = useStore();

// 알코올 / 논 알코올 value
const statusAlcohol = ref(true);
const trueValue = ref(true);
const falseValue = ref(false);

// 멀티 레인지 슬라이더 value
const leftValue = ref(6);
const rightValue = ref(15);

// 재료 선택
interface ingredients {
  id: number;
  image: string;
  name: string;
}

// 이미지
const images: ingredients[] = [
  {
    id: 0,
    image:
      "url(https://www.thecocktaildb.com/images/ingredients/Gin-Small.png)",
    name: "술",
  },
  {
    id: 1,
    image:
      "url(https://www.thecocktaildb.com/images/ingredients/Kahlua-Small.png)",
    name: "리큐르",
  },
  {
    id: 2,
    image:
      "url(https://www.thecocktaildb.com/images/ingredients/Ginger%20Ale-Small.png)",
    name: "음료수",
  },
  {
    id: 3,
    image:
      "url(https://www.thecocktaildb.com/images/ingredients/lemon-Small.png)",
    name: "추가 재료",
  },
];

// 카테고리 클릭
const clickIngredient = (index: number) => {
};
</script>

<style scoped lang="scss">
form {
  @include flex(column);
  align-items: center;
  gap: 32px;

  > div {
    @include flex(column);
    gap: 24px;
    width: 100%;

    input[type="radio"] {
      display: none;
    }
    > p {
      @include font($fs-md, $fw-medium);
      color: $main-color;

      @media (min-height: 750px) {
        @include font($fs-main, $fw-medium);
      }
    }
    .cocktail-search-filter-radio {
      @include flex;
      gap: 36px;

      label {
        @include flex;
        gap: 16px;
        @include font($fs-md, $fw-medium);

        @media (min-height: 750px) {
          @include font($fs-main, $fw-medium);
        }

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
    .cocktail-search-filter-category {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(25%, auto));
      row-gap: 24px;

      > div {
        @include flex(column);
        align-items: center;
        @include font($fs-md, $fw-medium);
        color: $gray200;

        @media (min-height: 750px) {
          @include font(16px, $fw-medium);
        }

        .category-image {
          width: 72px;
          height: 72px;
          border: 0;
          border-radius: 50%;
          background-color: $white200;
          background-repeat: no-repeat;
          background-position: center;
          background-size: cover;

          @media (min-height: 750px) {
            width: 80px;
            height: 80px;
          }
          @media #{$tablet} {
            width: 102px;
            height: 102px;
          }
        }
      }
    }
  }
}

.cocktail-search-filter-button {
  border-radius: 1000em;
  padding: 8px;
  @include font(13px, $fw-bold);

  @media (min-height: 750px) {
    margin-top: 16px;
    padding: 12px;
    @include font($fs-md, $fw-bold);
  }
}
</style>
