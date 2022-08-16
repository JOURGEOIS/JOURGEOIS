<template>
  <form
    class="cocktail-desc-review-profile-input"
    @submit.prevent="submitCreateReviewForm"
  >
    <div class="cocktail-desc-review-profile">
      <round-image
        :round-image="{ image: profileInfo.image, width: '45px' }"
      ></round-image>
    </div>
    <div class="cocktail-desc-review-input-notice">
      <div class="cocktail-desc-review-input">
        <textarea-basic
          :data="reviewInputData"
          :textarea-style="reviewInputStyle"
          v-model.trim="reviewInputValue"
        ></textarea-basic>
        <failure-pop-up v-if="failModalStatus">
          {{ reviewConditionErrorMessage }}
        </failure-pop-up>
        <button-basic
          :button-style="[buttonColor, '30px']"
          :disabled="!reviewInputValue"
          class="buttonstyle"
        >
          등록
        </button-basic>
      </div>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch, onMounted } from "vue";
import { useStore } from "vuex";
import TextareaBasic from "@/components/basics/TextareaBasic.vue";
import RoundImage from "@/components/basics/RoundImage.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
import { checkBadWord } from "../../functions/checkText";

// 유저 정보 불러오기
const store = useStore();
const userInfo = computed(() => store.getters["personalInfo/getUserInfo"]);

const text = ref("");

// profile image
const profileImg = userInfo.value.profileImg;
const nickname = userInfo.value.nickname;
const profileInfo = reactive({
  image: profileImg,
  name: nickname,
});

// textarea 데이터
const reviewInputData: object = reactive({
  button: false,
  placeholder:
    "광고 및 욕설, 비속어나 타인을 비방하는 문구를 사용하면 비공개 될 수 있습니다.",
  type: "text",
  maxlength: 100,
});

// textarea value
const reviewInputValue = ref("");
const reviewInputStyle = ref("normal");

// button color
const buttonColor = computed(() => {
  if (reviewInputValue.value) {
    return "primary-blank";
  } else {
    return "unchecked-blank";
  }
});

// 에러 처리
const reviewConditionErrorMessage: string =
  "부적절한 단어가 포함되어 있습니다.";
const errorMessage: string[] = reactive([]);
const occurredError = ref(false);

// 리뷰 등록 실패 팝업
const failModalStatus = computed(
  () => store.getters["cocktailDesc/getFailPopupStatus"]
);

const toggleFailPopUp = (value: boolean) => {
  store.dispatch("cocktailDesc/toggleFailPopupStatus", value);
};

watch(failModalStatus, () => {
  if (failModalStatus.value) {
    setTimeout(() => {
      toggleFailPopUp(false);
    }, 2000);
  }
});

// 리셋
onMounted(() => {
  toggleFailPopUp(false);
});

// 데이터 전송
const createReview = (data: object) =>
  store.dispatch("cocktailReview/createCocktailReview", data);

const cocktailData = computed(
  () => store.getters["cocktailDesc/getCurrentCocktailData"]
);
const cocktailId = Number(cocktailData.value.id);
const getReview = (cocktailId: number) =>
  store.dispatch("cocktailReview/getCocktailReview", cocktailId);

// 제출
const submitCreateReviewForm = () => {
  // 유효성 검사
  const reviewCondition = checkBadWord(reviewInputValue.value);
  // 전달할 데이터
  const data: object = {
    cocktailId: cocktailId,
    comment: reviewInputValue.value,
  };
  // 제출
  if (reviewCondition) {
    reviewInputStyle.value = "error";
    occurredError.value = true;
    toggleFailPopUp(true);
  } else {
    createReview(data);

    // 리셋
    errorMessage.length = 0;
    reviewInputStyle.value = "normal";
    occurredError.value = false;
    reviewInputValue.value = "";
  }
};
</script>

<style scoped lang="scss">
.cocktail-desc-review-profile-input {
  @include flex-center;
  width: 100%;
  gap: 6px;
  .cocktail-desc-review-profile {
    @include flex-center;
    flex-direction: column;
    p {
      @include font($fs-xs, $fw-regular);
    }
  }
  .cocktail-desc-review-input-notice {
    @include flex;
    width: 100%;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    padding: 0px;
    gap: 4px;
    p {
      @include font($fs-xs, $fw-regular);
      text-justify: center;
    }
    .cocktail-desc-review-input {
      @include flex;
      width: 100%;
      flex-direction: row;
      align-items: center;
      padding: 0px;
      gap: 12px;
      .buttonstyle {
        @include font($fs-xs, $fw-regular);
        padding: 37px 0px;
        background-color: white;
      }
    }
  }
}
</style>
