<template>
  <!-- 헤더 -->
  <header-basic :success="false" @prevClicked="$router.go(-1)">
    <div>
      레몬나르고빚갚으리오임요
    </div>
    <button-basic
      :button-style="[buttonColor]"
      class="buttonstyle"
      @click="clickSetting"
    >
      <span class="material-icons-outlined">
        settings
      </span>
    </button-basic>
  </header-basic>
  <div class="the-my-profile-view top-view">
    <the-my-profile-basic></the-my-profile-basic>
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'
import TheMyProfileBasic from '@/components/profile/TheMyProfileBasic.vue'
import HeaderBasic from '@/components/basics/HeaderBasic.vue'
import ButtonBasic from '@/components/basics/ButtonBasic.vue'
import NavBar from '@/components/basics/NavBar.vue'
const router = useRouter()
const route = useRoute()
const store = useStore()

// 세팅 모달
const clickSetting = () => {
  alert('모달 만들기 전까진 일단 로그아웃')
  store.dispatch('account/logout')
}

// 버튼 색깔
const buttonColor = computed(() => {
  return 'sub-blank'
})
</script>

<style scoped lang="scss">
::v-deep(.header-content) {
  @include flex-center;
  justify-content: space-between;
}
.buttonstyle {
  padding: 0px;
}
</style>
