<template>
  <div class="video-section" v-if="videoLink">
    <article class="article-title">
      <slot></slot>
      <span
        class="material-icons-outlined icon-info"
        @click="toggleShowDescription"
      >
        info
      </span>
    </article>
    <article class="article-description" v-if="isShowDescription">
      주류주아가 도움이 될 영상을 추천해드려요
    </article>
    <article class="article-video">
      <iframe
        width="100%"
        height="100%"
        :src="videoLink"
        frameborder="0"
      ></iframe>
    </article>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import axios from "axios";
import api from "../../api/api";

const isShowDescription = ref(false);
const toggleShowDescription = () => {
  isShowDescription.value = !isShowDescription.value;
};

// Video 가져오기
const videoLink = ref("");
axios({
  url: api.homes.clipVideo(),
  method: "GET",
})
  .then((res) => {
    videoLink.value = res.data.url;
  })
  .catch((err) => {
    console.error(err.response);
  });
</script>

<style scoped lang="scss">
.video-section {
  @include flex(column);
  width: 100%;
  padding: 20px;
  border-radius: 5px;
  background-color: $white;
  gap: 25px;
  @include shadow-feed;

  .article-title {
    @include flex-xy(flex-start, flex-end);
    gap: 5px;

    .icon-info {
      @include font-size-placeholder(19px);
    }
  }

  .article-description {
    margin-top: -20px;
    @include font(14px, $fw-regular);
    color: $sub-color;
  }

  .article-video {
    width: 100%;
    aspect-ratio: 16/9;
    background-color: $unchecked-color;
  }
}
</style>
