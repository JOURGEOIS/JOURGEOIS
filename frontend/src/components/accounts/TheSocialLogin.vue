<template>
  <!-- <hr /> -->
  <div class="social-login">
    <p>or</p>
    <GoogleLogin
      :callback="callback"
      :button-style="['google-login', 'long', 'small']"
    />
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
      @click="kakaoLogin"
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
// import { CallbackTypes } from "vue3-google-login";
const store = useStore();
const router = useRouter();

// 구글 로그인 클릭
const googleAPI = computed(
  () => store.getters["socialLogin/getGoogleLoginApi"]
);

// const callback: CallbackTypes.CredentialCallback = (response) => {
//   console.log("Credential JWT string", response.credential);
// };

const clickGoogle = () => {
  window.location.href = `https://accounts.google.com/o/oauth2/v2/auth?client_id=217608233279-029l12gsikbdcs9jpeku5i8e7sdbqu1q.apps.googleusercontent.com&redirect_uri=https://jourgeois.com/api/member/login/google/redirect&response_type=code&scope=profile%20email%20openid`;
};

const kakaoLogin = () => {
  window.Kakao.Auth.login({
    scope: "profile_nickname, profile_image, account_email",
    success: getKakaoAccount,
  });
};
const getKakaoAccount = () => {
  window.Kakao.API.request({
    url: "/v2/user/me",
    success: (res: any) => {
      const kakao_account = res.kakao_account;
      const nickname = kakao_account.profile.nickname;
      const email = kakao_account.email;
      console.log(kakao_account);
      console.log("nickname", nickname);
      console.log("email", email);

      //로그인 처리 구현
      alert("로그인 성공!");
    },
    fail: (error: any) => {
      console.log(error);
    },
  });
};

// const clickKakao = () => {
//   // axios({
//   //   url: api.accounts.kakaoLogin(),
//   //   method: 'GET',
//   // })
//   //   .then((res) => {
//   //     const data = res.data
//   //     console.log(data)
//   //   })
//   //   .catch((err) => {
//   //     console.error(err.response)
//   //   })
//   window.open("https://kauth.kakao.com/oauth/authorize?client_id=0c777eb20471ff56b13960d5e8534d5e&redirect_uri=https://jourgeois.com/api/member/login/kakao/redirect&response_type=code")
// }

const clickNaver = () => {
  window.location.href = `
https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=D0B_Xn9oGT7I_66h9Z4U&redirect_uri=https://jourgeois.com/api/member/login/naver/redirect&state=78577e6c-3f10-4146-910c-3fd4e684dfb5`;
  // const naverLogin = new naver.LoginWithNaverId({
  //   clientId: "D0B_Xn9oGT7I_66h9Z4U", // Naver client key
  //   callbackUrl: `https://jourgeois.com/api/member/login/naver/redirect`,
  //   callbackHandle: true,
  //   isPopup: true,
  // });
  // naverLogin.init();
  // naverLogin.getLoginStatus(function (status: any) {
  //   if (status) {
  //     const email = naverLogin.user.getEmail();
  //     const name = naverLogin.user.getName();
  //     const profileImage = naverLogin.user.getProfileImage();
  //     const birthday = naverLogin.user.getBirthday();
  //     const uniqId = naverLogin.user.getId();
  //     const age = naverLogin.user.getAge();
  //     console.log(email);
  //     console.log(name);
  //     console.log(profileImage);
  //     console.log(birthday);
  //     console.log(uniqId);
  //     console.log(age);
  //   } else {
  //     console.log("AccessToken이 올바르지 않습니다.");
  //   }
  // });
  // naverLogin.reprompt();
};

// const clickNaver = () => {
//   var client_id = 'D0B_Xn9oGT7I_66h9Z4U';
//   var callbackUrl = 'https://jourgeois.com/api/member/login/naver/redirect';//서버 주소
//   var url = 'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=' + client_id + '&redirect_uri=' + callbackUrl + '&state=1234';
//   window.location.replace(url);
// }
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
