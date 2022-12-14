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
          <!-- <span v-if="isUpdated">수정 {{ updateTimeDelta }}</span> -->
          <span class="updated" v-if="isUpdated">수정됨</span>
        </div>
      </div>
    </div>
    <div class="part-right">
      <!-- 팔로우/팔로잉 버튼 -->
      <span
        v-if="isFollowed !== -1 && isLoggedIn"
        class="follow-btn"
        :class="{ following: isFollowed, follow: !isFollowed }"
        @click="clickFollowBtn"
      >
        <span class="material-icons-outlined follow-icon" v-if="!isFollowed">
          person_add
        </span>
        <span class="follow-text">{{ followBtnText }}</span></span
      >
    </div>
  </section>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { User } from "../../interface";
import { calcDateDelta } from "../../functions/date";
import { watch, computed } from "vue";
import { useStore } from "vuex";
import { useRouter, useRoute } from "vue-router";
const router = useRouter();
const route = useRoute();
const store = useStore();

// 로그인 여부
const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);

const customCocktailInfo = computed(() => {
  return store.getters["customCocktailInfo/getCustomCocktailDetail"];
});

const uid = computed(() => customCocktailInfo?.value?.followerDTO?.uid);
const nickname = computed(
  () => customCocktailInfo?.value?.followerDTO?.nickname
);
const profileImg = computed(
  () => customCocktailInfo?.value?.followerDTO?.profileImg
);

const createTime = computed(
  () => customCocktailInfo?.value?.customCocktail?.createTime
);
const createTimeDelta = computed(() => calcDateDelta(createTime.value));
const updateTime = computed(
  () => customCocktailInfo?.value?.customCocktail?.lastUpdateTime
);
const updateTimeDelta = computed(() => calcDateDelta(updateTime.value));
const isUpdated = computed(
  () => customCocktailInfo?.value?.customCocktail?.isUpdated
);

// 작성자 프로필로 이동 함수
const goProfile = () => {
  router.push({
    name: "TheUserProfileView",
    params: {
      userId: uid.value,
    },
  });
};

// 팔로우/팔로잉 텍스트
const followBtnText = computed(() => (isFollowed.value ? "팔로잉" : "팔로우"));

// 팔로우/팔로잉 버튼 클릭
const isFollowed = computed(
  () => customCocktailInfo?.value?.followerDTO?.isFollowed
);

watch(customCocktailInfo?.value?.followerDTO?.isFollowed, () => {
  const isFollowed = computed(
    () => customCocktailInfo?.value?.followerDTO?.isFollowed
  );
});

const clickFollowBtn = () => {
  if (isFollowed.value) {
    store.dispatch("follow/unfollow", { uid: uid.value });
  } else {
    store.dispatch("follow/follow", { uid: uid.value });
  }

  store.dispatch("customCocktailInfo/toggleFollowCustomCocktail");
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
        gap: 5px;
        @include font-size-sub(12px);
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
      @include flex-xy(center, flex-end);
      gap: 5px;
      @include shadow-feed;
      font-size: 14px;
      @include for-click;

      .follow-icon {
        @include font(17px);
      }
    }
  }
}

.following {
  color: $unchecked-color;
}

.follow {
  color: $red400;
}

.updated {
  color: $navy-color;
}
</style>
