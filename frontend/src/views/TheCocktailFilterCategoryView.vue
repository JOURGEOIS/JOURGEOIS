<!-- filter 카테고리별 재료 상세 view -->
<template>
  <div class="the-cocktail-filter-category-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      {{ title }}
    </header-basic>
    <section>
      <div
        class="the-cocktail-filter-category-item"
        v-for="item in data"
        :key="item.id"
        @click="clickIngredient(item.id)"
        :class="choiceIngredients.includes(item.id) ? 'activate' : 'none'"
      >
        <div class="img" :style="{ backgroundImage: `url(${item.img})` }"></div>
        <p>{{ item.nameKr }}</p>
        <p>{{ item.name }}</p>
      </div>
      <button-basic-round
        class="cocktail-search-filter-button"
        :button-style="[buttonColor, 'calc(100% - 32px)', '']"
        :disabled="searchFilterResultCnt === 0"
        @click="setSearchFilter"
      >
        {{ searchFilterResultCnt }}개의 검색 결과
      </button-basic-round>
    </section>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import ButtonBasicRound from "@/components/basics/ButtonBasicRound.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { computed, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { baseAlcohol, liqueur, drinks, ingredients } from "../assets/filter";
const route = useRoute();
const router = useRouter();
const store = useStore();

// index: url 파라미터
const index = route.params.category;

// 타이틀
const title = computed(() => {
  if (typeof index === "string") {
    const firstChar = index.charAt(0);
    const char = index.slice(1);
    return firstChar.toUpperCase() + char;
  }
});

// 보여줄 재료 리스트
const data = computed(() => {
  switch (index) {
    case "alcohol": {
      return baseAlcohol;
    }
    case "liqueur": {
      return liqueur;
    }
    case "drinks": {
      return drinks;
    }
    case "ingredients": {
      return ingredients;
    }
  }
});

// 선택된 재료 리스트
const choiceIngredients = computed(
  () => store.getters["cocktailSearch/getSearchFilterIngredients"]
);

// 재료 선택하면 vuex에 저장한다.
const clickIngredient = (id: number) => {
  store.dispatch("cocktailSearch/addFilterIngredients", id);
};

// 만들 수 있는 칵테일 개수 값 가져오기
const searchFilterResultCnt = computed(
  () => store.getters["cocktailSearch/getFilterResultCnt"]
);

// 버튼 색상 (만들 수 있는 칵테일이 생기면 활성화)
const buttonColor = computed(() => {
  if (searchFilterResultCnt.value) {
    return "light-primary";
  } else {
    return "sub";
  }
});

// 버튼 클릭하면 결과 창으로 이동한다.
const setSearchFilter = () => {
  router.push({ name: "TheSearchFilterResultView" });
};

// 이전 창으로 가도 필터가 유지되도록 상태를 true로 유지 시킨다.
onMounted(() => {
  store.dispatch("cocktailSearch/toggleFilter", true);
});
</script>

<style scoped lang="scss">
.the-cocktail-filter-category-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
  margin-bottom: 48px;

  section {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(40%, auto));
    position: relative;

    @media #{$tablet} {
      grid-template-columns: repeat(auto-fill, minmax(25%, auto));
    }

    width: 100%;
    gap: 36px;
    margin-top: 36px;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }

    .the-cocktail-filter-category-item {
      @include flex(column);
      align-items: center;
      justify-content: center;
      padding: 8px 0;
      @include font($fs-main, $fw-medium);
      border-radius: 20px;

      p {
        &:last-child {
          @include font($fs-md, $fw-regular);
          color: $gray100;
        }
      }

      .img {
        width: 88px;
        height: 88px;
        border: 0;
        border-radius: 50%;
        background-repeat: no-repeat;
        background-position: center;
        background-size: cover;
      }
    }
  }
}

.activate {
  @include shadow-feed;

  .img {
    background-color: $white200;
  }
}

.cocktail-search-filter-button {
  position: fixed;
  bottom: 100px;
  left: 50%;
  transform: translate(-50%, 0);
  padding: 16px;
  border-radius: 10px;
  @include font($fs-md, $fw-bold);
  opacity: 0.85;
}
</style>
