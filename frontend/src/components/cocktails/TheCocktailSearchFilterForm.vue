<!-- filter: form -->
<template>
  <form @submit.prevent="submitSearchFilterForm">
    <div>
      <div class="cocktail-search-filter-radio">
        <!-- 알코올, 논 알코올 여부 -->
        <label for="filter-alcohol">
          알콜
          <div>
            <span class="material-icons" v-show="statusAlcohol"> done </span>
          </div>
        </label>
        <label for="filter-none-alcohol">
          무 알콜
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
    <!-- 재료 선택 -->
    <div class="cocktail-search-filter-ingredients">
      <p>재료</p>
      <div class="cocktail-search-filter-category">
        <div
          v-for="(image, index) in images"
          :key="`image-${index}`"
          @click="clickIngredient(image.params)"
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
      :button-style="[buttonColor, 'long', '']"
    >
      {{ searchFilterResultCnt }}개의 검색 결과
    </button-basic-round>
  </form>
</template>

<script setup lang="ts">
import TheCocktailSearchFilterSlider from "@/components/cocktails/TheCocktailSearchFilterSlider.vue";
import ButtonBasicRound from "@/components/basics/ButtonBasicRound.vue";
import { reactive, ref, computed, onMounted, watch } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
const store = useStore();
const router = useRouter();

// 알코올 / 논 알코올 value
const trueValue = ref(1);
const falseValue = ref(0);
const statusAlcoholValue = computed(
  () => store.getters["cocktailSearch/getSearchFilterAlcohol"]
);
const statusAlcohol = ref(statusAlcoholValue.value);
watch(statusAlcohol, () => {
  store.dispatch("cocktailSearch/changeFilterAlcohol", statusAlcohol.value);
});

// 개수
const searchFilterResultCnt = computed(
  () => store.getters["cocktailSearch/getFilterResultCnt"]
);

// 멀티 레인지 슬라이더 value
// const strengthValue = computed(
//   () => store.getters["cocktailSearch/getSearchFilterAlcoholStrength"]
// );
// const leftValue = ref(strengthValue.value[0]);
// const rightValue = ref(strengthValue.value[1]);

// let debounce: any;
// watch([leftValue, rightValue], () => {
//   clearTimeout(debounce);
//   debounce = setTimeout(
//     () =>
//       store.dispatch("cocktailSearch/changeFilterAlcoholStrength", [
//         leftValue.value,
//         rightValue.value,
//       ]),
//     200
//   );
// });

// 재료 선택
interface ingredients {
  params: string;
  image: string;
  name: string;
}
const choiceIngredients = computed(
  () => store.getters["cocktailSearch/getSearchFilterIngredients"]
);

// 이미지
const images: ingredients[] = [
  {
    params: "alcohol",
    image:
      "url(https://www.thecocktaildb.com/images/ingredients/Gin-Small.png)",
    name: "술",
  },
  {
    params: "liqueur",
    image:
      "url(https://www.thecocktaildb.com/images/ingredients/Kahlua-Small.png)",
    name: "리큐르",
  },
  {
    params: "drinks",
    image:
      "url(https://www.thecocktaildb.com/images/ingredients/Ginger%20Ale-Small.png)",
    name: "음료수",
  },
  {
    params: "ingredients",
    image:
      "url(https://www.thecocktaildb.com/images/ingredients/lemon-Small.png)",
    name: "추가 재료",
  },
];

// 카테고리 클릭
const clickIngredient = (params: string) => {
  store.dispatch("cocktailSearch/changeFilterClass", "none");
  router.push({
    name: "TheCocktailFilterCategoryView",
    params: {
      category: params,
    },
  });
};

// 버튼 색상
const buttonColor = computed(() => {
  if (searchFilterResultCnt.value) {
    return "light-primary";
  } else {
    return "sub";
  }
});

// 제출
const submitSearchFilterForm = () => {};

onMounted(() => {
  store.dispatch("cocktailSearch/searchFilterResultCnt");
});
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
      cursor: pointer;
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
  border-radius: 1em;
  padding: 10px;
  @include font(13px, $fw-bold);

  @media (min-height: 750px) {
    margin-top: 16px;
    padding: 12px;
    @include font($fs-md, $fw-bold);
  }
}
</style>
