<template>
  <div class="change-user-info-view">
    <!-- 헤더 -->
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
      회원 정보 수정
    </header-basic>
    <section class="top-view">
      <the-user-info-change-form></the-user-info-change-form>
      <div class="change-user-info-link">
        <router-link to="/user/sign-out">회원탈퇴</router-link>
      </div>
    </section>
  </div>
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
import TheUserInfoChangeForm from "@/components/accounts/TheUserInfoChangeForm.vue";
import NavBar from "@/components/basics/NavBar.vue";
import SuccessPopUp from "@/components/modals/SuccessPopUp.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
import { useStore } from "vuex";
import { computed, onMounted, watch } from "vue";

const store = useStore();

// navbar 색깔 부여
store.dispatch("navbar/setNavIconStatus", 4);

// 개인 정보 수정 성공 팝업
const successPopUpStatus = computed(
  () => store.getters["account/getUserInfoChangeSuccess"]
);
const toggleSuccessPopUp = (value: boolean) =>
  store.dispatch("account/toggleUserInfoChangeSuccess", value);

// 개인 정보 수정 실패 팝업
const failModalStatus = computed(
  () => store.getters["account/getUserInfoChangeError"]
);

const toggleFailPopUp = (value: boolean) => {
  store.dispatch("account/toggleUserInfoChangeError", value);
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
.change-user-info-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  p {
    cursor: pointer;
  }
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

  .change-user-info-link {
    @include flex-xy(flex-end, center);
    gap: 8px;
    width: 100%;
    margin-top: 16px;
    * {
      padding: 8px;
      color: $white400;
      @include font($fs-btn, $fw-medium);
    }
  }
  a {
    text-decoration: none;
  }
}
</style>
