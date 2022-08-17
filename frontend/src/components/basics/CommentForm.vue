<!-- comment 작성 form: 공란, 비속어 유효성 검사를 진행한다.  -->
<template>
  <form class="comment-form" @submit.prevent="submitCommentForm">
    <RoundImage :round-image="imageData" class="round-image"></RoundImage>
    <input
      class="comment-input"
      :class="commentErrorClass"
      type="text"
      maxlength="200"
      v-model.trim="commentInputValue"
      placeholder="댓글 추가..."
    />
    <button class="comment-button" :disabled="commentDisabled">
      <span class="material-icons"> arrow_upward </span>
    </button>
  </form>
  <div class="comment-error" v-if="commentErrorStatus">
    부적절한 단어가 포함되어 있습니다.
  </div>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import { checkBadWord } from "../../functions/checkText";
import { computed, ref } from "vue";
import { useStore } from "vuex";
const store = useStore();

const props = defineProps<{
  pageId: number;
}>();

// 본인 프로필 사진
const profileImage = computed(
  () => store.getters["personalInfo/getProfileImage"]
);

// 이미지 props
const imageData = {
  image: profileImage.value,
};

// comment input value
const commentInputValue = ref("");

// disabled status: 공란일 경우, 제출하는 버튼이 활성화되지 않는다. (제출 불가능)
const commentDisabled = computed(() => {
  if (!commentInputValue.value) {
    return true;
  } else {
    return false;
  }
});

// 에러 status
const commentErrorStatus = ref(false);
const commentErrorClass = ref("");

// 댓글 제출
const submitCommentForm = () => {
  // 리셋
  commentErrorStatus.value = false;
  commentErrorClass.value = "";

  // 비속어 유효성 검사
  const conditionA = checkBadWord(commentInputValue.value);

  // 보낼 데이터
  const data = {
    postId: props.pageId,
    review: commentInputValue.value,
  };
  if (!conditionA) {
    store.dispatch("comment/createComment", data);
    commentInputValue.value = "";
  } else {
    commentErrorStatus.value = true;
    commentErrorClass.value = "error";
  }
};
</script>

<style scoped lang="scss">
.comment-form {
  @include flex-xy(flex-start, center);
  gap: 4px;
  width: 100%;
  padding: 32px 0px;

  > .round-image {
    flex-shrink: 0;
    width: 30px;
    height: 30px;

    @media #{$tablet} {
      width: 50px;
      height: 50px;
    }
  }

  .comment-input {
    flex-grow: 1;
    height: 40px;
    padding: 0 8px;
    border: none;
    border-radius: 10px;
    background-color: $white150;

    @media #{$tablet} {
      height: 50px;
    }

    &:focus {
      outline: none;
      border: 1px solid $main-color;
    }
  }

  .comment-button {
    flex-shrink: 0;
    width: fit-content;
    height: 40px;
    padding: 4px;
    background-color: $white;

    .material-icons {
      @media #{$tablet} {
        font-size: 40px;
      }
    }
  }
}

.comment-error {
  align-self: flex-start;
  margin-top: -36px;
  margin-left: 40px;
  color: $danger-color;
  @include font($fs-md, $fw-regular);

  @media #{$tablet} {
    margin-left: 55px;
  }
}

.error {
  border: 1px solid $danger-color !important;
}
</style>
