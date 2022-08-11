<template>
  <teleport to="body">
    <div class="create-post-modal">
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
          <article class="logOut set-btn" @click="logOut">
            <div class="btn-text">로그아웃</div>
          </article>
        </section>
      </div>
    </div>
  </teleport>
</template>

<script setup lang="ts">
import { computed} from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
const router = useRouter();
const store = useStore();

// 애니메이션 상태
const animation = computed(
  // store 만들어지면 변경
  () => store.getters["createFeed/getCreateFeedModalClass"]
);

// 필터 off
const clickXIcon = () => {
  // store 만들어지면 변경
  // store.dispatch("createFeed/changeCreateFeedModalClass", "end");
  // setTimeout(
  //   () => store.dispatch("createFeed/toggleCreateFeedModal", false),
  //   200
  // );
};

// 비밀번호 변경
const changePassword = () => {
  // store 만들어지면 변경
  // store.dispatch("createFeed/toggleCreateFeedModal", false);
  router.push({ name: "TheChangePwView" });
};

// 개인정보 수정
const editPersonalInfo = () => {
  // store 만들어지면 변경
  // store.dispatch("createFeed/toggleCreateFeedModal", false);
  router.push({ name: "TheChangeUserView" });
};

// 프로필 공개 설정
const profilePublicSet = () => {
  // store 만들어지면 변경
  // store.dispatch("createFeed/toggleCreateFeedModal", false);
  // router.push({ name: "TheChangePwView" });
};

// 로그아웃
const logOut = () => {
  // store 만들어지면 변경
  // store.dispatch("createFeed/toggleCreateFeedModal", false);
  store.dispatch("account/logout");
};

// 아이폰인지 확인
const deviceType: string = store.getters["navbar/getDeviceType"];
const isIphone = computed(() => {
  return deviceType === "iphone";
});
</script>

<style scoped lang="scss">
  // store 만들어지면 레이아웃 확인 후 추가
</style>