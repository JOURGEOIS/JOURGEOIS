<!-- 
---- 사용법 ----
- 좋아요 이벤트는 emit을 통해 호출한 곳에서 eventListen
- 좋아요 여부에 따라 isLiked(boolean) 을 props로 받아 빈하트/찬하트 v-if
- 좋아요와 댓글 개수는 slot을 이용해 호출한 곳에서 넣어주는 방식
- 좋아요가 눌리면 숫자 갱신을 위해 게시물 내용을 다시 호출하는 함수를 실행
- 좋아요한 유저 탭 누르면 좋아요 유저 리스트로 이동
- 댓글 아이콘은 클릭 이벤트가 없음
-->
<template>
  <section class="like-comment-share">
    <div class="like tab">
      <span
        class="material-icons like-icon"
        v-if="data.isLiked"
        @click="clickLikeInner"
      >
        favorite
      </span>
      <span
        class="material-icons like-icon"
        v-if="!data.isLiked"
        @click="clickLikeInner"
      >
        favorite_border
      </span>
    </div>
    <div class="liked-users tab" @click="clickLikedUsers">
      <span class="material-icons liked-users-icon"> diversity_1 </span>
      <span class="button-text">
        <slot name="like"></slot>
      </span>
    </div>
    <div class="comment tab">
      <span class="material-icons-outlined comment-icon"> mode_comment </span>
      <span class="button-text">
        <slot name="comment"></slot>
      </span>
    </div>
    <div class="share tab" @click="clickShare">
      <span class="material-icons share-icon"> ios_share </span>
      <span class="button-text">공유</span>
    </div>
  </section>
</template>

<script setup lang="ts">
import axios from "axios";
import api from "../../api/api";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { ref } from "vue";
const router = useRouter();
const route = useRoute();
const store = useStore();

const props = defineProps<{
  data: {
    isLiked: boolean;
  };
}>();

const emit = defineEmits<{
  (e: "clickLike"): void;
}>();

// 좋아요 버튼을 누른 경우 Emit
const clickLikeInner = () => {
  emit("clickLike");
};

// 좋아요 숫자(좋아요한 유저 목록) 클릭
const clickLikedUsers = () => {
  const feedId = route.params.feedId;
  router.push({
    name: "TheLikedUserListView",
    params: { feedId },
  });
};

// 공유 버튼 클릭
const clickShare = () => {
  store.dispatch("share/changeShareModalClass", "start");
  store.dispatch("share/toggleShareModal", true);
};
</script>

<style scoped lang="scss">
.like-comment-share {
  width: calc(100% + 32px);
  padding: 10px;
  border-top: 1px solid $unchecked-color;
  border-bottom: 1px solid $unchecked-color;
  @include flex-xy(space-between, center);

  .comment {
    cursor: auto;
  }
}

.tab {
  @include flex-center;
  flex-grow: 1;
  padding: 10px;
  gap: 5px;
  @include font-size-sub(16px);
  @include for-click;
}

.like-icon {
  @include font(30px);
  color: $red400;
}
</style>
