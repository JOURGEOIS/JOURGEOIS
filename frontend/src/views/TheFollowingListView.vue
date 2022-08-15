<template>
  <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
    팔로잉한 유저
  </header-basic>
  <div class="liked-user-list-view top-view">
    <the-followee-item></the-followee-item>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import TheFolloweeItem from '@/components/profile/TheFolloweeItem.vue';
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";

</script>

<style scoped lang="scss">

</style>