<!-- 프로필 공개 설정 페이지 -->
<template>
  <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
    개인정보 보호
  </header-basic>
  <section class="top-view">
    <the-user-profile-private-select-form></the-user-profile-private-select-form>
  </section>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheUserProfilePrivateSelectForm from "@/components/profile/TheUserProfilePrivateSelectForm.vue"
import NavBar from "@/components/basics/NavBar.vue";

</script>

<style scoped lang="scss">

</style>