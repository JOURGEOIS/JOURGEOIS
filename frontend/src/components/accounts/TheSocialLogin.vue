<template>
  <hr />
  <div class="social-login">
    <p>or</p>
    <button-basic
      @click="clickGoogle"
      :button-style="['google-login', 'long', 'small']"
    >
      <div class="button-content">
        <img
          src="https://raw.githubusercontent.com/JaeKP/image_repo/main/img/google.png"
        />
        Google 로 로그인
      </div>
    </button-basic>
    <button-basic
      @click="clickKakao"
      :button-style="['kakao-login', 'long', 'small']"
    >
      <div class="button-content">
        <img
          src="https://raw.githubusercontent.com/JaeKP/image_repo/main/img/kakao.png"
        />
        KaKao 로 로그인
      </div>
    </button-basic>
    <button-basic
      @click="clickNaver"
      :button-style="['naver-login', 'long', 'small']"
    >
      <div class="button-content">
        <img
          src="https://raw.githubusercontent.com/JaeKP/image_repo/main/img/naver.png"
        />
        Naver 로 로그인
      </div>
    </button-basic>
  </div>
</template>

<script setup lang="ts">
import axios from 'axios'
import api from '../../api/api'
import ButtonBasic from '@/components/basics/ButtonBasic.vue'
import { computed, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
const store = useStore()
const router = useRouter()

// 구글 로그인 클릭
const googleAPI = computed(() => store.getters['socialLogin/getGoogleLoginApi'])

const clickGoogle = () => {
  store.dispatch('socialLogin/getGoogleLoginApi', googleAPI)
  // window.open("https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?scope=profile%20email%20openid&response_type=code&redirect_uri=http%3A%2F%2Fjourgeois.com%2Fapi%2Fmember%2Flogin%2Fgoogle%2Fredirect&client_id=217608233279-k2op58rvkdtbbteakk7ag0tp5ia54mij.apps.googleusercontent.com&flowName=GeneralOAuthFlow", "_blank");
}

const clickKakao = () => {
  axios({
    url: api.accounts.kakaoLogin(),
    method: 'GET',
  })
    .then((res) => {
      const data = res.data
      console.log(data)
    })
    .catch((err) => {
      console.error(err.response)
    })
}

const clickNaver = () => {
  window.open("http://jourgeois.com/api/member/login/naver", "_blank");
  // axios({
  //   url: api.accounts.naverLogin(),
  //   method: 'GET',
  // })
  //   .then((res) => {
  //     const data = res.data
  //     console.log(data)
  //   })
  //   .catch((err) => {
  //     console.error(err.response)
  //   })
}
</script>

<style scoped lang="scss">
hr {
  @include hr;
  width: 100%;
}

p {
  @include font($fs-sm, $fw-regular);
}

img {
  position: absolute;
  left: 20%;
  width: 30px;
}
.social-login {
  @include flex(column);
  align-items: center;
  justify-content: center;
}

.social-login > * {
  margin-bottom: 1.2rem;
}

.button-content {
  position: relative;
  @include flex-center();
  @include font($fs-md, $fw-regular);
  letter-spacing: $ls-main;
  color: $main-color;
}

.button-content > img {
  position: absolute;
  left: -40px;
}

.google-login-bg {
  border: 1px solid $unchecked-color;
  background-color: $white;
}

.google-login-bg img {
  width: 20px;
  left: -35px;
}

.kakao-login-bg {
  background-color: rgb(253, 220, 63);
}

.naver-login-bg {
  background-color: rgb(3, 199, 90);
}
</style>
