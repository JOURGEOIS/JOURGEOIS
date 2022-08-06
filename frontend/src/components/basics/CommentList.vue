<template>
  <div v-if="commentList" class="the-comment-list">
    <div v-for="item in commentList" :key="item.pr_id">
      <comment-item :comment="item"></comment-item>
    </div>
  </div>
  <div v-else class="the-comment-none">
    <p>댓글이 없습니다</p>
    <p>😥</p>
  </div>
  <success-pop-up v-if="successPopUpStatus">
    성공적으로 수정되었습니다
  </success-pop-up>
</template>

<script setup lang="ts">
import { onBeforeMount, computed } from "vue";
import CommentItem from "@/components/basics/CommentItem.vue";
import SuccessPopUp from "@/components/modals/SuccessPopUp.vue";
import { useStore } from "vuex";
const store = useStore();

// 프롭스
const props = defineProps<{
  pageId: number;
}>();

// 댓글 정보
const commentList = computed(() => store.getters["comment/getCommentList"]);

// 인피니티 스크롤
const handleScroll = (event: any) => {
  const data = {
    event,
    action: "comment/saveCommentList",
    data: props.pageId,
  };
  store.dispatch("scroll/handleScroll", data);
};

// 댓글 정보 받기
onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);
  store.dispatch("comment/saveCommentList", props.pageId);
});

// 모달 상태
const successPopUpStatus = computed(
  () => store.getters["comment/getSuccessPopUpStatus"]
);

const deletePopUpStatus = computed(
  () => store.getters["comment/deletePopUpStatus"]
);
</script>

<style scoped lang="scss">
.the-comment-list {
  @include flex(column);
  gap: 32px;
  width: 100%;
  margin-bottom: 40px;
}

.the-comment-none {
  @include flex-center;
  gap: 4px;
  @include font($fs-main, $fw-medium);
  margin-bottom: 72px;
}
</style>
