<template>
  <teleport to="body">
    <div class="loading-container">
      <div class="loading">
        <div class="line-1"></div>
        <div class="line-2"></div>
        <div class="line-3"></div>
      </div>
    </div>
  </teleport>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.loading-container {
  @include flex-xy(center, center);
  position: fixed;
  top: 0;
  height: 100vh;
  width: 100vw;
  background-color: rgba(255, 255, 255, 0.8);
}

@keyframes loading {
  0% {
    transform: translate(0, 0);
  }
  50% {
    transform: translate(0, 15px);
  }
  100% {
    transform: translate(0, 0);
  }
}

.loading > div {
  display: inline-block;
  width: 15px;
  height: 15px;
  margin: 3px;
  border-radius: 15px;
  background-color: $primary-color;

  @media #{$tablet} {
    width: 20px;
    height: 20px;
  }
}

.line-1 {
  animation: loading 0.8s 0.1s linear infinite;
}
.line-2 {
  animation: loading 0.8s 0.2s linear infinite;
}
.line-3 {
  animation: loading 0.8s 0.3s linear infinite;
}
</style>
