<template>
  <div class="the-following-item">
    <div class="part-left">
      <round-image
        :round-image="userImage"
        class="the-following-profile"
      ></round-image>
      <div class="user-info-text">
        <h1 class="user-nickname">{{ nickname }}</h1>
        <p class="user-introduce">{{ introduce }}</p>
      </div>
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
  followee: User;
}>();

const nickname = props.followee.nickname;
const introduce = props.followee.introduce;
// 로그인 여부
const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);

const isFollowed = ref(props.followee.isFollowed);

// 팔로우/팔로잉 텍스트
const followBtnText = computed(() => {
  if (isFollowed.value === 1) {
    return "팔로잉";
  } else if (isFollowed.value === 0) {
    return "팔로우";
  }
});

// 팔로우/팔로잉 버튼 클릭
const uid = props.followee.uid;
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
      userId: props.followee.uid,
    },
  });
};

// 유저 이미지
const userImage = {
  image: props.followee.profileImg,
  width: "50px",
};
</script>

<style scoped lang="scss">
.the-following-item {
  @include flex-xy(space-between, center);
  gap: 10px;
  width: 100%;
  padding: 10px;
  border-bottom: 1px solid $seperate-color;
  @include list-hover;

  .part-left {
    @include flex-xy(flex-start, center);
    @include text-overflow-ellipsis;
    gap: 10px;

    .the-following-profile {
      flex-shrink: 0;
    }
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
}
</style>
