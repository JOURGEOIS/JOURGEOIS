<template>
  <div>
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
    <!-- <a id="custom-login-btn" @click="kakaoLogin()">
      <img src="//k.kkaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg" width="222" />
    </a> -->
  </div>
</template>
<script>
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
export default {
  methods: {
    kakaoLogin() {
      window.Kakao.Auth.login({
        scope: "profile, account_email",
        success: this.getKakaoAcount,
      });
    },
    getKakaoAccount() {
      window.Kakao.API.request({
        url: "/v2/user/me",
        success: (res) => {
          const kakao_account = res.kakao_account;
          const nickname = kakao_account.profile.nickname;
          const email = kakao_account.email;
          console.log("nickname", nickname);
          console.log("email", email);

          //로그인 처리 구현
          alert("로그인 성공!");
        },
        fail: (error) => {
          error;
        },
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
