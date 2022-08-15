<template>
  <!-- <hr /> -->
  <div class="social-login">
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
import axios from "axios";
import api from "../../api/api";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { computed, onMounted, reactive } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
const store = useStore();
const router = useRouter();

// 구글 로그인 클릭
const googleAPI = computed(
  () => store.getters["socialLogin/getGoogleLoginApi"]
);

const clickGoogle = () => {
  const googleClientId = `217608233279-029l12gsikbdcs9jpeku5i8e7sdbqu1q.apps.googleusercontent.com`;
  const googleRedirect = `https://jourgeois.com/api/member/login/google/redirect`;
  // const googleRedirect = `http://localhost:8080/member/login/google/redirect`;
  window.location.href = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${googleClientId}&redirect_uri=${googleRedirect}&response_type=code&scope=profile%20email%20openid`;
};

const clickKakao = () => {
  const kakaoRestApiKey = `f1c36f65322c75f1f28caf1560a306d1`;
  const kakaoRedirect = `https://jourgeois.com/api/member/login/kakao/redirect`;
  window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${kakaoRestApiKey}&redirect_uri=${kakaoRedirect}&response_type=code`;
};

const clickNaver = () => {
  const naverClientId = `D0B_Xn9oGT7I_66h9Z4U`;
  const naverRedirect = `https://jourgeois.com/api/member/login/naver/redirect`;
  window.location.href = `
  https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${naverClientId}&redirect_uri=${naverRedirect}&state=1234`;
};

// const kakaoLogin = () => {
//   window.Kakao.Auth.login({
//     scope: "profile_nickname, profile_image, account_email",
//     success: getKakaoAccount,
//   });
// };
// const getKakaoAccount = () => {
//   window.Kakao.API.request({
//     url: "/v2/user/me",
//     success: (res: any) => {
//       const kakao_account = res.kakao_account;
//       const nickname = kakao_account.profile.nickname;
//       const email = kakao_account.email;
//       console.log(kakao_account);
//       console.log("nickname", nickname);
//       console.log("email", email);

//       //로그인 처리 구현
//       alert("로그인 성공!");
//     },
//     fail: (error: any) => {
//       console.log(error);
//     },
//   });
// };
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
