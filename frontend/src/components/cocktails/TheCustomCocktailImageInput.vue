<template>
  <label for="custom-cocktail-image-input">
    <p>칵테일 이미지 <span> *</span></p>
    <div :style="{ backgroundImage: `url(${imageUrl})` }">
      <div class="custom-cocktail-image-input-desc" v-if="!imageUrl">
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
import { ref, computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

// 이미지 연결
const imageUrl = computed(() => store.getters["customCocktail/getImgLink"]);

// 이미지 input
const emit = defineEmits<{
  (event: "changeImage", value: object): void;
}>();

const changeCustomCocktailImage = (event: Event) => {
  // 취소 버튼 누르는 경우 바로 return
  if (!(event?.target as HTMLInputElement).files![0]) {
    return;
  }
  const data = {
    img: (event?.target as HTMLInputElement).files![0],
  };
  store.dispatch("customCocktail/uploadImage", data);
  emit("changeImage", data.img);
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
    width: calc(100% + 32px);
    margin: 10px -16px;
    padding-bottom: 100%;
    background-color: $white150;
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
    aspect-ratio: 1/1;

    .custom-cocktail-image-input-desc {
      @include flex-center;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      @include font(16px, $fw-regular);
      color: $white400;
    }
  }
}

input {
  display: none;
}
</style>
