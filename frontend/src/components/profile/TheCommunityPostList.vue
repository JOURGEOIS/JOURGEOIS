<template>
  <article v-for="community in userCommunityPostData" :key="community.postId">
    <the-community-post-item :community="community"></the-community-post-item>
  </article>
  <section>
    <div class="community-post-none" v-if="isEmpty && isPrivate">
      <p><span class="material-icons-outlined">lock</span>비공개 계정입니다.</p>
    </div>
  </section>
</template>

<script setup lang="ts">
import TheCommunityPostItem from "@/components/profile/TheCommunityPostItem.vue";
import { ref, computed, onBeforeMount, onUnmounted, watch } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const userCommunityPostData = computed(
  () => store.getters["profileDesc/getCurrentUserPostCommunity"]
);

const userInfo = computed(
  () => store.getters["profileDesc/getCurrentUserData"]
);
const isPrivate = computed(() => userInfo.value.isPrivate);

const isEmpty = ref(false);
setTimeout(() => {
  if (userCommunityPostData.value.length === 0) {
    isEmpty.value = true;
  }
}, 200);

// 인피니티 스크롤
const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "profileDesc/getCurrentUserPostCommunityData",
    data: route.params.userId,
  };
  store.dispatch("scroll/handleScroll", data);
};

// 인피니티 스크롤을 연동, 처음 데이터 가져오기
onBeforeMount(() => {
  // store.dispatch("profileDesc/getCurrentUserData", route.params.userId);
  window.addEventListener("scroll", handleScroll);
  store.dispatch(
    "profileDesc/getCurrentUserPostCommunityData",
    route.params.userId
  );
});

// unmount될 때, 페이지와 리스트를 리셋한다.
onUnmounted(() => {
  store.dispatch("profileDesc/resetCurrentUserPost");
  window.removeEventListener("scroll", handleScroll);
});

const paramsUserId = computed(() => route.params.userId);
watch(paramsUserId, () => {
  store.dispatch(
    "profileDesc/getCurrentUserPostCommunityData",
    paramsUserId.value
  );
});
</script>

<style scoped lang="scss">
article {
  width: 100%;
  @include flex(column);
  gap: 15px;
}
.community-post-none {
  width: 100%;
  padding: 64px 70px;
  border-radius: 16px;
  background-color: $white200;
  @include font($fs-main, $fw-bold);
  text-align: center;
  p {
    @include flex-center;
  }

  @media #{$tablet} {
    @include font($fs-lg, $fw-bold);
    width: 450px;
  }
}
</style>
