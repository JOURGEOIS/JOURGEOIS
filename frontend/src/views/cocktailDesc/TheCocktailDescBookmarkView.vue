<template>
  <div class="cocktail-bookmark-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      북마크한 유저
    </header-basic>
    <section class="cocktail-bookmark-section top-view-no-margin">
      <!-- <div class="cocktail-bookmark-none" v-if="isEmpty">
        <p>해당 칵테일을 북마크한 유저가 없습니다</p>
        <p class="emoji">😥</p>
      </div> -->
      <div class="cocktail-bookmark-exist">
        <the-list-item-user
          v-for="(item, idx) in bookMarkUserList"
          :key="idx"
          :data="item"
        ></the-list-item-user>
      </div>
    </section>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheListItemUser from "@/components/cocktails/TheListItemUser.vue";
import { User } from "../../interface";
import NavBar from "@/components/basics/NavBar.vue";
import { ref, onUnmounted, onBeforeMount, computed } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";
const route = useRoute();
const router = useRouter();
const store = useStore();

// navbar 색깔 부여
store.dispatch("navbar/setNavIconStatus", 3);

// const isEmpty = ref(false);
// setTimeout(() => {
//   if (bookMarkUserList.value.length === 0) {
//     isEmpty.value = true;
//   }
// }, 200);

// 북마크 유저 리스트
const bookMarkUserList = computed(
  () => store.getters["cocktailDesc/getCocktailBookMarkUserList"]
);

// 인피니티 스크롤
const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "cocktailDesc/fetchBookMarkUserList",
    data: { cocktailId: route.params.cocktailId },
  };
  store.dispatch("scroll/handleScroll", data);
};

// 리스트 받아오기
onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch("cocktailDesc/fetchBookMarkUserList", {
    cocktailId: route.params.cocktailId,
  });
});

// 리스트 리셋
onUnmounted(() => {
  store.dispatch("cocktailDesc/resetBookMarkUserList");
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss">
.cocktail-bookmark-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
  margin-bottom: 48px;

  section {
    @include flex(column);
    justify-content: center;
    align-items: center;
    gap: 24px;
    width: 100%;
    margin-top: 24px;

    .cocktail-bookmark-none {
      width: 100%;
      margin-top: 120px;
      padding: 64px 16px;
      border-radius: 16px;
      background-color: $white200;
      @include font($fs-main, $fw-bold);
      text-align: center;

      .emoji {
        font-size: $fs-xl;
      }

      @media #{$tablet} {
        @include font($fs-lg, $fw-bold);
        width: 450px;
      }
    }

    .cocktail-bookmark-exist {
      width: 100%;
    }
  }
}
</style>
