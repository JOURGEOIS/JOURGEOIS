<template>
  <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
    프로필
  </header-basic>
  <div class="the-user-profile-view top-view">
    <the-user-profile-basic></the-user-profile-basic>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import TheUserProfileBasic from '@/components/profile/TheUserProfileBasic.vue'
import HeaderBasic from '@/components/basics/HeaderBasic.vue'
import NavBar from '@/components/basics/NavBar.vue'
const store = useStore()

</script>

<style scoped lang="scss"></style>