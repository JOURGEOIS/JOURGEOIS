<template>
  <div class="home-basic-section">
    <slot name="title"></slot>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.home-basic-section {
  width: 100%;
  background-color: $white150;
  border-radius: 10px;
}
</style>
