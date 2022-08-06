<template>
<the-community-notice-modal></the-community-notice-modal>
<div class="the-community-notice-button">
  <button-basic
      :button-style="['sub', '130px']"
      class="buttonstyle"
      @click="toggleCommunityNoticeModal"
    >
      이용규칙 전체보기 >
  </button-basic>
</div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useStore } from "vuex";
import ButtonBasic from '@/components/basics/ButtonBasic.vue'
import TheCommunityNoticeModal from '@/components/feeds/TheCommunityNoticeModal.vue';
const store = useStore();

// 서비스 이용 약관 동의
const toggleCommunityNoticeModal = () => {
  store.dispatch("signup/toggleServiceUseModal");
};
</script>

<style scoped lang="scss">
.the-community-notice-button{
  margin-bottom: 5px;
  .buttonstyle {
    @include font($fs-sm, $fw-regular);
    padding: 2px 6px;
  }
}
</style>