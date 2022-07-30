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
      <p>30도</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, watch, computed } from "vue";

// slider Value
const sliderLeftValue = ref(6);
const sliderRightValue = ref(15);

// 가짜 Slider 위치 계산
const leftThumbStyle = reactive({
  left: "20%",
});

const rightThumbStyle = reactive({
  left: "50%",
});

const rangeStyle = reactive({
  left: "20%",
  width: "30%",
});

// SliderValue
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
  // 범위 제한
  if (sliderRightValue.value <= sliderLeftValue.value) {
    sliderLeftValue.value = sliderRightValue.value - 3;
  }

  // 값 변경
  leftThumbStyle.left = `${(sliderLeftValue.value / 30) * 100}%`;
  rangeStyle.left = `${(sliderLeftValue.value / 30) * 100}%`;
  rangeStyle.width = `${
    ((sliderRightValue.value - sliderLeftValue.value) / 30) * 100
  }%`;

  console.log(sliderLeftValue.value);
});

// 오른쪽 Slider Value값이 변할 때 실행된다.
watch(sliderRightValue, () => {
  // 범위 제한
  if (sliderRightValue.value <= sliderLeftValue.value) {
    sliderRightValue.value = sliderLeftValue.value + 3;
  }

  // 값 변경
  rightThumbStyle.left = `${(sliderRightValue.value / 30) * 100}%`;
  rangeStyle.width = `${
    ((sliderRightValue.value - sliderLeftValue.value) / 30) * 100
  }%`;

  console.log(sliderRightValue.value);
});
</script>

<style scoped lang="scss">
.cocktail-search-filter-slider-container {
  @include flex(column);
  gap: 32px;
  align-items: center;
  width: 100%;
  .cocktail-search-filter-slider-input {
    @include flex(column);
    position: relative;
    width: 95%;
    input {
      position: absolute;
      z-index: 1;
      width: 100%;
      height: 8px;
      background-color: black;
      opacity: 0;
      appearance: none;
      pointer-events: none;

      // 크롬
      &::-webkit-slider-thumb {
        z-index: 10;
        width: 32px;
        height: 32px;
        background-color: black;
        cursor: pointer;
        appearance: none;
        pointer-events: all;
      }

      // 파이어폭스
      &::-moz-range-thumb {
        z-index: 10;
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

  // 깂
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
  }
}
</style>
