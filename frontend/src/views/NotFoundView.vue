<template>
  <section>
    <div class="title">
      <img src="https://jourgeois-profile-image.s3.ap-northeast-2.amazonaws.com/default/icon-bold-line-nobackground.png" alt="">
    </div>
    <br />
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
  width: 100%;
}
img {
  width: 200px;
  height: auto;
}
br {
  margin: 20px;
}
.content {
  @include flex(column);
  @include flex-center;
  .d404 {
    @include font(100px, $fw-bold)
  }
  .not-found {
    @include font(50px, $fw-regular)
  }
}
</style>