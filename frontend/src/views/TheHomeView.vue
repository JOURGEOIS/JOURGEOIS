<template>
  <div class="tmp-container">
    <router-link to="/">Home</router-link> <br />
    <router-link to="/user/signup">회원가입</router-link> <br />
    <router-link to="/user/login">로그인</router-link>
    <router-link to="/user/my-info">유저정보수정</router-link>
    <router-link to="/user/my-info/password">비밀번호 변경</router-link>
  </div>
  <success-pop-up v-if="changePwPopupStatus">
    비밀번호가 변경되었습니다
  </success-pop-up>
  <success-pop-up v-if="logOutPopupStatus"> 로그아웃 되었습니다</success-pop-up>
  <failure-pop-up v-if="failModalStatus">잠시 후에 시도해주세요</failure-pop-up>
  <failure-pop-up v-if="refreshFailPopupStatus"
    >다시 로그인 해주세요</failure-pop-up
  >
  <success-pop-up v-if="signOutPopupStatus">탈퇴되었습니다</success-pop-up>
</template>

<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useStore } from "vuex";
import SuccessPopUp from "@/components/modals/SuccessPopUp.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
const store = useStore();

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

onMounted(() => {
  // 로그아웃 팝업 시간제 off
  if (logOutPopupStatus) {
    setTimeout(() => store.dispatch("account/toggleLogOutPopup", false), 3000);
  }

  // 비밀번호 변경 팝업 시간제 off
  if (changePwPopupStatus) {
    setTimeout(
      () => store.dispatch("password/toggleChangePwPopup", false),
      3000
    );
  }

  // 실패 팝업 시간제 off
  if (failModalStatus) {
    setTimeout(
      () => store.dispatch("account/toggleFailModalStatus", false),
      3000
    );
  }

  // 탈퇴 팝업 시간제 off
  if (signOutPopupStatus) {
    setTimeout(() => store.dispatch("account/toggleSignOutPopup", false), 3000);
  }

  // 리프레시 실패 팝업 시간제 off
  if (refreshFailPopupStatus) {
    setTimeout(
      () => store.dispatch("personalInfo/toggleRefreshFailPopup", false),
      3000
    );
  }
});
</script>

<style scoped lang="scss">
.tmp-container {
  @include flex(column);
  align-items: center;
  padding: 30px;
  @include font(20px, $fw-bold);
}
</style>
