<template>
  <div class="community-desc-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      nickname
    </header-basic>

  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { useStore } from 'vuex'

// 유저 정보 불러오기
const store = useStore()

</script>

<style scoped lang="scss">

</style>