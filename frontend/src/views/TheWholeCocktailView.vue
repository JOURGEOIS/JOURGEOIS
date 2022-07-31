<template>
  <div class="cocktail-list-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      칵테일</header-basic
    >
    <div class="the-item-container">
      <the-list-item-cocktail
        v-for="(item, idx) in wholeCocktailList"
        :key="idx"
        :data="item"
        @click="clickCocktail(item)"
      ></the-list-item-cocktail>
    </div>
  </div>
</template>

<script setup lang="ts">
import TheListItemCocktail from "@/components/cocktails/TheListItemCocktail.vue";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import axios from "axios";
import api from "../api/api";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { reactive, ref, onMounted } from "vue";
const router = useRouter();
const store = useStore();

// 칵테일 interface
interface Cocktail {
  id: number;
  name: string;
  img: string | null;
  alcohol: number | null;
  baseLiquor: string;
}

// 전체 칵테일 리스트
const wholeCocktailList: Cocktail[] = reactive([]);
const wholeCocktailListPage = ref(0);

// 칵테일 누른 경우 칵테일 상세 페이지로 이동
const clickCocktail = (item: Cocktail) => {
  router.push({ name: "TheCocktailDescView", params: { cocktailId: item.id } });
};

// * View 입장과 함께 처음 api 불러오기
const addWholeCocktailList = () => {
  axios({
    url: api.lookups.wholeCocktail(),
    method: "GET",
    headers: {
      email: "tmdgns1126@naver.com",
    },
    params: {
      page: wholeCocktailListPage.value,
    },
  })
    .then((res) => {
      res.data.forEach((cocktail: Cocktail) => {
        wholeCocktailList.push(cocktail);
      });
      wholeCocktailListPage.value++;
    })
    .catch((err) => console.error(err));
};

onMounted(() => {
  addWholeCocktailList();
});
</script>

<style scoped lang="scss">
.cocktail-list-view {
  @include flex(column);
  @include accountLayOut;
  justify-content: flex-start;
  align-items: center;
  .the-item-container {
    @include flex(column);
    width: 100%;

    margin-top: 1rem;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }
  }
}
</style>
