<template>
  <div class="label-container">
    <label :for="data.id"> {{ data.label }}</label>
    <div class="input-container">
      <input
        class="normal-input"
        type="text"
        v-model="inputValue"
        :class="inputColor"
        :id="data.id"
        :placeholder="data.placeholder"
      />
      <div
        class="input-icon"
        :style="{ visibility: buttonVisibility }"
        @click="clickXButton"
      >
        <span class="material-icons"> close </span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";

// props.data의 타입을 정의한 인터페이스
export interface dataObject {
  button: boolean;
  id: string;
  label: string;
  placeholder: string;
}

const props = defineProps<{
  data: dataObject;
  inputStyle: string;
}>();

// props.data.button의 값에 따라 x버튼이 보이는지 안보이는지 설정할 수 있다.
const buttonVisibility = computed(() => {
  if (props.data.button) {
    return "visible";
  } else {
    return "hidden";
  }
});

const inputColor = computed(() => {
  if (props.inputStyle === "normal") {
    return "normal-input";
  } else {
    return "error-input";
  }
});

// 버튼을 클릭할 시, input이 리셋된다.
let inputValue = ref("");

const clickXButton = () => {
  inputValue.value = "";
};
</script>

<style scoped lang="scss">
label-container {
  width: calc(100% - 32px);
}

label {
  color: $label-color;
}

.input-container {
  @include flex-xy(flex-start, center);
  width: 100%;
  position: relative;
}

input {
  @include font($fs-md, $fw-medium);
  background-color: $white;
  flex-grow: 1;
  border: none;
  height: 40px;
  color: $main-color;

  &:focus {
    outline: none;
  }

  &::placeholder {
    @include font($fs-md, $fw-medium);
    color: $placeholder-color;
  }
}

.error-input {
  border-bottom: 2px solid $danger-color;
}

.normal-input {
  border-bottom: 2px solid $white250;
  transition: 0.3s;

  &:focus {
    border-bottom: 2px solid $main-color;
  }
}

.input-icon {
  @include p-component(xs);
  position: absolute;
  right: 0px;
  &:hover {
    cursor: pointer;
  }
}

.material-icons {
  font-size: $fs-lg;
  color: $white100;
  background-color: $white300;
  border-radius: 50em;
}
</style>
