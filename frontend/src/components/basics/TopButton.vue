<template>
  <div class="top-button" v-show="goTopBtnStatus" @click="goToTop">
    <span class="material-icons icon-top"> keyboard_double_arrow_up </span>
  </div>
</template>

<script setup lang="ts">
import { ref, onBeforeMount } from "vue";
import { useStore } from "vuex";
const store = useStore();

const goToTop = () => {
  store.dispatch("scroll/goToTop");
};

onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
});

let debounce: ReturnType<typeof setTimeout>;
let lastScrollTop = 0;
const goTopBtnStatus = ref(0);
const handleScroll = (event: Event) => {
  if (debounce) {
    clearTimeout(debounce);
  }
  debounce = setTimeout(() => {
    console.log("hello");
    const top = document.documentElement.scrollTop;
    const st = window.pageYOffset || document.documentElement.scrollTop;
    // scroll up
    if (top > 1500 && st < lastScrollTop) {
      goTopBtnStatus.value = 1;
    } else {
      goTopBtnStatus.value = 0;
    }
    lastScrollTop = st <= 0 ? 0 : st;
  }, 30);
};
</script>

<style scoped lang="scss">
.top-button {
  @include flex-center;
  width: 50px;
  height: 50px;
  position: fixed;
  bottom: 70px;

  &:hover {
    transition: all 0.2s ease-in-out;
  }

  .icon-top {
    @include font(50px);
    opacity: 0.1;

    &:hover {
      @include font-size-primary(55px);
      opacity: 0.25;
      transition: 0.25s;
    }
  }
}
</style>
