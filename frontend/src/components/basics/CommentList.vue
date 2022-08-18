<!-- 댓글 리스트 컴포넌트 -->
<template>
  <!--  api를 통해 받은 comment 정보를 통해 댓글을 사용자에게 보여준다.  -->
  <div v-if="commentList.length" class="the-comment-list">
    <!-- comment-item -->
    <div v-for="item in commentList" :key="item.postReviewId">
      <comment-item :comment="item" :page-id="pageId"></comment-item>
    </div>
  </div>

  <!-- 댓글이 없을 경우 보여지는 화면  -->
  <!-- <div v-if="isEmpty" class="the-comment-none">
    <p>댓글이 없습니다</p>
    <p>😥</p>
  </div> -->

  <!-- 댓글 수정 성공 팝업 -->
  <success-pop-up v-if="successPopUpStatus" @off-modal="offSuccessPopUpModal">
    성공적으로 수정되었습니다
  </success-pop-up>

  <!-- 댓글 삭제 확인 모달 -->
  <comment-delete-modal
    v-if="deleteModalStatus"
    :page-id="pageId"
    @off-modal="offDeleteModal"
  ></comment-delete-modal>
</template>

<script setup lang="ts">
import { ref, onBeforeMount, computed, watch, onUnmounted } from "vue";
import CommentItem from "@/components/basics/CommentItem.vue";
import CommentDeleteModal from "@/components/modals/CommentDeleteModal.vue";
import SuccessPopUp from "@/components/modals/SuccessPopUp.vue";
import { useStore } from "vuex";
const store = useStore();

// const isEmpty = computed(() => {
//   if (!commentList.value.length) {
//     return true;
//   } else {
//     return false;
//   }
// });

// 프롭스
const props = defineProps<{
  pageId: number;
}>();

// 댓글 정보
const commentList = computed(() => store.getters["comment/getCommentList"]);

// 인피니티 스크롤
const handleScroll = (event: Event) => {
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

const offSuccessPopUpModal = () => {
  store.dispatch("comment/toggleSuccessPopUpStatus", false);
};

const offDeleteModal = () => {
  store.dispatch("comment/toggleDeleteModalStatus", false);
};

// 시간제 모달
watch(successPopUpStatus, () => {
  if (successPopUpStatus) {
    setTimeout(() => offSuccessPopUpModal(), 2000);
  }
});

// 이벤트 연결 끊기
onUnmounted(() => {
  store.dispatch("comments/resetCommentData");
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss">
.the-comment-list {
  @include flex(column);
  gap: 32px;
  width: 100%;
  margin-bottom: 40px;
}
/* 
.the-comment-none {
  @include flex-center;
  gap: 4px;
  @include font($fs-main, $fw-regular);
  color: $sub-color;
  margin-bottom: 72px;
} */
</style>
