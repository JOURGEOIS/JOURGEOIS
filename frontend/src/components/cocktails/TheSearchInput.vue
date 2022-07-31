<!--
  부모에서 보내는 props => data
  -------------------------------------------------
  data: input에 대한 정보를 담은 객체
    - button: (boolean) x버튼의 유무를 선택한다. 
    - id: (string) 해당 input을 식별할 수 있는 유니크한 id값이다.
    - placeholder: (string) placeholder에 들어가는 값. 없을 경우 ""로 표기
    - maxlength(number): input maxLength
-->

<template>
  <div class="input-container">
    <span class="material-icons search-icons"> search </span>
    <div class="input-section">
      <input
        type="text"
        :id="data.id"
        :disabled="data.isDisabled"
        :value="modelValue"
        :placeholder="data.placeholder"
        :maxlength="data.maxlength"
        @input="emitValue"
        autocapitalize="off"
        autocomplete="off"
      />
    </div>
    <span
      class="material-icons activate-icon"
      @click="clickCloseIconInner"
      v-if="modelValue"
    >
      close
    </span>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";

// props.data의 타입을 정의한 인터페이스
export interface dataObject {
  button: boolean;
  id: string;
  placeholder: string;
  type: string;
  maxlength: number;
  isDisabled: boolean;
}

const props = defineProps<{
  data: dataObject;
  modelValue: string;
}>();

// props.data.button의 값에 따라 x버튼이 보이는지 안보이는지 설정할 수 있다.
const buttonVisibility = computed(() => {
  if (props.data.button) {
    return "visible";
  } else {
    return "hidden";
  }
});

// input.value emit하기
const emit = defineEmits<{
  (event: "update:modelValue", value: string): void;
  (event: "clickCloseIcon"): void;
}>();

// 값이 변할 때 emit
const emitValue = (event: Event) => {
  emit("update:modelValue", (event.target as HTMLInputElement).value);
};

// 닫기 버튼 누르면 emit
const clickCloseIconInner = (event: Event) => {
  emit("clickCloseIcon");
};
</script>

<style scoped lang="scss">
.input-container {
  @include flex-xy(space-between, center);
  padding: 0 15px;
  border-radius: 100em;
  gap: 5px;
  background-color: $white150;
  width: 100%;

  .input-section {
    @include flex-xy(space-between, center);
    flex-grow: 1;

    input {
      @include font($fs-md, $fw-medium);
      width: 100%;
      height: 40px;
      background-color: $white150;
      border: none;
      color: $main-color;

      &:focus {
        outline: none;
      }

      &::placeholder {
        @include font($fs-md, $fw-medium);
        color: $white350;
      }
    }

    .activate-icon {
      color: $main-color;
    }
  }
}

.search-icons {
  color: $placeholder-color;
  font-size: $fs-xl;
}
</style>
