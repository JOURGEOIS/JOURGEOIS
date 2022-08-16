<template>
  <div class="email-form-view">
    <!-- 헤더 -->
    <header-notice :success="false">
    </header-notice>
    <section class="content top-view">
      <div class="email-image">
        <!-- <img
          src="https://jourgeois-profile-image.s3.ap-northeast-2.amazonaws.com/default/emailpage2.png"
          alt=""
          class="email-logo"
        > -->
        <span class="material-icons-outlined email-icon">
          drafts
        </span>
      </div>
      <!-- <h1>본인 이메일 인증이 <br /> 완료되었습니다.</h1> -->
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
      .email-icon {
        @include font(150px, $fw-regular);
        color: #dedefe;
        user-select: none;    
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