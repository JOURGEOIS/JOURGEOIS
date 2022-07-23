<template>
  <div class="change-user-info-view">
    <section>
      <div class="change-user-info-link">
        <p @click="toggleLogOutModal(true)">로그아웃</p>
        <p>회원탈퇴</p>
      </div>
    </section>
  </div>
  <the-log-out-modal v-if="logOutModalStatus"></the-log-out-modal>
</template>

<script setup lang="ts">
import TheLogOutModal from "@/components/accounts/TheLogOutModal.vue";
import { useStore } from "vuex";
import { computed } from "vue";

const store = useStore();
const logOutModalStatus = computed(
  () => store.getters["account/getLogOutModalStatus"]
);
const toggleLogOutModal = (value: boolean) =>
  store.dispatch("account/toggleLogOutModal", value);
</script>

<style scoped lang="scss">
.change-user-info-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
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
    @include flex-center;
    gap: 20px;
    width: 100%;
    p {
      padding: 8px;
      color: $gray;
      @include font($fs-btn, $fw-medium);
    }
  }
}
</style>
