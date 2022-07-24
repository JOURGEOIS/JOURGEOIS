<template>
  <div class="tmp-container">
    <router-link to="/">Home</router-link> <br />
    <router-link to="/user/signup">회원가입</router-link> <br />
    <router-link to="/user/login">로그인</router-link>
    <router-link to="/user/help/info">유저정보수정</router-link>
  </div>
  <success-pop-up v-if="logOutPopupStatus"> 로그아웃 되었습니다</success-pop-up>
  <failure-pop-up v-if="failModalStatus"
    >잠시 후에 시도해주세요.</failure-pop-up
  >
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

const failModalStatus = computed(
  () => store.getters["account/getFailModalStatus"]
);

onMounted(() => {
  // 로그아웃 팝업 시간제 off
  if (logOutPopupStatus) {
    setTimeout(() => store.dispatch("account/toggleLogOutPopup", false), 3000);
  }

  // 실패 팝업 시간제 off
  if (failModalStatus) {
    setTimeout(
      () => store.dispatch("account/toggleFailModalStatus", false),
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
