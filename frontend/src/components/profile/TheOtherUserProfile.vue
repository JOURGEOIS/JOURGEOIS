<template>
  <!-- 헤더 불러오기 -->
  <header-basic
    :prev="true"
    :success="true"
    formId="the-community-form"
    @prevClicked="$router.go(-1)"
  >
    글쓰기
  </header-basic>
  <div></div>
</template>

<script setup lang="ts"></script>

<style scoped></style>
