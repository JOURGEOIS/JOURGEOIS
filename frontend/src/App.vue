<template>
  <router-view />
  <failure-pop-up-app
    v-if="failModalAppStatus"
    @off-modal="offFailurePopUpAPP"
    >{{ failMessage }}</failure-pop-up-app
  >
  <failure-pop-up v-if="errorModalAppStatus" @off-modal="offFailurePopUp">
    {{ errorModalAppMessage }}
  </failure-pop-up>
  <success-pop-up v-if="successModalAppStatus" @off-modal="offSuccessPopUp">
    {{ successModalAppMessage }}
  </success-pop-up>
  <the-create-post-modal v-if="createFeedModalStatus"></the-create-post-modal>
  <share-modal v-if="shareModalStatus"></share-modal>
  <top-button></top-button> 
  <div class="bottom-area"></div>
</template>

<script setup lang="ts">
import FailurePopUpApp from "@/components/modals/FailurePopUpApp.vue";
import SuccessPopUpApp from "@/components/modals/SuccessPopUpApp.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
import SuccessPopUp from "@/components/modals/SuccessPopUp.vue";
import TheCreatePostModal from "@/components/feeds/TheCreatePostModal.vue";
import ShareModal from "@/components/basics/ShareModal.vue";
import TopButton from "@/components/basics/TopButton.vue";
import { useRoute, useRouter } from "vue-router";
import { computed, onMounted } from "vue";
import { useStore } from "vuex";
const router = useRouter();
const route = useRoute();
const store = useStore();

const failModalAppStatus = computed(
  () => store.getters["modal/getFailModalAppStatus"]
);
const failMessage = "문제가 발생했습니다. 다시 시도해주세요.";

const createFeedModalStatus = computed(
  () => store.getters["createFeed/getCreateFeedModalStatus"]
);

const shareModalStatus = computed(
  () => store.getters["share/getShareModalStatus"]
);

const errorModalAppStatus = computed(
  () => store.getters["modal/getErrorModalAppStatus"]
);

const errorModalAppMessage = computed(
  () => store.getters["modal/getErrorModalAppMessage"]
);

const offFailurePopUpAPP = () => {
  store.dispatch("modal/toggleFailModalAppStatus", false);
};

const offFailurePopUp = () => {
  store.dispatch("modal/toggleErrorModalAppStatus", false);
};

const successModalAppStatus = computed(
  () => store.getters["modal/getSuccessModalAppStatus"]
);

const offSuccessPopUp = () => {
  store.dispatch("modal/toggleSuccessModalAppStatus", false);
};

const successModalAppMessage = computed(
  () => store.getters["modal/getSuccessModalAppMessage"]
);

// deviceType 확인
onMounted(() => {
  store.dispatch("navbar/setDeviceType");
});
</script>

<style lang="scss">
#app {
  display: flex;
  justify-content: center;
  margin-left: $margin-mobile;
  margin-right: $margin-mobile;
  padding-bottom: 60px !important;
}

.top-view {
  margin-top: 100px !important;
  @media #{$tablet} {
    margin-top: 128px !important;
  }
}

.top-view-no-margin {
  margin-top: 74px !important;

  // @media #{$tablet} {
  //   margin-top: 74px !important;
  // }
}


</style>
