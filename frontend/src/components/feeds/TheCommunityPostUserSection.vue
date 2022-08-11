<template>
  <section class="post-user-section">
    <div class="part-left">
      <round-image
        :round-image="{ image: profileImg }"
        class="user-info-profile"
        @click="goProfile"
      ></round-image>
      <div class="user-info-text">
        <div class="user-nickname" @click="goProfile">{{ nickname }}</div>
        <div class="date-line">
          <span>{{ createTimeDelta }}</span>
          <span class="updated" v-if="isUpdated">수정됨</span>
        </div>
      </div>
    </div>
    <div class="part-right">
      <!-- 팔로우/팔로잉 버튼 -->
      <span
        v-if="isFollowed !== -1"
        class="follow-btn"
        :class="{ following: isFollowed, follow: !isFollowed }"
        @click="clickFollowBtn"
      >
        <span class="material-icons-outlined follow-icon" v-if="!isFollowed">
          person_add
        </span>
        <span class="follow-text">{{ followBtnText }}</span>
      </span>
    </div>
  </section>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { User } from "../../interface";
import { calcDateDelta, compareDate } from "../../functions/date";
import { ref, computed } from "vue";
import { useStore } from "vuex";
// import { useRouter, useRoute } from "vue-router";
// const router = useRouter();
// const route = useRoute();
const store = useStore();

const feedDescInfo = computed(() => {
  return store.getters["feedDescInfo/getCommunityDetail"];
});
const uid = computed(() => feedDescInfo?.value?.followerDTO?.uid);
const nickname = computed(() => feedDescInfo?.value?.followerDTO?.nickname);
const profileImg = computed(() => feedDescInfo?.value?.followerDTO?.profileImg);
const createTime = computed(
  () => feedDescInfo?.value?.customCocktail?.createTime
);
const createTimeDelta = computed(() => calcDateDelta(createTime.value));
const updateTime = computed(
  () => feedDescInfo?.value?.customCocktail?.lastUpdateTime
);
const isUpdated = computed(
  () => feedDescInfo?.value?.customCocktail?.isUpdated
);
// 작성자 프로필로 이동 함수
const goProfile = () => {
  alert("유저 프로필 페이지로 가라!");
  // 아직 파일명 및 url 구조 모름... 추가되면 바꾸기
  // router.push({ name: "TheProfileView", params: { userId: uid } });
};

// 팔로우/팔로잉 텍스트
const followBtnText = computed(() => (isFollowed.value ? "팔로잉" : "팔로우"));

// 팔로우/팔로잉 버튼 클릭
const isFollowed = ref(feedDescInfo?.value?.followerDTO?.isFollowed);
const clickFollowBtn = () => {
  if (isFollowed.value) {
    store.dispatch("follow/unfollow", { uid });
    isFollowed.value = 0;
  } else {
    store.dispatch("follow/follow", { uid });
    isFollowed.value = 1;
  }
};
</script>

<style scoped lang="scss">
.post-user-section {
  width: calc(100% + 32px);
  margin-left: -16px;
  padding: 15px 16px;
  border-bottom: 1px solid $unchecked-color;
  @include flex-xy(space-between, center);

  .part-left {
    @include flex-xy(flex-start, center);
    gap: 10px;

    .round-image {
      width: 45px;
      height: 45px;
    }
    .user-info-text {
      @include for-click;
      @include flex(column);
      gap: 3px;
      .user-nickname {
        @include font(15px, $fw-medium);
      }
      .date-line {
        @include flex;
        @include font-size-sub(12px);
        gap: 5px;
        .updated {
          color: $navy400;
        }
      }
    }
    .user-info-profile {
      @include for-click;
    }
  }
  .part-right {
    .follow-btn {
      border: 1px solid $unchecked-color;
      border-radius: 1000px;
      padding: 0.3em 1em;
      @include flex-center;
      gap: 10px;
      @include shadow-feed;
      font-size: 15px;
      @include for-click;

      // .follow-icon {
      //   @include font(19px);
      // }
    }
  }
}

.following {
  color: $unchecked-color;
}

.follow {
  color: $red400;
}
</style>