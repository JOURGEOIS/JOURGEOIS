<template>
  <header class="notice-header-container">
    <div>
      <img
        class="main-logo"
        src="https://user-images.githubusercontent.com/86189596/184537312-81ae4cbf-b7af-494a-86a8-f6057bfa0820.png"
        alt=""
      />
    </div>
    <button class="notice-icon" @click="noticeClick">
      <span class="material-icons" :class="noticeColor"> notifications </span>
    </button>
  </header>
</template>

<script setup lang="ts">
import { useStore } from "vuex";
import { computed } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();
const store = useStore();

// 알림 상태 확인 (신규 알림이 있는지 확인)
const noticeStatus = computed(() => store.getters["notice/getNoticeStatus"]);
const noticeColor = computed(() => {
  if (noticeStatus.value) {
    return "red-notice";
  } else {
    return "black-notice";
  }
});

const noticeClick = () => {
  router.push({ name: "TheNoticeView" });
};
</script>

<style scoped lang="scss">
.main-logo {
  width: 130px;
  height: auto;
}
.notice-header-container {
  @include flex-xy(space-between, center);
  @include shadow-feed;
  flex-wrap: wrap;
  position: fixed;
  width: 100vw;
  height: 74px;
  z-index: 5;
  padding: 12px 16px;
  background-color: $white !important;

  @media #{$tablet} {
    padding: 12px 15%;
  }
  @media #{$pc} {
    padding: 12px 20%;
  }

  .material-icons {
    padding: 0;
    align-self: center;
    // margin-top: 5px;
    @include font($fs-title, $fw-thin);

    @media #{$tablet} {
      font-size: $fs-xl;
      // margin-top: 5px;
    }

    @media #{$pc} {
    }
    x &:hover {
      cursor: pointer;
    }
  }

  .header-content {
    @include font($fs-title, $fw-medium);
    text-align: center;
    flex-grow: 1;

    @media #{$tablet} {
      font-size: $fs-xl;
    }
  }

  .notice-icon {
    width: fit-content;
    padding: 0;
    letter-spacing: $ls-main;
    @include font($fs-main, $fw-medium);
    background-color: white;
    font-size: $fs-title;

    .material-icons {
      padding: 0;
      align-self: center;
      // margin-top: 5px;
      font-size: 25px;
    }
    @media #{$tablet} {
      font-size: 35px;
    }
  }
}

.red-notice {
  color: $danger-color;
}

.black-notice {
  color: $white400;
}
</style>
