<!--
  부모에서 보내는 props => data, textareaStyle
  -------------------------------------------------
  data: textarea에 대한 정보를 담은 객체
    - button: (boolean) x버튼의 유무를 선택한다. 
    - placeholder: (string) placeholder에 들어가는 값. 없을 경우 ""로 표기
    - type(string): textarea 타입
    - maxlength(number): textarea maxLength
  textareaStyle: normal과 error로 나뉘며 normal일 경우, 평범한 textarea을 가져온다. error일 경우,빨간색 textarea을 가져온다. 
-->

<template>
  <div class="textarea-container">
    <textarea
      :type="data.type"
      :class="textareaColor"
      :value="modelValue"
      :placeholder="data.placeholder"
      :maxlength="data.maxlength"
      @input="emitValue"
      autocapitalize="off"
      autocomplete="off"
    ></textarea>
    <div
      class="textarea-icon"
      :style="{ visibility: buttonVisibility }"
      v-if="!data.isDisabled"
      @click="$emit('update:modelValue', '')"
    >
      <span class="material-icons">close</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'

// props.data의 타입을 정의한 인터페이스
export interface dataObject {
  button: boolean
  placeholder: string
  type: string
  maxlength: number
  isDisabled: boolean
}

const props = defineProps<{
  data: dataObject
  textareaStyle: string
  modelValue: string
}>()

// props.data.button의 값에 따라 x버튼이 보이는지 안보이는지 설정할 수 있다.
const buttonVisibility = computed(() => {
  if (props.data.button) {
    return 'visible'
  } else {
    return 'hidden'
  }
})

const textareaColor = computed(() => {
  if (props.textareaStyle === 'normal') {
    return 'normal-textarea'
  } else {
    return 'error-textarea'
  }
})

// textarea.value emit하기
const emit = defineEmits<{
  (event: 'update:modelValue', value: string): void
}>()
const emitValue = (event: Event) => {
  emit('update:modelValue', (event.target as HTMLInputElement).value)
}
</script>

<style scoped lang="scss">
.textarea-container {
  @include flex-xy(flex-start, center);
  position: relative;
  width: 100%;
}

textarea {
  @include font($fs-sm, $fw-medium);
  font-family: 'Noto Sans KR', sans-serif;

  flex-grow: 1;
  width: 100%;
  height: 66px;
  background-color: $white;
  color: $main-color;
  border-radius: 5px;
  padding: 10px;
  resize: none;

  &:focus {
    outline: none;
  }

  &::placeholder {
    @include font($fs-sm, $fw-medium);
    font-family: 'Noto Sans KR', sans-serif;
    color: $placeholder-color;
  }
}

.error-textarea {
  border: 2px solid $danger-color;
}

.normal-textarea {
  border: 2px solid $white250;
  transition: 0.3s;

  &:focus {
    border: 2px solid $main-color;
  }
}

.textarea-icon {
  @include p-component(xs);
  position: absolute;
  right: 0px;

  &:hover {
    cursor: pointer;
  }
}

.material-icons {
  border-radius: 50em;
  background-color: $white300;
  color: $white100;
  font-size: $fs-lg;
}
</style>
