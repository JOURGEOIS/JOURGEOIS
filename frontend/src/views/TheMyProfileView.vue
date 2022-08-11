<template>
  <!-- 헤더 -->
  <header-basic :success="false" @prevClicked="$router.go(-1)">
    <div>
      내 프로필
    </div>
    <button-basic
      :button-style="[buttonColor,'24px']"
      class="buttonstyle"
      @click="clickSetting"
    >
      <span class="material-icons-outlined">
        settings
      </span>
    </button-basic>
  </header-basic>
  <the-settings-modal v-if="settingsModalStatus"></the-settings-modal>
  <div class="the-my-profile-view top-view">
    <the-my-profile-basic></the-my-profile-basic>
  </div>
  <the-log-out-modal
    v-if="logOutModalStatus"
    @off-modal="toggleLogOutModal(false)"
  ></the-log-out-modal>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { onBeforeRouteLeave, useRoute, useRouter } from 'vue-router'
import TheMyProfileBasic from '@/components/profile/TheMyProfileBasic.vue'
import TheSettingsModal from '@/components/profile/TheSettingsModal.vue'
import TheLogOutModal from "@/components/accounts/TheLogOutModal.vue";
import HeaderBasic from '@/components/basics/HeaderBasic.vue'
import ButtonBasic from '@/components/basics/ButtonBasic.vue'
import NavBar from '@/components/basics/NavBar.vue'
const router = useRouter()
const route = useRoute()
const store = useStore()

// 세팅 모달
const settingsModalStatus = computed(
  () => store.getters["settings/getSettingsModalStatus"]
);
//=========
const toggleSettingsModal = () => {
  store.dispatch("settings/toggleSettingsModal");
};

const clickSetting = () => {
  store.dispatch("settings/changeSettingsModalClass", "start");
  store.dispatch("settings/toggleSettingsModal", true);
}

onBeforeRouteLeave((to, from, next) => {
  if (settingsModalStatus.value) {
    store.dispatch("settings/changeSettingsModalClass", "end");
    setTimeout(
      () => store.dispatch("settings/toggleSettingsModal", false),
      200
    );
  } else {
    next();
  }
});

// 로그아웃 모달
const logOutModalStatus = computed(
  () => store.getters["account/getLogOutModalStatus"]
);
const toggleLogOutModal = (value: boolean) =>
  store.dispatch("account/toggleLogOutModal", value);

// 리셋
onMounted(() => {
  toggleLogOutModal(false);
});

// 버튼 색깔
const buttonColor = computed(() => {
  return 'sub-blank'
})
</script>

<style scoped lang="scss">
::v-deep(.header-content) {
  @include flex-center;
  position: relative;

  justify-content: center;
}
.buttonstyle {
  padding: 0px;
  position: absolute;
  left: 50%;
  transform: translate(150px, 0px);
}
</style>
