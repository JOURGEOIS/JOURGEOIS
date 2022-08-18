<template>
  <label for="community-image-input">
    <p>
      이미지
      <span>*</span>
    </p>
    <div :style="{ backgroundImage: `url(${imageUrl})` }">
      <div class="community-image-input-desc" v-if="!imageUrl">
        <span class="material-icons">add_photo_alternate</span>
        <p>이미지를 삽입해주세요</p>
      </div>
    </div>
  </label>
  <input
    type="file"
    id="community-image-input"
    accept="image/*"
    @change="changeCommunityImage"
  />
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";
import {
  checkImageSize,
  compressorImage,
  checkImageExtension,
} from "../../functions/image";
const store = useStore();

// 이미지 연결
const imageUrl = computed(() => store.getters["feedDescInfo/getImgLink"]);

// 이미지 input
const emit = defineEmits<{
  (event: "changeImage", value: object): void;
}>();

const changeCommunityImage = (event: Event) => {
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
        store.dispatch("feedDescInfo/uploadImage", data);
        emit("changeImage", data.img);
        return;
      }
    });

    // 이미지 용량이 5mb 이하면 그냥 업로드 한다.
  } else {
    const data = {
      img: file,
    };
    store.dispatch("feedDescInfo/uploadImage", data);
    emit("changeImage", data.img);
    return;
  }
};
</script>

<style scoped lang="scss">
label[for="community-image-input"] {
  @include flex(column);
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
    width: calc(100% + 16px);
    aspect-ratio: 1/1;
    margin: 0 -8px;
    background-color: $white150;
    border-radius: 10px;
    background-color: $white150;
    background : {
      size: cover;
      position: center center;
    }

    .community-image-input-desc {
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
