<template>
  <div class="the-community-form-view">
    <!-- 헤더 불러오기 -->
    <header-basic
      :prev="true"
      :success="true"
      formId="the-community-update-form"
      @prevClicked="$router.go(-1)"
    >
      글수정
    </header-basic>
    <section>
      <!-- 폼 풀러오기 -->
      <the-community-update-form
        id="the-community-update-form"
      ></the-community-update-form>
      <!-- 이용규칙 -->
      <div class="the-community-notice">
        <the-community-notice-button></the-community-notice-button>
        <p>
          주류주아는 누구나 기분 좋게 참여할 수 있는 커뮤니티를 만들기 위해
          커뮤니티 이용규칙을 제정하여 운영하고 있습니다.
        </p>
        <p>게시물 작성 전 이용규칙 전문을 반드시 확인해주시기 바랍니다.</p>
      </div>
    </section>
  </div>
  <failure-pop-up v-if="errorStatus">
    {{ errorMessage }}
  </failure-pop-up>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
import TheCommunityUpdateForm from "@/components/feeds/TheCommunityUpdateForm.vue";
import TheCommunityNoticeButton from "@/components/feeds/TheCommunityNoticeButton.vue";
import { onBeforeMount, onUnmounted, computed, watch } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
const route = useRoute();
const store = useStore();

const errorStatus = computed(() => store.getters["createFeed/getAlertStatus"]);
const errorMessage = computed(
  () => store.getters["createFeed/getErrorMessage"]
);
// 시간제 모달
watch(errorStatus, () => {
  if (errorStatus.value) {
    setTimeout(() => {
      store.dispatch("createFeed/changeAlertStatus", false);
    }, 2000);
  }
});

// 모달 초기화 및 db 요청
onBeforeMount(() => {
  console.log(route.params.feedId);
  store.dispatch("feedDescInfo/getCommunityDetail", {
    feedId: route.params.feedId,
  });
  store.dispatch("createFeed/changeAlertStatus", false);
});

onUnmounted(() => {
  store.dispatch("createFeed/resetCommunityData");
});
</script>

<style scoped lang="scss">
.the-community-form-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  position: relative;
  @include accountLayOut;
  section {
    @include flex(column);
    width: 100%;
    gap: 36px;
    margin-top: 2rem;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }
    .the-community-notice {
      @include flex(column);
      p {
        @include font($fs-sm, $fw-regular);
        margin-bottom: 10px;
      }
    }
  }
}
</style>
