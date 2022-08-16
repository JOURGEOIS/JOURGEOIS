<template>
  <article v-for="post in userCustomPostData" :key="post.postId">
    <the-custom-cocktail-post-item :post="post"></the-custom-cocktail-post-item>
  </article>
  <section>
    <div class="custom-post-none" v-if="isEmpty">
      <p><span class="material-icons-outlined">lock</span>비공개 계정입니다.</p>
    </div>
  </section>
</template>

<script setup lang="ts">
import TheCustomCocktailPostItem from "@/components/profile/TheCustomCocktailPostItem.vue";
import { ref, computed, onBeforeMount, onUnmounted } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const userCustomPostData = computed(
  () => store.getters["profileDesc/getCurrentUserPostCustom"]
);

const isEmpty = ref(false);
setTimeout(() => {
  if (userCustomPostData.value.length === 0) {
    isEmpty.value = true;
  }
}, 200);

// 인피니티 스크롤
const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "profileDesc/getCurrentUserPostCustomData",
    data: route.params.userId,
  };
  store.dispatch("scroll/handleScroll", data);
};

// 인피니티 스크롤을 연동, 처음 데이터 가져오기
onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch(
    "profileDesc/getCurrentUserPostCustomData",
    route.params.userId
  );
});

// unmount될 때, 페이지와 리스트를 리셋한다.
onUnmounted(() => {
  store.dispatch("profileDesc/resetCurrentUserPost");
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss">
article {
  width: 100%;
  @include flex(column);
  gap: 15px;
}
.custom-post-none {
  width: 100%;
  padding: 64px 16px;
  border-radius: 16px;
  background-color: $white200;
  @include font($fs-main, $fw-bold);
  text-align: center;
  p {
      @include flex-center;
    }

  @media #{$tablet} {
    @include font($fs-lg, $fw-bold);
    width: 450px;
  }
}
</style>
