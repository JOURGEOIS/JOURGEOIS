<template>
  <div class="the-user-profile-basic">
    <round-image
      :round-image="{ image: profileImg, width: '100px' }"
    ></round-image>
    <div class="user-profile-nickname-icon">
      <p class="user-profile-nickname">{{ nickname }}</p>
      <span class="material-icons-outlined" v-if="privateNickname()">
        lock
      </span>
    </div>
    <p class="my-profile-introduce">{{ introduce }}</p>
    <div class="profile-categories">
      <div class="category">
        <p>게시물</p>
        <p class="category-count">{{ postCnt }}</p>
      </div>
      <div class="category" @click="goFollower">
        <p>팔로워</p>
        <p class="category-count">{{ followerCnt }}</p>
      </div>
      <div class="category" @click="goFollowee">
        <p>팔로잉</p>
        <p class="category-count">{{ followingCnt }}</p>
      </div>
    </div>
    <div class="part-right" v-if="uid != userId">
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
      <!-- 채팅 버튼 -->
      <span
        v-if="isLoggedIn"
        class="chat-btn"
      >
        <span class="material-icons chat-icon"> 
          mail 
        </span>
        <span class="follow-text">채팅</span></span
      >
    </div>
  </div>
</template>

<script setup lang="ts">
import RoundImage from '@/components/basics/RoundImage.vue'
import { computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'
const router = useRouter()
const route = useRoute()
const store = useStore()

const userId = computed(() => store.getters["personalInfo/getUserInfoUserId"]);
// 유저 정보 불러오기
const userInfo = computed(() => store.getters['profileDesc/getCurrentUserData'])
const uid = computed(() => userInfo.value.uid)
const profileImg = computed(() => userInfo.value.profileImg)
const nickname = computed(() => userInfo.value.nickname)
const introduce = computed(() => userInfo.value.introduce)
const postCnt = computed(() => userInfo.value.postCnt)
const followerCnt = computed (() => userInfo.value.followerCnt)
const followingCnt = computed (() => userInfo.value.followingCnt)

const isPrivate = computed(() => userInfo.value.isPrivate)
const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);

const customCocktailInfo = computed(() => {
  return store.getters["customCocktailInfo/getCustomCocktailDetail"];
});

// 계정 이름 텍스트
const privateNickname = () => {
  if (isPrivate.value === 1) {
    return true
  } else {
    return false
  }
}

const goFollower = () => {
  router.push({ name: "TheFollowerListView" , params: { userId: uid.value }});
}

const goFollowee = () => {
  router.push({ name: "TheFollowingListView" , params: { userId: uid.value }});
}

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
.the-user-profile-basic {
  @include flex(column);
  width: 100%;
  align-items: center;
  gap: 10px;
  .user-profile-nickname-icon {
    @include flex-center;
    .user-profile-nickname {
      @include font($fs-lg, $fw-bold);
    }
  }
  .user-profile-introduce {
    @include font($fs-md, $fw-regular);
  }
  .profile-categories {
    @include flex;
    gap: 48px;

    .category {
      @include flex(column);
      @include font($fs-md, $fw-medium);
      color: $navy500;
      justify-content: center;
      align-items: center;
      .category-count {
        @include font($fs-md, $fw-medium);
        color: $navy700;
      }
    }
  }
  .part-right {
    @include flex-xy(space-between, flex-start);
    width: 100%;
    gap: 25px;
    margin-top: 25px;
    .follow-btn {
      width: 48%;
      border: 1px solid $unchecked-color;
      border-radius: 1000px;
      padding: 0.5em 3em;
      @include flex-xy(center, flex-end);
      gap: 5px;
      @include shadow-feed;
      font-size: 14px;
      @include for-click;

      .follow-icon {
        @include font(17px);
      }
    }
    .chat-btn {
      width: 48%;
      border: 1px solid $unchecked-color;
      border-radius: 1000px;
      padding: 0.5em 3em;
      @include flex-xy(center, flex-end);
      gap: 5px;
      @include shadow-feed;
      font-size: 14px;
      @include for-click;
      color: $primary400;

      .chat-icon {
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

</style>
