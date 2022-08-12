<template>
  <h4>
    일반
  </h4>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const communityPost = computed(() => 
  store.getters["profileDesc/getCurrentUserPostCommunity"]
)
console.log(communityPost.value)
onMounted(() => {
  store.dispatch("profileDesc/getCurrentUserPostCommunity", route.params.userId )
});
</script>

<style scoped lang="scss">

</style>