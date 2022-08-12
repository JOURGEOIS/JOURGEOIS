<template>
  <div class="the-cocktail-awards-agree">
    <label for="cocktail-awards-agree">
      <div>
        <span class="material-icons" v-if="modelValue"> done </span>
      </div>
      개인정보 수집에 동의합니다.
    </label>
    <input
      class="the-cocktail-awards-input"
      type="checkbox"
      id="cocktail-awards-agree"
      @input="emitValue"
      :value="modelValue"
    />
    <div class="the-cocktail-awards-agree-content">
      <p>
        주류주아는 아래의 목적으로 개인정보를 수집 및 이용하며, 개인정보를
        안전하게 취급하는데 최선을 다합니다.
      </p>
      <p>목적: 컨테스트 접수 확인 및 상품 지급</p>
      <p>항목: 이름, 이메일</p>
      <p>보유 및 이용 기간: 컨테스트 진행 기간</p>
    </div>
  </div>
</template>

<script setup lang="ts">
// textarea value
const props = defineProps<{
  modelValue: boolean;
}>();

// textarea의 value emit하기
const emit = defineEmits<{
  (event: "update:modelValue", value: boolean): void;
}>();

const emitValue = (event: Event) => {
  emit("update:modelValue", (event.target as HTMLInputElement).checked);
};
</script>

<style scoped lang="scss">
.the-cocktail-awards-agree {
  @include flex(column);
  gap: 8px;
  @include font($fs-md, $fw-medium);
  color: $main-color;
  margin-top: 16px;

  label {
    @include flex-xy(flex-start, center);
    gap: 8px;

    div {
      position: relative;
      width: 20px;
      height: 20px;
      border: 2px solid $main-color;
      border-radius: 5px;

      span {
        position: absolute;
        color: $primary-color;
        font-size: $fs-lg;
        font-weight: 800;
        top: -2px;
        left: -2px;
      }
    }
  }
  .the-cocktail-awards-input {
    display: none;
  }

  .the-cocktail-awards-agree-content {
    @include font(13px, $fw-medium);
    color: $sub-color;
  }
}
</style>
