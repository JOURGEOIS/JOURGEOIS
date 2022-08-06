<template>
  <section class="like-comment-share">
    <div class="like tab" @click="clickLike">
      <span class="material-icons like-icon" v-if="isLiked"> favorite </span>
      <span class="material-icons like-icon" v-if="!isLiked">
        favorite_border
      </span>
      <span class="button-text" @click="clickLikedUsers">
        <slot name="like" v-if="!isCalledLike"></slot>
        <p v-if="isCalledLike">{{ likeCnt }}</p>
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
    isLiked: number;
    p_id: number;
  };
}>();

// 좋아요 버튼
const isLiked = ref(1);
const likeCnt = ref(-1);
const isCalledLike = ref(0);
// 좋아요 버튼을 누른 경우
const clickLike = () => {
  axios({
    url: api.post.toggleBookmark(),
    method: "POST",
    headers: {
      // uid:
    },
    data: {
      p_id: props.data.p_id,
    },
  })
    .then((res) => {
      const data = res.data;
      console.log(data);
      const { count, status } = data;
      isCalledLike.value = 1;
      isLiked.value = status;
      likeCnt.value = count;
    })
    .catch((err) => {
      console.error(err.response);
      alert("오류 떴으니까 이따 해라");
    });
};

// 좋아요 숫자(좋아요한 유저 목록) 클릭
const clickLikedUsers = () => {
  alert("커스텀 칵테일 좋아요 유저 목록으로 이동");
  const cocktailId = route.params.cocktailId;
  const feedId = route.params.feedId;
  router.push({
    name: "TheLikedUserListView",
    params: { cocktailId, feedId },
  });
};

// 공유 버튼 클릭
const clickShare = () => {
  alert("공유 버튼 눌렀다. 공유 모달이나 슬라이더 생기게 하기");
};
</script>

<style scoped lang="scss">
.like-comment-share {
  width: calc(100% + 32px);
  padding: 10px;
  border-top: 1px solid $unchecked-color;
  border-bottom: 1px solid $unchecked-color;
  @include flex-xy(space-between, center);

  .like {
    .like-icon {
      color: $red400;
    }
  }

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
</style>
