<template>
  <modal-basic modal-color="white" @offModal="offModal">
    <div class="cocktail-following-modal">
      <div class="cocktail-following-title">북마크</div>
      <div class="cocktail-following-content">
        <div class="cocktail-following-none">
          <p>해당 칵테일을 북마크한 유저가 없습니다</p>
          <p>😥</p>
        </div>
      </div>
    </div>
  </modal-basic>
</template>

<script setup lang="ts">
import ModalBasic from "@/components/basics/ModalBasic.vue";
import { useStore } from "vuex";
const store = useStore();

// 외부 클릭 시 모달이 닫힌다.
const offModal = () => {
  store.dispatch("cocktailDesc/toggleBookmarkModalStatus", false);
};
</script>

<style scoped lang="scss">
.cocktail-following-modal {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  gap: 32px;
  max-height: 50vh;

  .cocktail-following-title {
    @include font($fs-lg, $fw-bold);
    text-align: center;
    margin-top: -8px;
  }
  .cocktail-following-content {
    @include flex(column);
    justify-content: center;
    align-items: center;
    gap: 16px;
  }
  .cocktail-following-none > p {
    @include font($fs-main, $fw-bold);
    text-align: center;
    margin-bottom: 8px;
  }
}

.cocktail-following-none > p:last-child {
  font-size: $fs-xxl;
}
</style>
