<template>
  <section>
    <div class="title">
      <img src="https://jourgeois-profile-image.s3.ap-northeast-2.amazonaws.com/default/icon-bold-line-nobackground.png" alt="">
    </div>
    <div class="content">
      <p class="d404">404</p>
      <p class="not-found">Cocktail Not Found</p>
      <p class="message">죄송합니다. 원하는 페이지를 찾을 수 없습니다.</p>
    </div>
  </section>
</template>

<script setup lang="ts">

</script>

<style scoped lang="scss">
section {
  @include flex(column);
  width: 100%;
  gap: 20px;
}
img {
  width: 150px;
  height: auto;
  margin-top: 10px;
}
.content {
  @include flex(column);
  @include flex-center;
  .d404 {
    @include font(100px, $fw-bold)
  }
  .not-found {
    @include font(30px, $fw-regular)
  }
}
</style>