<template>
  <section class="add-custom-cocktail" @click="addCustomCocktail">
    <span class="material-icons add-icon">add_circle</span>
    <span>커스텀 칵테일 만들기</span>
  </section>
  <section class="custom-cocktail-section">
    <div class="custom-cocktail-container" :class="{ blur: !isLoggedIn }">
      <the-list-item-custom-cocktail
        v-for="(item, idx) in customCocktails"
        :key="idx"
        :data="item"
        @click="clickCustomCocktail(item)"
      ></the-list-item-custom-cocktail>
    </div>
    <blur-block
      v-if="!isLoggedIn"
      id="custom-cocktail-blur"
      :class="{ none: customCocktails.length === 0 }"
    >
      <p>로그인하고</p>
      <p>유저들이 만든 칵테일 보기 🎉</p>
    </blur-block>
  </section>
</template>

<script setup lang="ts">
import TheListItemCustomCocktail from "@/components/cocktails/TheListItemCustomCocktail.vue";
import BlurBlock from "@/components/basics/BlurBlock.vue";
import { useRoute, useRouter } from "vue-router";
import { computed, onBeforeMount, onUnmounted } from "vue";
import { useStore } from "vuex";
import { CustomCocktail } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);

// 원본 칵테일 아이디는 현재 칵테일 params
const originalCocktailId = Number(route.params.cocktailId);
// 최신순 정렬
const asc = true;

// 커스텀 칵테일 리스트
const customCocktails = computed(() => {
  return store.getters["customCocktailInfo/getCustomCocktails"];
});

// 커스텀 칵테일 만들기 클릭
const addCustomCocktail = () => {
  router.push({
    name: "TheCustomCocktailFormView",
    params: { cocktailId: route.params.cocktailId },
  });
};

// 커스텀 칵테일 아이템 클릭시
const clickCustomCocktail = (item: CustomCocktail) => {
  const params = {
    cocktailId: originalCocktailId,
    feedId: item.customCocktail.postId,
  };
  router.push({ name: "TheCustomCocktailDescView", params });
};

// 스크롤시
const handleScroll = (event: Event) => {
  const dataInner = {
    originalCocktailId,
    asc,
  };
  const data = {
    event,
    action: "customCocktailInfo/setCustomCocktails",
    data: dataInner,
  };
  store.dispatch("scroll/handleScroll", data);
};

// 커스텀칵테일 리스트 추가
const setCustomCocktails = (data: {
  originalCocktailId: number;
  asc: boolean;
}) => {
  store.dispatch("customCocktailInfo/setCustomCocktails", data);
};

onBeforeMount(() => {
  // window 스크롤 이벤트 감지
  if (isLoggedIn.value) {
    window.addEventListener("scroll", handleScroll);
  }

  // 원본 칵테일 id와 정렬 순서 정보 data로 감싸서
  setCustomCocktails({ asc, originalCocktailId });
});

onUnmounted(() => {
  store.dispatch("customCocktailInfo/removeCustomCocktails");
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss">
.add-custom-cocktail {
  @include flex-center;
  gap: 10px;
  @include font-size-navy(18px);
  user-select: none;
  cursor: pointer;

  &:hover {
    color: $navy500;
    .add-icon {
      color: $navy500;
    }
  }

  .add-icon {
    @include font-size-navy(20px);
  }
}

.custom-cocktail-section {
  position: relative;
  width: 100%;

  .custom-cocktail-container {
    width: 100%;
    @include flex(column);
    gap: 15px;
  }
}

.blur {
  min-height: 180px;
  filter: blur(6px);
  -webkit-filter: blur(6px);
}

.none {
  top: -10px;
}
</style>
