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
    <div>
      <span class="material-icons back-icon" v-if="prev" @click="clicked">
        arrow_back_ios_new
      </span>
      <div class="header-content"><slot></slot></div>
      <span class="material-icons-outlined" v-if="setting" @click="clickSet">
        settings
      </span>
    </div>
  </header>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

const props = defineProps<{
  prev: boolean;
  setting: boolean;
}>();

const emit = defineEmits<{
  (e: "prevClicked"): void;
}>();

const clicked = () => {
  emit("prevClicked");
};

const clickSet = () => {
  store.dispatch("settings/changeSettingsModalClass", "start");
  store.dispatch("settings/toggleSettingsModal", true);
};
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

  > div {
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
    .material-icons-outlined {
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
    .back-icon {
      position: absolute;
      top: 50%;
      left: 16px;
      transform: translate(0, -55%);
      user-select: none;
    }
  }
}
</style>
