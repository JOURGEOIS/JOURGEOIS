<template>
  <div class="the-my-profile-basic">
    <round-image
      :round-image="{ image: profileImg, width: '100px' }"
    ></round-image>
    <div class="my-profile-nickname-icon">
      <p class="my-profile-nickname">{{ nickname }}</p>
      <span class="material-icons-outlined">
        lock
      </span>
    </div>
    <p class="my-profile-introduce">{{ introduce }}</p>
    <div class="profile-categories">
      <div class="category">
        <p>게시물</p>
        <p class="category-count">999+</p>
      </div>
      <div class="category">
        <p>팔로워</p>
        <p class="category-count">999+</p>
      </div>
      <div class="category">
        <p>팔로잉</p>
        <p class="category-count">999+</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import RoundImage from '@/components/basics/RoundImage.vue'
import { onBeforeMount, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useStore } from 'vuex'
const route = useRoute()
const store = useStore()

const myProfileInfo = computed(
  () => store.getters["profileDesc/getCurrentUserData"]
);

// 유저 정보 불러오기
console.log(myProfileInfo?.value)
const profileImg = computed(() => myProfileInfo?.value?.profileImg)
const nickname = computed(() => myProfileInfo?.value?.nickname)
const introduce = computed(() => myProfileInfo?.value?.introduce)

</script>

<style scoped lang="scss">
.the-my-profile-basic {
  @include flex(column);
  align-items: center;
  gap: 10px;
  .my-profile-nickname-icon {
    @include flex-center;
    .my-profile-nickname {
      @include font($fs-lg, $fw-bold);
    }
  }
  .my-profile-introduce {
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
}
</style>
