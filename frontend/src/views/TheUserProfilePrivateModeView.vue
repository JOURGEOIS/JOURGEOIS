<!-- 프로필 공개 설정 페이지 -->
<template>
  <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
    개인정보 보호
  </header-basic>
  <section class="profile-form top-view">
    <the-user-profile-private-mode-set></the-user-profile-private-mode-set>
  </section>
  <success-pop-up
    v-if="successPopUpStatus"
    @off-modal="toggleSuccessPopUp(false)"
  >
    성공적으로 저장되었습니다
  </success-pop-up>
  <failure-pop-up v-if="failModalStatus" @off-modal="toggleFailPopUp(false)">
    잠시 후에 다시 시도해주세요
  </failure-pop-up>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheUserProfilePrivateModeSet from "@/components/profile/TheUserProfilePrivateModeSet.vue"
import NavBar from "@/components/basics/NavBar.vue";
import SuccessPopUp from "@/components/modals/SuccessPopUp.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
import { useStore } from "vuex";
import { computed, onMounted, watch } from "vue";

const store = useStore();

// 계정 모드 수정 성공 팝업
const successPopUpStatus = computed(
  () => store.getters["settings/getPrivateModeChangeSuccess"]
);
const toggleSuccessPopUp = (value: boolean) =>
  store.dispatch("settings/privateModeChangeSuccess", value);

// 계정 모드 수정 실패 팝업
const failModalStatus = computed(
  () => store.getters["settings/getPrivateModeChangeError"]
);

const toggleFailPopUp = (value: boolean) => {
  store.dispatch("settings/privateModeChangeError", value);
};

// 시간제 모달
watch(failModalStatus, () => {
  if (failModalStatus.value) {
    setTimeout(() => {
      toggleFailPopUp(false);
    }, 2000);
  }
});

watch(successPopUpStatus, () => {
  if (successPopUpStatus.value) {
    setTimeout(() => {
      toggleSuccessPopUp(false);
    }, 2000);
  }
});

// 리셋
onMounted(() => {
  toggleSuccessPopUp(false);
  toggleFailPopUp(false);
});

</script>

<style scoped lang="scss">
.profile-form {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
  margin-bottom: 48px;
  .top-view {
    width: 100%;
  }
}
</style>