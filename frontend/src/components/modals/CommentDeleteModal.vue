<!-- 삭제 모달
  모달 외부 혹은 취소 버튼을 클릭하면 모달이 off된다.
  삭제 버튼을 클릭 시, 
  1. 로그아웃 모달 off
  2. 모든 데이터 삭제 
  3. 홈으로 이동  
  4. 로그아웃 성공 팝업 
-->
<template>
  <modal-basic modal-color="white" @off-modal="toggleDeleteModalStatus(false)">
    <div class="logout-modal-content">
      <p>정말 삭제하시겠어요?</p>
      <div class="logout-modal-button">
        <!-- no 버튼 -->
        <button-basic
          :button-style="['main-blank', '50%', 'small']"
          @click="toggleDeleteModalStatus(false)"
        >
          취소
        </button-basic>
        <!-- yes 버튼 -->
        <button-basic
          :button-style="['primary', '50%', 'small']"
          @click="clickDeleteComment"
        >
          삭제
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

// 프롭스
const props = defineProps<{
  pageId: number;
}>();

const toggleDeleteModalStatus = (value: boolean) => {
  store.dispatch("comment/toggleDeleteModalStatus", value);
};

const clickDeleteComment = () => {
  toggleDeleteModalStatus(false);
  store.dispatch("comment/commentDelete", props.pageId);
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
