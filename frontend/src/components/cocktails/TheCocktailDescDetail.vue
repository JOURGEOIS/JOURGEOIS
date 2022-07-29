<template>
  <div class="cocktail-desc-detail">
    <div class="cocktail-desc-detail-title">
      <p>마가리타마가리타</p>
      <p>Magarita</p>
    </div>
    <round-image :round-image="cocktailImage"></round-image>
    <div class="cocktail-desc-tag">
      <filled-tag
        :tagStyle="['light-primary', '12px', '5px']"
        v-for="(tag, index) in tags"
        :key="index"
      >
        {{ tag }}
      </filled-tag>
    </div>
    <div class="cocktail-desc-detail-content">
      <div>
        <span class="material-icons-outlined"> bookmarks </span>
        <p>북마크</p>
        <p>999+</p>
      </div>
      <div>
        <span class="material-icons-outlined"> local_bar </span>
        <p>베이스 술</p>
        <p>데킬라</p>
      </div>
      <div>
        <span class="material-icons-outlined"> bubble_chart </span>
        <p>도수</p>
        <p>무알콜</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import FilledTag from "@/components/basics/FilledTag.vue";
import RoundImage from "@/components/basics/RoundImage.vue";

const cocktailImage = {
  // image:
  width: "120px",
};

const tags = ["Alcoholic", "IBA", "OrdinaryDrink", "Dairy"];
</script>

<style scoped lang="scss">
.cocktail-desc-detail {
  @include flex(column);
  align-items: center;
  justify-content: center;
  gap: 24px;
  width: 100%;
  .cocktail-desc-detail-title {
    @include flex(column);
    align-items: center;
    justify-content: center;
    gap: 8px;
    p {
      &:first-child {
        @include font($fs-xl, $fw-medium);
      }
      &:last-child {
        @include font($fs-md, $fw-medium);
      }
    }
  }
  .cocktail-desc-tag {
    @include flex-center;
    gap: 6px;
    margin-bottom: 16px;
  }

  .cocktail-desc-detail-content {
    @include flex-center;
    gap: 48px;

    div {
      @include flex(column);
      align-items: center;

      span {
        color: $navy500;
        font-size: $fs-xxl;
        margin-bottom: 8px;
      }

      p {
        &:nth-child(2) {
          @include font($fs-md, $fw-regular);
          color: $navy500;
        }

        &:last-child {
          @include font($fs-main, $fw-bold);
          color: $navy700;
        }
      }
    }
  }
}
</style>
