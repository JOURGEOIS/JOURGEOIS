<template>
  <div class="email-form-view">
    <!-- 헤더 -->
    <header-notice :success="false">
    </header-notice>
    <section class="content top-view">
      <h1>본인 이메일 인증이 <br /> 완료되었습니다.</h1>
      <hr />
      <p>주류주아 가입의 모든 절차가 끝났습니다.</p> 
      <p>아래 버튼을 통해 가입을 완료해주세요.</p>
      <br>
      <!-- 홈으로 돌아가기 -->
      <div class="button">
        <button-basic
          :button-style="['primary', 'long', 'small']"
          @click="$router.go(-1)"
        >
          주류주아로 돌아가기
        </button-basic>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderNotice from "@/components/basics/HeaderNotice.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { useRouter } from "vue-router";
const router = useRouter();

function clickHome() {
  router.push({
    path: "/",
    name: "TheHomeView",
  });
}
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