<template>
  <article v-for="community in userCommunityPostData" :key="community.postId">
    <the-community-post-item :community="community"></the-community-post-item>
  </article>
</template>

<script setup lang="ts">
import TheCommunityPostItem from '@/components/profile/TheCommunityPostItem.vue';
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const userCommunityPostData = computed(() => 
  store.getters["profileDesc/getCurrentUserPostCommunity"]
)

console.log(userCommunityPostData.value)
onMounted(() => {
  store.dispatch("profileDesc/getCurrentUserPostCommunityData", route.params.userId )
});
</script>

<style scoped lang="scss">
article {
  width: 100%;
  @include flex(column);
  gap: 15px;
}
</style>