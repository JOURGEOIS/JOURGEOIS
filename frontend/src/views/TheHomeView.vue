<template>
  <div class="the-home-view">
    <!-- 헤더 -->
    <header-basic :success="false" @prevClicked="$router.go(-1)">
      홈
    </header-basic>
    <section class="top-view-no-margin">
      <the-home-view-container></the-home-view-container>
    </section>
  </div>

  <!-- 건들지 말 것  -->
  <success-pop-up v-if="changePwPopupStatus" @off-modal="offChangePwPopupModal">
    비밀번호가 변경되었습니다
  </success-pop-up>
  <success-pop-up v-if="logOutPopupStatus" @off-modal="offLogOutPopupModal">
    로그아웃 되었습니다</success-pop-up
  >
  <failure-pop-up v-if="failModalStatus" @off-modal="offFailModalStatus"
    >잠시 후에 시도해주세요</failure-pop-up
  >
  <failure-pop-up
    v-if="refreshFailPopupStatus"
    @off-modal="offRefreshFailPopupStatus"
    >다시 로그인 해주세요</failure-pop-up
  >
  <success-pop-up v-if="signOutPopupStatus" @off-modal="offSignOutPopupModal"
    >탈퇴되었습니다</success-pop-up
  >
  <success-pop-up
    v-if="completeSignUpModalStatus"
    @off-modal="offCompeteSignupModal"
    >회원가입이 완료되었습니다.</success-pop-up
  >
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import TheHomeViewContainer from "@/components/homes/TheHomeViewContainer.vue";
import NavBar from "@/components/basics/NavBar.vue";
import { computed, onBeforeMount, onBeforeUnmount, onMounted } from "vue";
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

const offChangePwPopupModal = () => {
  store.dispatch("password/toggleChangePwPopup", false);
};

const offLogOutPopupModal = () => {
  store.dispatch("account/toggleLogOutPopup", false);
};

const offFailModalStatus = () => {
  store.dispatch("account/toggleFailModalStatus", false);
};

const offRefreshFailPopupStatus = () => {
  store.dispatch("personalInfo/toggleRefreshFailPopup", false);
};

const offSignOutPopupModal = () => {
  store.dispatch("account/toggleSignOutPopup", false);
};

const offCompeteSignupModal = () => {
  store.dispatch("signup/toggleCompleteSignUpModalStatus", false);
};

onMounted(() => {
  // 로그아웃 팝업 시간제 off
  if (logOutPopupStatus) {
    setTimeout(() => offLogOutPopupModal(), 2000);
  }

  // 비밀번호 변경 팝업 시간제 off
  if (changePwPopupStatus) {
    setTimeout(() => offChangePwPopupModal(), 2000);
  }

  // 실패 팝업 시간제 off
  if (failModalStatus) {
    setTimeout(() => offFailModalStatus(), 2000);
  }

  // 탈퇴 팝업 시간제 off
  if (signOutPopupStatus) {
    setTimeout(() => offSignOutPopupModal(), 2000);
  }

  // 리프레시 실패 팝업 시간제 off
  if (refreshFailPopupStatus) {
    setTimeout(() => offRefreshFailPopupStatus(), 2000);
  }

  // 알림 상태 확인하기
  store.dispatch("notice/checkNotice");
});

const handleScroll = (event: any) => {
  const data = {
    event,
    // action: "newsFeed/getNewsFeedListData",
  };
  store.dispatch("scroll/handleScroll", data);
};

// db 받아오기
onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
});

// 저장된 스크롤 높이로 이동
onMounted(() => {
  const scrollY = computed(() => store.getters["scroll/getHomeScrollY"]);
  setTimeout(() => {
    window.scrollTo({ left: 0, top: scrollY.value });
  }, 0);
});

// 스크롤 높이 저장
onBeforeUnmount(() => {
  console.log(window.scrollY);
  store.dispatch("scroll/setHomeScrollY", window.scrollY);
});
</script>

<style scoped lang="scss">
.the-home-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  section {
    width: 100%;
    margin-top: 8px;

    @media #{$tablet} {
      width: 60%;
    }

    @media #{$pc} {
      width: 50%;
    }
  }
}
</style>
