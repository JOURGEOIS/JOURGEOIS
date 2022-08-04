<template>
  <label for="custom-cocktail-image-input">
    <p>칵테일 이미지 <span> *</span></p>
    <div>
      <div class="custom-cocktail-image-input-desc">
        <span class="material-icons"> add_photo_alternate </span>
        <p>이미지를 삽입해주세요</p>
      </div>
    </div>
  </label>
  <input
    type="file"
    id="custom-cocktail-image-input"
    accept="image/*"
    @change="changeCustomCocktailImage"
  />
</template>

<script setup lang="ts">
import { ref } from "vue";

// 이미지 input
const changeCustomCocktailImage = (event: Event) => {
  const data = {
    imageFile: (event?.target as HTMLInputElement).files![0],
  };
};
</script>

<style scoped lang="scss">
label[for="custom-cocktail-image-input"] {
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
  > div {
    position: relative;
    width: 100%;
    padding-bottom: 100%;
    background-color: $white150;

    .custom-cocktail-image-input-desc {
      @include flex-center;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      @include font($fs-main, $fw-regular);
      color: $white400;
    }
  }
}

input {
  display: none;
}
</style>
