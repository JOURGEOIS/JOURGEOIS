<template>
  <div class="custom-cocktail-textarea">
    <label for="custom-cocktail-textarea-input"> 칵테일 설명</label>
    <textarea
      id="custom-cocktail-textarea-input"
      cols="10"
      rows="5"
      maxlength="500"
      placeholder="광고 및 욕설, 비속어나 타인을 비방하는 문구를 사용하면 비공개 될 수 있습니다"
      @input="emitValue"
    ></textarea>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

// textarea의 value emit하기
const emit = defineEmits<{
  (event: "update:modelValue", value: string): void;
}>();
const emitValue = (event: Event) => {
  emit("update:modelValue", (event.target as HTMLInputElement).value);
};
</script>

<style scoped lang="scss">
.custom-cocktail-textarea {
  @include flex(column);
  gap: 16px;

  label {
    @include font($fs-md, $fw-medium);
    color: $label-color;
  }
  textarea {
    background-color: $white150;
    border: 0;
    border-radius: 10px;
    padding: 16px 24px;
    font-family: $ff-main;
    resize: none;

    &:focus {
      outline: none;
    }

    &::placeholder {
      @include font($fs-md, $fw-medium);
      font-family: $ff-main;
      color: $white400;
    }
  }
}
</style>
