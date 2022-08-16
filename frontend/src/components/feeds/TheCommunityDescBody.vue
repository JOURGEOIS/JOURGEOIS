<template>
  <section class="the-community-desc-body">
    <!-- 작성자 정보 섹션 -->
    <the-community-post-user-section
      :userInfo="feedDescInfo.followerDTO"
      :date="feedDescInfo.customCocktail.createTime"
    ></the-community-post-user-section>
    <!-- 이미지 섹션 -->
    <article
      class="community-image"
      :style="{ backgroundImage: `url(${imgLink})` }"
    ></article>
    <!-- 일반 게시글 내용 섹션 -->
    <article class="community-description">
      <p class="normal-paragraph">{{ description }}</p>
    </article>
    <!-- 수정 삭제 섹션 -->
    <article class="community-host-section" v-if="isMine">
      <div class="btn edit-btn" @click="clickEdit">수정</div>
      <div class="btn delete-btn" @click="clickDelete">삭제</div>
    </article>
  </section>
  <!-- 게시글 삭제 확인 모달 -->
  <feeds-delete-modal
    v-if="deleteModalStatus"
    :postId="postId"
    @off-modal="offDeleteModal"
  ></feeds-delete-modal>
</template>

<script setup lang="ts">
import TheCommunityPostUserSection from "@/components/feeds/TheCommunityPostUserSection.vue";
import FeedsDeleteModal from "@/components/modals/FeedsDeleteModal.vue";
import { CustomCocktail } from "../../interface";
import { useRoute, useRouter } from "vue-router";
import { computed } from "vue";
import { useStore } from "vuex";
const store = useStore();
const router = useRouter();
const route = useRoute();

const feedDescInfo = computed(() => {
  return store.getters["feedDescInfo/getCommunityDetail"];
});

const postId = computed(() => feedDescInfo?.value?.customCocktail?.postId);
const imgLink = computed(() => feedDescInfo?.value?.customCocktail?.imgLink);
const description = computed(
  () => feedDescInfo?.value?.customCocktail?.description
);
const isFollowed = computed(() => feedDescInfo?.value?.followerDTO?.isFollowed);
const isMine = computed(() => {
  return isFollowed.value === -1;
});

// 수정 클릭
const clickEdit = () => {
  router.push({
    name: "TheCommunityUpdateFormView",
    params: {
      feedId: route.params.feedId,
    },
  });
};

const deleteModalStatus = computed(
  () => store.getters["feedDescInfo/getDeleteModalStatus"]
);

const offDeleteModal = () => {
  store.dispatch("feedDescInfo/toggleDeleteModal", false);
};

// 삭제 클릭
const clickDelete = () => {
  store.dispatch("feedDescInfo/toggleDeleteModal", true);
};
</script>

<style scoped lang="scss">
.the-community-desc-body {
  @include flex(column);
  width: 100%;

  .community-image {
    width: calc(100% + 32px);
    margin: 0px -16px;
    aspect-ratio: 1/1;
    background : {
      size: cover;
      position: center center;
    }
  }

  .community-host-section {
    width: 100%;
    @include flex-xy(flex-end, center);
    @include font-size-sub(15px);

    .btn {
      padding: 10px;
      @include for-click;
    }
  }

  .community-description {
    padding: 10px 0;
  }
}

.title1 {
  @include font(15px, $fw-bold);
  color: $navy600;
  margin: 10px 0;
  user-select: none;
}

.normal-paragraph {
  @include font-size-sub(15px);
}
</style>
