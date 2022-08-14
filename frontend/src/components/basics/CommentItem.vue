<!-- commentList를 이루는 comment item -->
<template>
  <article class="the-comment-article">
    <!-- 프로필 이미지  -->
    <round-image
      :round-image="{ image: comment.profileImg }"
      @click="clickProfileImage"
      class="the-comment-profile-image"
    ></round-image>

    <!-- 댓글 컨텐츠 -->
    <div class="the-comment-item">
      <div class="the-comment-item-header">
        <!-- 이름 -->
        <p class="the-comment-item-name" @click="clickProfileImage">
          {{ comment.nickname }}
        </p>

        <!-- 수정, 삭제 버튼 (본인 일 때에만 보인다. ) -->
        <div
          class="the-comment-item-button"
          v-if="comment.isMine && !updateComment"
        >
          <span
            @click="
              clickUpdate({ review: comment.review, id: comment.postReviewId })
            "
          >
            수정
          </span>
          <span @click="clickDelete(comment.postReviewId)"> 삭제</span>
        </div>

        <!-- 완료, 취소 버튼 (수정을 누르면 수정, 삭제 버튼 자리에 생성된다.) -->
        <div class="the-comment-item-button" v-if="updateComment">
          <span @click="submitUpdate"> 완료</span>
          <span @click="clickCancel"> 취소</span>
        </div>
      </div>

      <!-- 날짜와 수정됨(수정이 되면 수정됨이 보인다) -->
      <div class="the-comment-item-date" v-if="!updateComment">
        <p>{{ calcDateDelta(comment.createTime) }}</p>
        <p class="the-comment-item-update" v-if="comment.isUpdated">수정됨</p>
      </div>

      <!-- 댓글 내용 -->
      <div class="the-comment-item-content" v-if="!updateComment">
        {{ comment.review }}
      </div>

      <!-- 댓글 수정 form -->
      <form v-else @submit.prevent="submitUpdate">
        <textarea-basic
          :data="commentInputData"
          :textarea-style="commentInputStyle"
          v-model.trim="commentInputValue"
        ></textarea-basic>
      </form>

      <!-- 댓글 수정 에러 메시지 -->
      <p class="error-message" v-if="badWordTest">
        부적절한 언어가 포함되었습니다.
      </p>
      <p class="error-message" v-if="blankWordTest">
        빈 값을 입력할 수 없습니다.
      </p>
    </div>
  </article>
</template>

<script setup lang="ts">
import { computed, ref, reactive } from "vue";
import RoundImage from "@/components/basics/RoundImage.vue";
import TextareaBasic from "@/components/basics/TextareaBasic.vue";
import { calcDateDelta } from "../../functions/date";
import { checkBadWord } from "../../functions/checkText";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
const store = useStore();
const router = useRouter();

interface comment {
  createTime: number[];
  updateTime: number[];
  isMine: number;
  isUpdated: number;
  nickname: string;
  postReviewId: number;
  profileImg: string;
  review: string;
  uid: number;
}

const props = defineProps<{
  comment: comment;
  pageId: number;
}>();

// 프로필 이미지 클릭
const clickProfileImage = () => {
  router.push({
    name: "TheUserProfileView",
    params: {
      userId: props.comment.uid,
    },
  });
};

// 에러 메시지
const badWordTest = ref(false);
const blankWordTest = ref(false);

// 수정 ===================================================
const clickUpdate = (data: { review: string; id: number }) => {
  const { review, id } = data;
  commentInputValue.value = review;
  commentId.value = id;
  updateComment.value = true;
};

const updateComment = ref(false);
const commentInputData: object = reactive({
  button: false,
  placeholder:
    "광고 및 욕설, 비속어나 타인을 비방하는 문구를 사용하면 비공개 될 수 있습니다.",
  type: "text",
  maxlength: 200,
});

// form 스타일
const commentInputStyle = ref("normal");

// comment value
const commentInputValue = ref("");

// comment id
const commentId = ref(0);

// 수정 취소 버튼
const clickCancel = () => {
  updateComment.value = false;

  // 리셋
  commentInputStyle.value = "normal";
  badWordTest.value = false;
  blankWordTest.value = false;
};

// 수정 제출 버튼
const submitUpdate = () => {
  // 리셋
  commentInputStyle.value = "normal";
  badWordTest.value = false;
  blankWordTest.value = false;

  // 유효성 검사
  const conditionA = checkBadWord(commentInputValue.value);
  const conditionB = !commentInputValue.value;

  if (!conditionA && !conditionB) {
    // 제출
    const data = {
      postReviewId: commentId.value,
      postId: props.pageId,
      review: commentInputValue.value,
    };
    store.dispatch("comment/updateComment", data);
    updateComment.value = false;
  } else {
    // 유효성 검사 실패
    commentInputStyle.value = "error";
    if (conditionA) {
      badWordTest.value = true;
    }
    if (conditionB) {
      blankWordTest.value = true;
    }
  }
};

// 삭제 ===================================================
// 삭제 버튼
const clickDelete = (id: number) => {
  store.dispatch("comment/toggleDeleteModalStatus", true);
  store.dispatch("comment/setDeleteCommentId", id);
};
</script>

<style scoped lang="scss">
.the-comment-article {
  display: flex;
  gap: 8px;
  width: 100%;

  .round-image {
    width: 50px;
    height: 50px;
  }

  .the-comment-item {
    width: calc(100% - 50px);
    .the-comment-item-header {
      @include flex-xy(space-between, center);
      width: 100%;
      .the-comment-item-name {
        @include font($fs-md, $fw-medium);
        cursor: pointer;
      }

      .the-comment-item-button {
        @include font(14px, $fw-regular);
        color: $sub-color;

        span {
          padding: 0 4px;
          cursor: pointer;
        }
      }
    }
  }
  .the-comment-item-date {
    @include flex-xy(flex-start, center);
    gap: 4px;
    @include font($fs-sm, $fw-regular);
    color: $sub-color;

    .the-comment-item-update {
      @include font($fs-sm, $fw-medium);
      color: $white400;
    }
  }
  .the-comment-item-content {
    position: relative;
    @include font($fs-md, $fw-regular);
  }
}
form::v-deep(.textarea-container) {
  width: 100%;
  margin-top: 4px;
}

form::v-deep(.textarea-container > textarea) {
  width: 100%;
  height: 100px;
  @include font(14px, $fw-regular);
}

.error-message {
  color: $red-color;
  @include font($fs-sm, $fw-medium);
}

.the-comment-profile-image {
  cursor: pointer;
}
</style>
