<!-- filter: form에 사용될 멀티 range 슬라이더
  자세한 원리는 노션 및 블로그에 정리되어 있다. 
-->
<template>
  <div class="cocktail-search-filter-slider-container">
    <!-- slider value -->
    <div class="cocktail-search-filter-slider-value">
      <div class="filter-slider-left-value" :style="leftThumbStyle">
        {{ sliderLeft }}
      </div>
      <div class="filter-slider-right-value" :style="rightThumbStyle">
        {{ sliderRight }}
      </div>
    </div>
    <div class="cocktail-search-filter-slider-input">
      <!-- 진짜 슬라이더 -->
      <input
        type="range"
        id="slider-left"
        max="30"
        min="0"
        v-model.number="sliderLeftValue"
      />
      <input
        type="range"
        id="slider-right"
        max="30"
        min="0"
        v-model.number="sliderRightValue"
      />

      <!-- 보이는 슬라이더 -->
      <div class="filter-slider">
        <div class="filter-slider-line"></div>
        <div class="filter-slider-range" :style="rangeStyle"></div>
        <div class="filter-slider-left" :style="leftThumbStyle"></div>
        <div class="filter-slider-right" :style="rightThumbStyle"></div>
      </div>
    </div>

    <!--슬라이더 범위  -->
    <div class="cocktail-search-filter-slider-desc">
      <p>0도</p>
      <p>30도 이상</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, watch, computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

// slider Value
const sliderValue = computed(
  () => store.getters["cocktailSearch/getSearchFilterAlcoholStrength"]
);
const sliderLeftValue = ref(sliderValue.value[0]);
const sliderRightValue = ref(sliderValue.value[1]);

// 슬라이더 값이 변경될 때, 값을 저장한다. (디바운스 사용하여 요청을 적당히...ㅎㅎ)
let debounce: ReturnType<typeof setTimeout>;
watch([sliderLeftValue, sliderRightValue], () => {
  if (debounce) {
    clearTimeout(debounce);
  }
  debounce = setTimeout(
    () =>
      store.dispatch("cocktailSearch/changeFilterAlcoholStrength", [
        sliderLeftValue.value,
        sliderRightValue.value,
      ]),
    200
  );
});

// 가짜 Slider 위치 계산
const leftThumbStyle = reactive({
  left: `${(sliderLeftValue.value / 30) * 100}%`,
});

const rightThumbStyle = reactive({
  left: `${(sliderRightValue.value / 30) * 100}%`,
});

const rangeStyle = reactive({
  left: `${(sliderLeftValue.value / 30) * 100}%`,
  width: `${((sliderRightValue.value - sliderLeftValue.value) / 30) * 100}%`,
});

// 보여지는 Slider Value로 한 자리 수면 앞에 0을 붙인다.
const sliderLeft = computed(() => {
  if (String(sliderLeftValue.value).length === 1) {
    return "0" + sliderLeftValue.value;
  } else {
    return sliderLeftValue.value;
  }
});

const sliderRight = computed(() => {
  if (String(sliderRightValue.value).length === 1) {
    return "0" + sliderRightValue.value;
  } else {
    return sliderRightValue.value;
  }
});

// 왼쪽 Slider Value값이 변할 때 실행된다.
watch(sliderLeftValue, () => {
  //맥시멈
  if (sliderLeftValue.value > 27) {
    sliderLeftValue.value = 27;
  }

  // 범위 제한
  if (sliderRightValue.value <= sliderLeftValue.value + 3) {
    sliderLeftValue.value = sliderRightValue.value - 3;
  }

  // 값 변경
  leftThumbStyle.left = `${(sliderLeftValue.value / 30) * 100}%`;
  rangeStyle.left = `${(sliderLeftValue.value / 30) * 100}%`;
  rangeStyle.width = `${
    ((sliderRightValue.value - sliderLeftValue.value) / 30) * 100
  }%`;
});

// 오른쪽 Slider Value값이 변할 때 실행된다.
watch(sliderRightValue, () => {
  // 미니멈
  if (sliderRightValue.value < 3) {
    sliderRightValue.value = 3;
  }

  // 범위 제한
  if (sliderRightValue.value - 3 <= sliderLeftValue.value) {
    sliderRightValue.value = sliderLeftValue.value + 3;
  }

  // 값 변경
  rightThumbStyle.left = `${(sliderRightValue.value / 30) * 100}%`;
  rangeStyle.width = `${
    ((sliderRightValue.value - sliderLeftValue.value) / 30) * 100
  }%`;
});
</script>

<style scoped lang="scss">
.cocktail-search-filter-slider-container {
  @include flex(column);
  gap: 32px;
  width: 100%;
  .cocktail-search-filter-slider-input {
    @include flex(column);
    position: relative;
    width: 95%;
    input {
      position: absolute;
      z-index: 10;
      width: 100%;
      height: 8px;
      background-color: black;
      opacity: 0;
      appearance: none;
      pointer-events: none;

      // 크롬
      &::-webkit-slider-thumb {
        z-index: 100;
        width: 32px;
        height: 32px;
        background-color: black;
        cursor: pointer;
        appearance: none;
        pointer-events: all;
      }

      // 파이어폭스
      &::-moz-range-thumb {
        z-index: 100;
        width: 32px;
        height: 32px;
        background-color: black;
        cursor: pointer;
        appearance: none;
        pointer-events: all;
      }
    }

    .filter-slider {
      position: absolute;
      width: 100%;

      .filter-slider-line {
        position: absolute;
        width: 100%;
        height: 8px;
        border-radius: 20px;
        background-color: $white250;
      }

      .filter-slider-range {
        position: absolute;
        height: 8px;
        background-color: $primary300;
      }

      .filter-slider-left,
      .filter-slider-right {
        position: absolute;
        width: 16px;
        height: 16px;
        margin-top: -4px;
        border-radius: 50%;
        background-color: $primary-color;
      }
    }
  }

  // 값
  .cocktail-search-filter-slider-value {
    position: relative;
    width: 95.5%;
    @include font($fs-md, $fw-medium);

    div {
      position: absolute;
    }
  }

  // 범위
  .cocktail-search-filter-slider-desc {
    @include flex-xy(space-between, center);
    width: 100%;
    margin-top: -16px;
    @include font($fs-md, $fw-medium);
    color: $sub-color;
    user-select: none;
  }
}
</style>
