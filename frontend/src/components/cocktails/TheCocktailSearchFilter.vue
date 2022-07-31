<template>
  <teleport to="body">
    <div class="cocktail-search-filter">
      <div class="cocktail-search-filter-container" :class="animation">
        <div class="cocktail-search-filter-header">
          <p>재 설정</p>
          <p>필터</p>
          <span class="material-icons" @click="clickXIcon"> close </span>
        </div>
        <hr />
        <the-cocktail-search-filter-form></the-cocktail-search-filter-form>
      </div>
    </div>
  </teleport>
</template>

<script setup lang="ts">
import TheCocktailSearchFilterForm from "@/components/cocktails/TheCocktailSearchFilterForm.vue";
import { computed, ref } from "vue";
import { useStore } from "vuex";
const store = useStore();

// 애니메이션 상태
const animation = ref("start");

// 필터 off
const clickXIcon = () => {
  animation.value = "end";
  setTimeout(() => store.dispatch("cocktailSearch/toggleFilter", false), 490);
};
</script>

<style scoped lang="scss">
@keyframes start {
  to {
    transform: translate3d(0, 0, 0);
  }
  from {
    transform: translate3d(0, 100%, 0);
  }
}

@keyframes end {
  to {
    transform: translate3d(0, 100%, 0);
  }
  from {
    transform: translate3d(0);
  }
}

.cocktail-search-filter {
  @include flex-xy(center, center);
  position: fixed;
  top: 0;
  height: 100vh;
  width: 100vw;
  background-color: rgba(0, 0, 0, 0.7);
  .cocktail-search-filter-container {
    @include flex(column);
    gap: 11px;
    position: absolute;
    bottom: 0px;
    width: 100%;
    height: 98%;
    border-top-left-radius: 30px;
    border-top-right-radius: 30px;
    padding: 0 16px;
    background-color: white;

    @media #{$tablet} {
      width: 64%;
      max-width: 600px;
    }

    @media #{$pc} {
      width: 42%;
      max-width: 1000px;
    }

    @media (min-height: 750px) {
      height: 90%;
    }

    .cocktail-search-filter-header {
      @include flex-xy(space-between, center);
      margin-top: 24px;

      p {
        &:first-child {
          @include font($fs-md, $fw-medium);
          color: $danger-color;
          cursor: pointer;
        }

        &:nth-child(2) {
          @include font($fs-main, $fw-medium);
          color: $main-color;
        }
      }
      .material-icons {
        width: 42.78px;
        text-align: center;
        cursor: pointer;
      }
    }

    hr {
      width: calc(100% + 32px);
      margin-left: -16px;
      height: 1px;
      border: 0;
      background-color: $unchecked-color;
    }
  }
}

.start {
  animation: start 0.5s ease-in-out;
}

.end {
  animation: end 0.5s ease-in-out;
}
</style>
