<template>
  <form>
    <div>
      <p class="cocktail-search-filter-alcohol">도수</p>
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
</script>

<style scoped lang="scss">
form {
  @include flex(column);
  align-items: center;
  gap: 24px;

  > div {
    @include flex(column);
    gap: 16px;
    width: 100%;

    > p {
      @include font($fs-main, $fw-medium);
      color: $main-color;
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
