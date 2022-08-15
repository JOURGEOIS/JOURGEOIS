<template>
  <div class="the-list-item-user">
    <div class="part-left" @click="goProfile">
      <round-image :round-image="userImage"></round-image>
      <div class="user-info-text">
        <h1 class="user-nickname">{{ data?.nickname }}</h1>
        <p class="user-introduce">{{ data?.introduce }}</p>
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
  </div>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { ref, computed } from "vue";
import { useStore } from "vuex";
import { useRouter, useRoute } from "vue-router";
import { User } from "../../interface";
const router = useRouter();
const route = useRoute();
const store = useStore();

const props = defineProps<{
  data: User;
}>();

// 로그인 여부
const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);

const isFollowed = ref(props.data.isFollowed);

// 팔로우/팔로잉 텍스트
const followBtnText = computed(() => (isFollowed ? "팔로잉" : "팔로우"));

// 팔로우/팔로잉 버튼 클릭
const uid = props.data.uid;
const clickFollowBtn = () => {
  if (isFollowed.value) {
    store.dispatch("follow/unfollow", { uid });
    isFollowed.value = 0;
  } else {
    store.dispatch("follow/follow", { uid });
    isFollowed.value = 1;
  }
};

const goProfile = () => {
  router.push({
    name: "TheUserProfileView",
    params: {
      userId: props.data.uid,
    },
  });
};

// 유저 이미지
const userImage = {
  image: props.data.profileImg,
  width: "50px",
};
</script>

<style scoped lang="scss">
.the-list-item-user {
  @include flex-xy(space-between, center);
  padding: 10px;
  gap: 10px;
  border-bottom: 1px solid $seperate-color;
  @include list-hover;

  .part-left {
    @include flex-xy(flex-start, center);
    @include text-overflow-ellipsis;
    gap: 10px;
    .user-info-text {
      @include flex(column);
      @include text-overflow-ellipsis;
      gap: 3px;

      .user-nickname {
        @include text-overflow-ellipsis;
        @include font(15px, $fw-medium);
      }
      .user-introduce {
        @include text-overflow-ellipsis;
        @include font-size-sub(11px);
      }
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
      min-width: 90px;

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
</style>
