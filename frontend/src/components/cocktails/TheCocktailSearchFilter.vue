<!-- filter: container
  on, off시 트랜지션 효과를 준다.  -->
<template>
  <teleport to="body">
    <div class="cocktail-search-filter" @click.self="clickXIcon">
      <div class="cocktail-search-filter-container" :class="animation">
        <!-- filter: header -->
        <div class="cocktail-search-filter-header">
          <p>필터</p>
          <span class="material-icons" @click="clickXIcon"> close </span>
        </div>
        <hr />

        <!-- filter:form  -->
        <the-cocktail-search-filter-form></the-cocktail-search-filter-form>
      </div>
    </div>
  </teleport>
</template>

<script setup lang="ts">
import TheCocktailSearchFilterForm from "@/components/cocktails/TheCocktailSearchFilterForm.vue";
import { computed, ref, onBeforeMount } from "vue";
import { useStore } from "vuex";
import { onBeforeRouteLeave } from "vue-router";
const store = useStore();

// 애니메이션 상태
const animation = computed(
  () => store.getters["cocktailSearch/getFilterClass"]
);

// 필터 off
const clickXIcon = () => {
  store.dispatch("cocktailSearch/changeFilterClass", "end");
  setTimeout(() => store.dispatch("cocktailSearch/toggleFilter", false), 490);
};

// 필터 초기화 (선택지 리셋!)
const resetFilter = () => {
  store.dispatch("cocktailSearch/resetSearchFilter");
};

// * 뒤로가기 할 때 모달 내려가게 하기
const getFilterStatus = computed(
  () => store.getters["cocktailSearch/getFilterStatus"]
);

onBeforeRouteLeave((to, from, next) => {
  if (getFilterStatus.value) {
    clickXIcon();
  } else {
    next();
  }
});
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
    transform: translate3d(0, 0, 0);
  }
}

.cocktail-search-filter {
  @include flex-xy(center, center);
  position: fixed;
  top: 0;
  height: 100vh;
  z-index: 10;
  width: 100vw;
  background-color: rgba(0, 0, 0, 0.7);
  .cocktail-search-filter-container {
    @include flex(column);
    gap: 11px;
    position: absolute;
    bottom: 0px;
    width: 100%;
    max-width: 600px;
    height: 90%;
    border-top-left-radius: 30px;
    border-top-right-radius: 30px;
    padding: 0 16px;
    background-color: white;

    @media #{$tablet} {
      width: 64%;
    }

    .cocktail-search-filter-header {
      @include flex-xy(center, center);
      margin-top: 24px;

      p {
        &:first-child {
          @include font($fs-main, $fw-medium);
          color: $main-color;
        }
      }
      .material-icons {
        position: absolute;
        right: 5px;
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
