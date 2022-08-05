<template>
  <div class="the-comment-list">
    <!-- <div v-for="{ item, index } in commentList" :key="index"> -->
    <div>
      <p>이름</p>
      <p>방금 전</p>
      <p>
        모든 국민은 근로의 의무를 진다. 국가는 근로의 의무의 내용과 조건을
        민주주의원칙에 따라 법률로 정한다. 국회의원이 회기전에 체포 또는 구금된
        때에는 현행범인이 아닌 한 국회의 요구가 있으면 회기중 석방된다.
        헌법개정안이 제2항의 찬성을 얻은 때에는 헌법개정은 확정되며, 대통령은
        즉시 이를 공포하여야 한다. 체포·구속·압수 또는 수색을 할 때에는 적법한
        절차에 따라 검사의 신청에 의하여 법관이 발부한 영장을 제시하여야 한다.
        다만, 현행범인인 경우와 장기 3년 이상의 형에 해당하는 죄를 범하고 도피
        또는 증거인멸의 염려가 있을 때에는 사후에 영장을 청구할 수 있다
      </p>
    </div>
  </div>
  <!-- <div v-else class="the-comment-none">
    <p>댓글이 없습니다</p>
    <p>😥</p>
  </div> -->
</template>

<script setup lang="ts">
import { onMounted, computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

// 프롭스
const props = defineProps<{
  pageId: number;
}>();

// 댓글 정보
const commentList = computed(() => store.getters["comment/getCommentList"]);

// 작성자 확인
const userId = computed(() => store.getters["personalInfo/getUserInfoUserId"]);

// 수정창으로 변경

// 댓글 정보 받기
onMounted(() => {
  store.dispatch("comment/saveCommentList", { asc: false, p_id: props.pageId });
});
</script>

<style scoped lang="scss">
.commentList {
  @include flex(columns);

  textarea {
  }
}

.the-comment-none {
  @include flex-center;
  gap: 4px;
  @include font($fs-main, $fw-medium);
}
</style>
