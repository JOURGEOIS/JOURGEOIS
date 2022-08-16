<template>
  <the-cocktail-desc-review-item
    v-for="(review, id) in cocktailReviewData"
    :key="`review-${id}`"
    :review="review"
  ></the-cocktail-desc-review-item>

  <!-- 수정이 완료되면 팝업 알림 -->
  <success-pop-up
    v-if="successPopUpStatus"
    @offModal="offSuccessPopUpModal"
  >
    성공적으로 변경되었습니다
  </success-pop-up>
  <review-delete-modal
    v-if="deleteModalStatus"
    :cocktailId="cocktailId"
    @offModal="offDeleteModal"
  >
  </review-delete-modal>
  
</template>

<script setup lang="ts">
import TheCocktailDescReviewItem from "@/components/cocktails/TheCocktailDescReviewItem.vue";
import SuccessPopUp from "@/components/modals/SuccessPopUp.vue";
import ReviewDeleteModal from "@/components/modals/ReviewDeleteModal.vue";
import { computed, onBeforeMount, watch, onUnmounted } from "vue";
import { useStore } from "vuex";
const store = useStore();
const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);

// 칵테일 id
const cocktailData = computed(
  () => store.getters["cocktailDesc/getCurrentCocktailData"]
);
const cocktailId = Number(cocktailData.value.id);


// 칵테일 후기 정보 불러오기
const cocktailReviewData = computed(
  () => store.getters["cocktailReview/getCurrentCocktailReview"]
);
console.log(cocktailReviewData.value)
// 전체 후기 추가 함수
const getWholeReview = (cocktailId: number) => {
  store.dispatch("cocktailReview/getCocktailReview", cocktailId);
};

// 스크롤 감지
const handleScroll = (event: Event) => {
  const data = {
    event,
    action: "cocktailReview/getCocktailReview",
    data: cocktailId,
  };
  store.dispatch("scroll/handleScroll", data);
};

onBeforeMount(() => {
  if (isLoggedIn.value) {
    window.addEventListener("scroll", handleScroll);

    // 리셋
    store.dispatch("cocktailReview/resetCocktailReview");
    store.dispatch("cocktailReview/toggleReviewChangeSuccess", false);
    store.dispatch("cocktailReview/toggleDeleteModal", false)

    // 데이터 받기
    store.dispatch("cocktailReview/getCocktailReview", cocktailId)
  }

  // 시간제 모달
    watch(successPopUpStatus, () => {
      if (successPopUpStatus) {
        setTimeout(() => offSuccessPopUpModal(), 2000);
      }
    });

    // 이벤트 연결 끊기
    onUnmounted(() => {
      window.removeEventListener("scroll", handleScroll);
    });
  getWholeReview(cocktailId);
});

// 모달 상태
const successPopUpStatus = computed(
  () => store.getters["cocktailReview/getReviewChangeSuccess"]
);

const deleteModalStatus = computed(
  () => store.getters["cocktailReview/getDeleteModalStatus"]
);

const offSuccessPopUpModal = () => {
  store.dispatch("cocktailReview/toggleReviewChangeSuccess", false)
}

const offDeleteModal = () => {
  store.dispatch("cocktailReview/toggleDeleteModal", false)
}

// 리셋
onUnmounted(() => {
  store.dispatch("cocktailReview/resetCocktailReview");
  window.removeEventListener("scroll", handleScroll);
});


</script>

<style scoped lang="scss"></style>
