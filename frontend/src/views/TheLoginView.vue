<!-- 로그인 페이지 -->
<template>
  <div class="login-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      로그인
    </header-basic>
    <section class="login-section">
      <the-login-form></the-login-form>
      <div class="login-link">
        <router-link to="/user/help/password">
          <p>비밀번호 찾기</p>
        </router-link>
        <router-link to="/user/signup">
          <p>회원가입</p>
        </router-link>
      </div>
      <the-social-login></the-social-login>
    </section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheLoginForm from "@/components/accounts/TheLoginForm.vue";
import TheSocialLogin from "@/components/accounts/TheSocialLogin.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
import { useStore } from "vuex";
import { computed } from "vue";
const store = useStore();

const loginFailModalStatus = computed(
  () => store.getters["account/getLoginFailModalStatus"]
);
</script>

<style scoped lang="scss">
.login-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
}

section {
  width: 100%;
  margin-top: 2rem;

  @media #{$tablet} {
    width: 80%;
  }

  @media #{$pc} {
    width: 70%;
  }
}

section > *:first-child {
  margin-bottom: 1rem;
}

.login-link {
  @include flex-center();
  gap: 2rem;
  color: $sub-color;
  @include font(13px, $fw-regular);

  @media #{$tablet} {
    font-size: 14px;
  }

  p {
    cursor: pointer;
  }
  a {
    color: $sub-color;
    text-decoration: none;
  }
}
</style>
