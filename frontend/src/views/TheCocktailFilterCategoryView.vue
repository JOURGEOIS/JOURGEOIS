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
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import { baseAlcohol, liqueur, drinks, ingredients } from "../assets/filter";
const route = useRoute();
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

const array = choiceIngredients;
// 재료 선택
const clickIngredient = (id: number) => {
  store.dispatch("cocktailSearch/addFilterIngredients", id);
};

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
</style>
