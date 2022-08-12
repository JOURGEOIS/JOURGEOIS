<template>
  <article v-for="post in userCustomPostData" :key="post.postId">
    <the-custom-cocktail-post-item :post="post"></the-custom-cocktail-post-item>
  </article>
</template>

<script setup lang="ts">
import TheCustomCocktailPostItem from "@/components/profile/TheCustomCocktailPostItem.vue";
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const userCustomPostData = computed(
  () => store.getters["profileDesc/getCurrentUserPostCustom"]
)
onMounted(() => {
  store.dispatch("profileDesc/getCurrentUserData", route.params.userId);
  store.dispatch("profileDesc/getCurrentUserPostCustom", route.params.userId);
});
</script>

<style scoped lang="scss">

</style>