<template>
  <the-custom-cocktail-post-item
    v-for="(post, id) in userPostListData"
    :key="`main-${id}`"
    :post="post"
  ></the-custom-cocktail-post-item>
</template>

<script setup lang="ts">
import TheCustomCocktailPostItem from '@/components/profile/TheCustomCocktailPostItem.vue';
import { computed, onMounted } from "vue";
import { useRoute } from 'vue-router';
import { useStore } from "vuex";
const route = useRoute()
const store = useStore();

const userPostListData = computed(
  () => store.getters["profileDesc/getCurrentUserPostCommunity"]
)
onMounted(() => {
  store.dispatch("profileDesc/getCurrentUserData", route.params.userId )
  store.dispatch("profileDesc/getCurrentUserPostCustom", route.params.userId )
});
</script>

<style scoped lang="ts">

</style>