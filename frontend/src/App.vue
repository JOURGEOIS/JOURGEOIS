<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script setup lang="ts"></script>

<style lang="scss">
@import "@/styles/style.scss";
#app {
  margin-left: $margin-mobile;
  margin-right: $margin-mobile;
}
</style>
