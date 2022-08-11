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
import {
  checkImageSize,
  compressorImage,
  checkImageExtension,
} from "../../functions/image";
const store = useStore();

// 이미지 연결
const imageUrl = computed(() => store.getters["customCocktail/getImgLink"]);

// 이미지 input
const emit = defineEmits<{
  (event: "changeImage", value: object): void;
}>();

// 이미지 함수
const changeCustomCocktailImage = (event: Event) => {
  let file = (event.target as HTMLInputElement).files![0];

  // 1. 파일을 업로드 하지 않고 취소 버튼을 누르는 경우
  // 2. 이미지 확장자가 아닌 경우
  // 바로 return 한다.
  if (!file) {
    return;
  }

  if (!checkImageExtension(file.name)) {
    store.dispatch(
      "modal/changeErrorModalMessage",
      "올바른 이미지 파일을 업로드 하세요."
    );
    store.dispatch("modal/blinkErrorModalAppStatus", true);
    return;
  }

  // 이미지 용량이 5mb초과면 compressor를 진행한다.
  if (!checkImageSize({ max: 5, fileSize: file.size })) {
    compressorImage(file).then((result) => {
      if (!checkImageSize({ max: 10, fileSize: result.size })) {
        store.dispatch(
          "modal/changeErrorModalMessage",
          "이미지가 너무 큽니다."
        );
        store.dispatch("modal/blinkErrorModalAppStatus");
        return;
      } else {
        const data = {
          img: result,
        };
        store.dispatch("customCocktail/uploadImage", data);
        emit("changeImage", data.img);
        return;
      }
    });

    // 이미지 용량이 5mb 이하면 그냥 업로드 한다.
  } else {
    const data = {
      img: file,
    };
    store.dispatch("customCocktail/uploadImage", data);
    emit("changeImage", data.img);
    return;
  }
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
