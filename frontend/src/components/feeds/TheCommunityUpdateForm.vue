<template>
  <form class="the-community-form" @submit.prevent="submitCommunityForm">
    <!-- 프로필 불러오기 -->
    <div class="the-list-item-user">
      <round-image
        :round-image="{ image: profileInfo.image, width: '50px' }"
      ></round-image>
      <div class="user-info-text">
        <h1 class="user-nickname">{{ profileInfo.name }}</h1>
      </div>
    </div>
    <!-- 이미지인풋폼불러오기 -->
    <the-community-image-input
      @change-image="communityImage"
    ></the-community-image-input>
    <!-- textarea 불러오기 -->
    <the-community-textarea
      v-model.trim="communityDescValue"
    ></the-community-textarea>
  </form>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import TextareaBasic from "@/components/basics/TextareaBasic.vue";
import TheCommunityImageInput from "@/components/feeds/TheCommunityImageInput.vue";
import TheCommunityTextarea from "@/components/feeds/TheCommunityTextarea.vue";
import { ref, reactive, computed } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

// 유저 정보 불러오기
const userInfo = computed(() => store.getters["personalInfo/getUserInfo"]);

const profileImg = userInfo.value.profileImg;
const nickname = userInfo.value.nickname;
const profileInfo = reactive({
  image: profileImg,
  name: nickname,
});

// image input
let communityImageValue = reactive({});
const communityImage = (data: object) => {
  communityImageValue = data;
};

// description input
const communityDescValue = computed({
  get: () =>
    store.getters["feedDescInfo/getCommunityDetail"].customCocktail.description,
  set: (newValue) => store.dispatch("feedDescInfo/setDescription", newValue),
});

const submitCommunityForm = () => {
  if (!(communityImageValue instanceof File)) {
    const data = {
      description: communityDescValue.value,
      img: "",
      postId: route.params.feedId,
    };
    store.dispatch("createFeed/updateCommunityForm", data);
  } else {
    const data = {
      description: communityDescValue.value,
      img: communityImageValue,
      postId: route.params.feedId,
    };
    store.dispatch("createFeed/updateCommunityForm", data);
  }
};
</script>

<style scoped lang="scss">
.the-list-item-user {
  @include flex(row);
  @include font($fs-sm, $fw-regular);
  align-items: center;
  gap: 7px;
  margin-bottom: 15px;
}
</style>
