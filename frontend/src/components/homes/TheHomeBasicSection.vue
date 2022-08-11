<template>
  <div class="home-basic-section">
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
      {{ data.description }}
    </article>
    <article class="article-carosel">
      <div class="carosel-item">
        <div class="carosel-img"></div>
        <div class="carosel-text">마!가리타라라라라라라라라라라라라라라라</div>
      </div>
      <div class="carosel-item">
        <div class="carosel-img"></div>
        <div class="carosel-text">마!가리타라라라라라라라라라</div>
      </div>
    </article>
    <article class="article-more-button">
      <div class="more-button" @click="clickMore">
        {{ data.moreButtonText }}
      </div>
    </article>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
const store = useStore();
const router = useRouter();

const props = defineProps<{
  data: {
    description: string;
    moreButtonText: string;
    callCaroselFunc: string;
    showMoreView: string;
  };
}>();

const isShowDescription = ref(false);

const toggleShowDescription = () => {
  isShowDescription.value = !isShowDescription.value;
};

// 더보기 클릭
const clickMore = () => {
  router.push({ name: props.data.showMoreView });
};
</script>

<style scoped lang="scss">
.home-basic-section {
  @include flex(column);
  width: 100%;
  padding: 20px;
  border-radius: 5px;
  background-color: $white;
  gap: 25px;
  @include shadow-feed;
}

// title slot
.article-title {
  @include flex-xy(flex-start, flex-end);
  @include font(15px);
  gap: 5px;

  .icon-info {
    @include font-size-placeholder(19px);
  }
}

.article-description {
  margin-top: -20px;
  @include font(12px, $fw-regular);
  color: $sub-color;
}

.article-carosel {
  @include flex;
  gap: 5px;
  .carosel-item {
    @include flex(column);
    width: 130px;
    gap: 5px;
    .carosel-img {
      // width: 150px;
      // height: 150px;
      height: 130px;
      background-color: $white200;
      border-radius: 5px;
    }
    .carosel-text {
      width: 100%;
      @include font-size-sub(12px);
    }
  }
}

.article-more-button {
  .more-button {
    @include flex-center;
    width: 100%;
    padding: 10px;
    border: 1px solid $unchecked-color;
    border-radius: 5px;
    @include font(12px, $fw-medium);
    color: $primary-color;
    @include shadow-feed;
    @include for-click;
  }
}
</style>
