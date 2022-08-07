<!-- 
  부모에서 보내는 props => prev, success, formId
  -------------------------------------------------------------
  prev가 true이면 이전으로 가기 버튼이 보인다. 
  prev가 false이면 이전으로 가기 버튼이 보이지 않는다. 
  success가 true이면 완료 버튼이 보인다.
  success가 false이면 완료 버튼이 보이지 않는다.
  formIds는 success 버튼을 클릭하면 보내는 form의 id이다.  
-->

<template>
  <header>
    <span
      class="material-icons back-icon"
      :style="{ visibility: iconVisibility }"
      @click="clicked"
    >
      arrow_back_ios_new
    </span>
    <div class="header-content"><slot></slot></div>
    <button
      class="header-success"
      :style="{ visibility: successVisibility }"
      type="submit"
      :form="formId"
    >
      완료
    </button>
  </header>
  <hr />
</template>

<script setup lang="ts">
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { computed } from "vue";

const props = defineProps<{
  prev?: boolean;
  success?: boolean;
  formId?: string;
}>();

const emit = defineEmits<{
  (e: "prevClicked"): void;
  (e: "successClicked"): void;
}>();

const clicked = () => {
  emit("prevClicked");
};

const submitted = () => {
  emit("successClicked");
};

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
  width: 100%;
  margin-top: 10px;

  @media #{$tablet} {
    margin-top: 16px;
  }
}

.material-icons {
  padding: 0.5em 1em 0.5em 0;
  @include font($fs-title, $fw-thin);

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
}

.header-success {
  width: fit-content;
  padding: 0.5em 0 0.5em 0.5em;
  letter-spacing: $ls-main;
  @include font($fs-main, $fw-medium);
  background-color: white;

  @media #{$tablet} {
    font-size: $fs-title;
  }

  &:hover {
    cursor: pointer;
  }
}

.back-icon {
  user-select: none;
}

hr {
  @include hr;
  width: 100vw;
  margin: 10px 0;

  @media #{$tablet} {
    margin: 16px 0;
  }
}
</style>
