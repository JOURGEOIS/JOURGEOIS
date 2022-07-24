<!-- 로그아웃 모달 
  모달 외부 혹은 취소 버튼을 클릭하면 모달이 off된다.
  로그아웃 버튼을 클릭 시, 
  1. 로그아웃 모달 off
  2. 모든 데이터 삭제 
  3. 홈으로 이동  
  4. 로그아웃 성공 팝업 
-->
<template>
  <modal-basic modal-color="white" @offModal="toggleLogOutModal(false)">
    <div class="logout-modal-content">
      <p>로그아웃 하시겠어요?</p>
      <div class="logout-modal-button">
        <!-- 취소 버튼 -->
        <button-basic
          :button-style="['main-blank', '50%', 'small']"
          @click="toggleLogOutModal(false)"
        >
          취소
        </button-basic>
        <!-- 로그아웃 버튼 -->
        <button-basic
          :button-style="['primary', '50%', 'small']"
          @click="clickLogout"
        >
          로그아웃
        </button-basic>
      </div>
    </div>
  </modal-basic>
</template>

<script setup lang="ts">
import ModalBasic from "@/components/basics/ModalBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { useStore } from "vuex";

const store = useStore();
const toggleLogOutModal = (value: boolean) =>
  store.dispatch("account/toggleLogOutModal", value);

const clickLogout = () => {
  store.dispatch("account/logout");
};
</script>

<style scoped lang="scss">
.logout-modal-content {
  @include flex(column);
  justify-content: center;
  align-items: center;
  gap: 36px;
  width: 300px;

  @media (max-width: 300px) {
    width: 80vw;
  }

  p {
    @include font($fs-lg, $fw-medium);
    text-align: center;
  }
}

.logout-modal-button {
  @include flex-xy(center, center);
  gap: 20px;
  width: 100%;

  button {
    @include p-component(md);
    @include shadow-modal;
  }
}
</style>
