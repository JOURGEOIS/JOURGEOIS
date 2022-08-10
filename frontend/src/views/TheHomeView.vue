<template>
  <div class="the-home-view">
    <!-- 헤더 -->
    <header-basic :success="false" @prevClicked="$router.go(-1)">
      홈
    </header-basic>
    <the-home-view-container></the-home-view-container>
  </div>

  <!-- 건들지 말 것  -->
  <success-pop-up v-if="changePwPopupStatus">
    비밀번호가 변경되었습니다
  </success-pop-up>
  <success-pop-up v-if="logOutPopupStatus"> 로그아웃 되었습니다</success-pop-up>
  <failure-pop-up v-if="failModalStatus">잠시 후에 시도해주세요</failure-pop-up>
  <failure-pop-up v-if="refreshFailPopupStatus"
    >다시 로그인 해주세요</failure-pop-up
  >
  <success-pop-up v-if="signOutPopupStatus">탈퇴되었습니다</success-pop-up>
  <success-pop-up v-if="completeSignUpModalStatus"
    >회원가입이 완료되었습니다.</success-pop-up
  >
  <!-- <div class="category-container">
    <router-link to="/user/signup">회원가입</router-link>
    <router-link to="/user/login">로그인</router-link>
    <router-link to="/user/my-info">유저정보수정</router-link>
    <router-link to="/user/my-info/password">비밀번호 변경</router-link>
  </div> -->
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheHomeViewContainer from "@/components/homes/TheHomeViewContainer.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { computed, getCurrentInstance, onMounted } from "vue";
import { useStore } from "vuex";
import SuccessPopUp from "@/components/modals/SuccessPopUp.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
const store = useStore();

// [basic] navbar icon 0번 켜기
const setNavIconStatus = (index: number) => {
  store.dispatch("navbar/setNavIconStatus", index);
};
setNavIconStatus(0);

const logOutPopupStatus = computed(
  () => store.getters["account/getLogOutPopupStatus"]
);

const changePwPopupStatus = computed(
  () => store.getters["password/getChangePwPopupStatus"]
);

const failModalStatus = computed(
  () => store.getters["account/getFailModalStatus"]
);

const signOutPopupStatus = computed(
  () => store.getters["account/getSignOutPopupStatus"]
);

const refreshFailPopupStatus = computed(
  () => store.getters["personalInfo/getRefreshFailPopupStatus"]
);

// 회원가입 성공 모달
const completeSignUpModalStatus = computed(
  () => store.getters["signup/getCompleteSignUpModalStatus"]
);

onMounted(() => {
  // 로그아웃 팝업 시간제 off
  if (logOutPopupStatus) {
    setTimeout(() => store.dispatch("account/toggleLogOutPopup", false), 2000);
  }

  // 비밀번호 변경 팝업 시간제 off
  if (changePwPopupStatus) {
    setTimeout(
      () => store.dispatch("password/toggleChangePwPopup", false),
      2000
    );
  }

  // 실패 팝업 시간제 off
  if (failModalStatus) {
    setTimeout(
      () => store.dispatch("account/toggleFailModalStatus", false),
      2000
    );
  }

  // 탈퇴 팝업 시간제 off
  if (signOutPopupStatus) {
    setTimeout(() => store.dispatch("account/toggleSignOutPopup", false), 2000);
  }

  // 리프레시 실패 팝업 시간제 off
  if (refreshFailPopupStatus) {
    setTimeout(
      () => store.dispatch("personalInfo/toggleRefreshFailPopup", false),
      2000
    );
  }
});
</script>

<style scoped lang="scss">
.the-home-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
  .category-container {
    @include flex;
    align-items: center;
    padding: 30px;
    gap: 10px;
  }
}
</style>
