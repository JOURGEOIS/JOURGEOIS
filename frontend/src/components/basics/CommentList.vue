<!-- 댓글 리스트 컴포넌트 -->
<template>
  <!--  api를 통해 받은 comment 정보를 통해 댓글을 사용자에게 보여준다.  -->
  <div v-if="commentList" class="the-comment-list">
    <!-- comment-item -->
    <div v-for="item in commentList" :key="item.pr_id">
      <comment-item :comment="item" :page-id="pageId"></comment-item>
    </div>
  </div>

  <!-- 댓글이 없을 경우 보여지는 화면  -->
  <div v-else class="the-comment-none">
    <p>댓글이 없습니다</p>
    <p>😥</p>
  </div>

  <!-- 댓글 수정 성공 팝업 -->
  <success-pop-up v-if="successPopUpStatus">
    성공적으로 수정되었습니다
  </success-pop-up>

  <!-- 댓글 삭제 확인 모달 -->
  <comment-delete-modal
    v-if="deleteModalStatus"
    :page-id="pageId"
  ></comment-delete-modal>
</template>

<script setup lang="ts">
import { onBeforeMount, computed, watch } from "vue";
import CommentItem from "@/components/basics/CommentItem.vue";
import CommentDeleteModal from "@/components/modals/CommentDeleteModal.vue";
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

// 마운트 되기 전에 댓글 정보 받기
onBeforeMount(() => {
  window.addEventListener("scroll", handleScroll);

  // 리셋
  store.dispatch("comment/resetCommentData");
  store.dispatch("comment/toggleSuccessPopUpStatus", false);
  store.dispatch("comment/toggleDeleteModalStatus", false);

  // 데이터 받기
  store.dispatch("comment/saveCommentList", props.pageId);
});

// 모달 상태
const successPopUpStatus = computed(
  () => store.getters["comment/getSuccessPopUpStatus"]
);

const deleteModalStatus = computed(
  () => store.getters["comment/getDeleteModalStatus"]
);

// 시간제 모달
watch(successPopUpStatus, () => {
  if (successPopUpStatus) {
    setTimeout(
      () => store.dispatch("comment/toggleSuccessPopUpStatus", false),
      2000
    );
  }
});
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
