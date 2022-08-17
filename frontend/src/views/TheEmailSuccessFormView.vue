<template>
  <div class="email-form-view">
    <section class="content">
      <div class="title">
        <img src="https://jourgeois-profile-image.s3.ap-northeast-2.amazonaws.com/default/icon-bold-line-nobackground.png" alt="">
      </div>
      <div class="email-image">
        <img
          src="https://jourgeois-profile-image.s3.ap-northeast-2.amazonaws.com/default/mail_success.png"
          alt=""
          class="email-logo"
        >
      </div>
      <h1>이메일 인증 완료</h1>
      <hr />
      <p>회원가입 페이지로 돌아가 가입을 진행해주세요.</p> 
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderNotice from "@/components/basics/HeaderNotice.vue";

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

.email-form-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
  .content {
    @include flex(column);
    gap: 10px;
    .email-image{
      @include flex-center;
      .email-logo {
        @include flex;
        justify-content: center;
        width: 150px;
        height: auto;
      }
    }
    h1 {
      @include flex-center;
    }
    p {
      @include flex-center;
    }
    .button {
      @include flex-center;
      margin-top: 35px;
    }
  }
}
section {
  width: 100%;
  margin-top: 8px;

  @media #{$tablet} {
    width: 80%;
  }

  @media #{$pc} {
    width: 60%;
  }
}
</style>