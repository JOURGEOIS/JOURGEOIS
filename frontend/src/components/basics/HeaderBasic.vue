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
  <header class="header-container">
    <div class="header-container-div">
      <span class="material-icons back-icon" v-if="prev" @click="clicked">
        arrow_back_ios_new
      </span>
      <div class="header-content"><slot></slot></div>
      <button
        v-if="success"
        class="header-success"
        type="submit"
        :form="formId"
      >
        완료
      </button>
    </div>
  </header>
</template>

<script setup lang="ts">
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
  if (props.success) {
    return;
  } else {
    return "hidden";
  }
});

const successVisibility = computed(() => {
  if (props.prev) {
    return true;
  } else {
    return false;
  }
});
</script>

<style scoped lang="scss">
.header-container {
  @include flex-center;
  @include shadow-feed;
  flex-wrap: wrap;
  position: fixed;
  height: 74px;
  background-color: $white !important;
  z-index: 5;

  .header-container-div {
    @include flex-center;
    position: relative;
    width: 100vw;
    padding: 12px 16px;

    @media #{$tablet} {
      padding: 12px 15%;
    }
    @media #{$pc} {
      padding: 12px 20%;
    }

    .material-icons {
      padding: 0;
      align-self: center;
      margin-top: 5px;
      @include font($fs-title, $fw-thin);

      @media #{$tablet} {
        font-size: $fs-xl;
        margin-top: 5px;
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
      flex-grow: 1;

      @media #{$tablet} {
        font-size: $fs-xl;
      }
    }

    .header-success {
      width: fit-content;
      padding: 0;
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
      position: absolute;
      top: 50%;
      left: 16px;
      transform: translate(0, -55%);
      user-select: none;

      @media #{$tablet} {
        left: 15%;
      }
      @media #{$pc} {
        left: 20%;
      }
    }
  }
}
</style>
