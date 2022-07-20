<!-- 
  부모에서 보내는 props => prev, success
  -------------------------------------------------------------
  prev가 true이면 이전으로 가기 버튼이 보인다. 
  prev가 false이면 이전으로 가기 버튼이 보이지 않는다. 
  success가 true이면 완료 버튼이 보인다.
  success가 false이면 완료 버튼이 보이지 않는다. 
-->

<template>
  <header>
    <span class="material-icons" :style="{ visibility: iconVisibility }">
      arrow_back_ios_new
    </span>
    <div class="header-content"><slot></slot></div>
    <div class="header-success" :style="{ visibility: successVisibility }">
      완료
    </div>
  </header>
  <hr />
</template>

<script setup lang="ts">
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { computed } from "vue";

const props = defineProps<{
  prev: boolean;
  success: boolean;
}>();

const iconVisibility = computed(() => {
  if (props.prev) {
    return "visible";
  } else {
    return "hidden";
  }
});

const successVisibility = computed(() => {
  if (props.success) {
    return "visible";
  } else {
    return "hidden";
  }
});
</script>

<style scoped lang="scss">
header {
  @include flex-xy(space-between, center);
  padding-top: 1.25em;
}

.material-icons {
  @include font($fs-title, $fw-thin);
  @include p-component(sm);

  @media #{$tablet} {
    font-size: $fs-xl;
  }

  @media #{$pc} {
  }

  &:hover {
    cursor: pointer;
  }
}

.header-content {
  @include font($fs-title, $fw-medium);
  text-align: center;

  @media #{$tablet} {
    font-size: $fs-xl;
  }

  @media #{$pc} {
  }
}

.header-success {
  @include font($fs-main, $fw-medium);
  @include p-component(sm);

  @media #{$tablet} {
    font-size: $fs-title;
  }

  @media #{$pc} {
  }

  &:hover {
    cursor: pointer;
  }
}

hr {
  @include hr;
}
</style>
