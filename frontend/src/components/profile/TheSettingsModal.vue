<template>
  <teleport to="body">
    <div class="settings-modal">
      <div
        class="container"
        :class="animation"
        :style="[isIphone ? { height: '350px' } : { height: '300px' }]"
      >
        <!-- filter: header -->
        <section class="header-section">
          <span class="material-icons invisible"> close </span>
          <p>설정</p>
          <span class="material-icons" @click="clickXIcon"> close </span>
        </section>
        <hr />
        <!-- 버튼 부분 -->
        <section class="btn-section">
          <!-- 비밀번호 변경 -->
          <article
            class="change-password set-btn"
            @click="changePassword"
          >
            <div class="btn-text">비밀번호 변경</div>
          </article>
          <article class="edit-personal-info set-btn" @click="editPersonalInfo">
            <div class="btn-text">개인정보 수정</div>
          </article>
          <article class="profile-public-set set-btn" @click="profilePublicSet">
            <div class="btn-text">프로필 공개 설정</div>
          </article>
          <article class="logOut set-btn">
            <div @click="toggleLogOutModal(true)" class="btn-text">로그아웃</div>
          </article>
        </section>
      </div>
    </div>
    <the-log-out-modal
      v-if="logOutModalStatus"
      @off-modal="toggleLogOutModal(false)"
    ></the-log-out-modal>
  </teleport>
</template>

<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import TheLogOutModal from "@/components/accounts/TheLogOutModal.vue";
const router = useRouter();
const store = useStore();

// 애니메이션 상태
const animation = computed(
  // store 만들어지면 변경
  () => store.getters["settings/changeSettingsModalClass"]
);

// 필터 off
const clickXIcon = () => {
  // store 만들어지면 변경
  store.dispatch("settings/changeSettingsModalClass", "end");
  setTimeout(
    () => store.dispatch("settings/toggleSettingsModal", false),
    200
  );
};

// 비밀번호 변경
const changePassword = () => {
  // store 만들어지면 변경
  store.dispatch("settings/toggleSettingsModal", false);
  router.push({ name: "TheChangePwView" });
};

// 개인정보 수정
const editPersonalInfo = () => {
  // store 만들어지면 변경
  store.dispatch("settings/toggleSettingsModal", false);
  router.push({ name: "TheChangeUserView" });
};

// 프로필 공개 설정
const profilePublicSet = () => {
  // store 만들어지면 변경
  store.dispatch("settings/toggleSettingsModal", false);
  // router.push({ name: "TheChangePwView" });
};


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

// 아이폰인지 확인
const deviceType: string = store.getters["navbar/getDeviceType"];
const isIphone = computed(() => {
  return deviceType === "iphone";
});
</script>

<style scoped lang="scss">
@keyframes start {
  to {
    transform: translate3d(0, 0, 0);
  }
  from {
    transform: translate3d(0, 100%, 0);
  }
}

@keyframes end {
  to {
    transform: translate3d(0, 100%, 0);
  }
  from {
    transform: translate3d(0, 0, 0);
  }
}

.settings-modal {
  @include flex-xy(center, center);
  position: fixed;
  top: 0;
  height: 100vh;
  z-index: 10;
  width: 100vw;
  background-color: rgba(0, 0, 0, 0.7);
  .container {
    @include flex(column);
    gap: 11px;
    position: absolute;
    bottom: 0px;
    width: 100%;
    max-width: 600px;
    height: 300px;
    border-top-left-radius: 30px;
    border-top-right-radius: 30px;
    padding: 0 16px;
    background-color: white;

    @media #{$tablet} {
      width: 64%;
    }

    @media (min-height: 750px) {
      height: 300px;
    }

    .header-section {
      @include flex-xy(space-between, flex-start);
      margin-top: 24px;

      p {
        &:first-child {
          @include font($fs-main, $fw-medium);
          color: $main-color;
        }
      }
      .material-icons-outlined {
        position: absolute;
        right: 5px;
        width: 42.78px;
        text-align: center;
        cursor: pointer;
      }
    }

    hr {
      width: calc(100% + 32px);
      margin-left: -16px;
      height: 1px;
      border: 0;
      background-color: $unchecked-color;
    }

    .btn-section {
      @include flex-xy(space-around, center);
      margin-top: 10px;
    }
  }
}

.post-btn {
  @include flex(column);
  @include flex-xy(center, center);
  @include for-click;
  gap: 10px;
  .btn-icon {
    width: 100px;
    height: 100px;
    border: 1px solid $white150;
    border-radius: 20px;
    background-color: $white100;
    @include shadow-popup1;
    @include flex-center;
    @include font-size-sub(15px);

    .material-icons-outlined {
      @include font-size-sub(50px);
    }
  }
  .btn-text {
    @include font-size-sub(15px);
  }
}

.start {
  animation: start 0.3s ease-in-out;
}

.end {
  animation: end 0.3s ease-in-out;
}

.invisible {
  visibility: hidden;
}
</style>