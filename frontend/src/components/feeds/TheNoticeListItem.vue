<template>
  <div class="the-notice-item-container">
    <!-- 프로필 이미지  -->
    <round-image
      :round-image="imageData"
      class="the-notice-item-image"
      @click="clickProfileImage"
    ></round-image>
    <div class="the-notice-item-content" @click="readNotice">
      <p>
        <!-- 알림 설명 -->
        <span class="the-notice-item-name">{{ noticeData.opponent.nickname + " "}}</span>
        <span> {{ notice }} </span>
      </p>
      <!-- 알림 시간, 알림 읽음 여부 체크 -->
      <p class="the-notice-item-time">
        <span>{{ time }} </span> <div class="the-notice-item-unread" v-if="!noticeData.notification.isRead"></div>
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, reactive } from "vue";
import { Notice } from "../../interface";
import {calcDateDelta2} from "../../functions/date"
import roundImage from "@/components/basics/RoundImage.vue";
import { useStore } from "vuex";
import { useRouter } from 'vue-router';
const store = useStore();
const router = useRouter();

const props = defineProps<{
  noticeData: Notice;        
}>();

// 프로필 이미지 
const imageData = reactive({
  image: props.noticeData.opponent.img
});

//  알림 설명
const notice = computed(() => {
  switch (props.noticeData.notification.type) {
    case "LIKE": {
      return "님이 당신의 게시글에 좋아요를 눌렀습니다.";
    }
    case "COMMENT": {
      return "님이 당신의 게시글에 댓글을 남겼습니다.";
    }
    case "FOLLOW": {
      return "님이 당신을 팔로우 했습니다.";
    }
  }
});

const timestamp = computed(()=> {
  return props?.noticeData?.notification?.timestamp
  });

// 알림 시간
const time = computed(() => {
  return new Date(
      timestamp.value.seconds * 1000 + timestamp.value.nanoseconds / 1000000,
    )
  // return calcDateDelta2(timestamp.value.toDate())  
});


// 알림 클릭시 (알림 읽음)
const readNotice = () => {
  const data = {
    notiId: props.noticeData.notification.notificationId,
    type: props.noticeData.notification.type,
    postId: props.noticeData.notification.postId,
    uid: props.noticeData.opponent.uid,
    postType: props?.noticeData?.postMetaInfo?.type || 0,
    baseCocktailId: props?.noticeData?.postMetaInfo?.baseCocktailId || 0,
  }
  store.dispatch("notice/readNotice",data)
};

// 프로필 클릭시, 해당 유저의 프로필로 이동한다. 
const clickProfileImage = () => {
  const uid = props.noticeData.notification.uid;
  router.push({ name: 'TheUserProfileView', params: { userId: uid } })
};
</script>

<style scoped lang="scss">
.the-notice-item-container {
  @include flex-xy(flex-start, center);
  gap: 12px;
  height: 88px;
  .the-notice-item-image {
    width: 40px;
    height: 40px;
    flex-shrink: 0;
    cursor: pointer;
  }

  .the-notice-item-content {
    font-size: $fs-md;
    cursor: pointer;
    color: $gray300;


    .the-notice-item-name {
      font-weight: $fw-bold;
      color: $main-color;
    }
    .the-notice-item-time{
      @include flex-xy(flex-start, center);
      gap: 4px;
      color: $gray300;
      @include font($fs-sm, $fw-regular);
      .the-notice-item-unread {
        width: 6px;
        height: 6px;
        border-radius: 50%;
        background-color: $danger-color;
      }
  }
  }
}

</style>
