<template>
  <div class="cocktail-desc-review">
    <div class="cocktail-desc-review-profile-content">
      <!-- 프로필 이미지 -->
      <div class="cocktail-desc-review-profile">
        <round-image
          :round-image="{
            image: review.profileImg,
            width: '45px',
            height: '45px',
          }"
          @click="goProfile"
        ></round-image>
      </div>

      <!-- 수정하지 않을 때 -->
      <div v-if="!isEditing" class="cocktail-desc-review-content">
        <div class="cocktail-decs-review-front">
          <div class="cocktail-desc-review-name-time">
            <p class="cocktail-desc-review-profile-name" @click="goProfile">
              {{ review.nickname }}
            </p>
            <div class="cocktail-desc-review-time-compare">
              <p class="cocktail-desc-review-time">{{ calc }}</p>
              <p class="cocktail-desc-review-compare">{{ compare }}</p>
            </div>
          </div>
          <div
            v-if="userInfo.nickname == review.nickname"
            class="cocktail-desc-review-button"
          >
            <button-basic
              :button-style="[buttonColor, '40px']"
              class="buttonstyle"
              @click="isEditing = !isEditing"
            >
              수정
            </button-basic>
            <button-basic
              :button-style="[buttonColor, '40px']"
              class="buttonstyle"
              @click="clickDeleteReview"
            >
              삭제
            </button-basic>
          </div>
        </div>
        <p>
          {{ review.comment }}
        </p>
      </div>
      <!-- 수정 버튼 누르면 수정폼으로 변환됨. -->
      <form
        @submit.prevent=""
        v-else-if="isEditing"
        class="cocktail-desc-review-content"
      >
        <div class="cocktail-decs-review-front">
          <div class="cocktail-desc-review-name-time">
            <p class="cocktail-desc-review-profile-name">
              {{ review.nickname }}
            </p>
            <p class="cocktail-desc-review-time">{{ calc }}</p>
          </div>
          <div
            v-if="userInfo.nickname == review.nickname"
            class="cocktail-desc-review-button"
          >
            <button-basic
              :button-style="[buttonColor, '40px']"
              :disabled="!reviewInputValue"
              class="buttonstyle"
              @click="clickEditReview"
            >
              완료
            </button-basic>
            <button-basic
              :button-style="[buttonColor, '40px']"
              class="buttonstyle"
              @click="clickCancel"
            >
              취소
            </button-basic>
          </div>
        </div>
        <div class="cocktail-desc-review-input">
          <textarea-basic
            :data="reviewInputData"
            :textarea-style="reviewInputStyle"
            v-model.trim="reviewInputValue"
          ></textarea-basic>
          <failure-pop-up v-if="failModalStatus">
            {{ reviewConditionErrorMessage }}
          </failure-pop-up>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch, onMounted } from "vue";
import { useRouter } from "vue-router"
import { useStore } from "vuex";
import RoundImage from "@/components/basics/RoundImage.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import TextareaBasic from "@/components/basics/TextareaBasic.vue";
import FailurePopUp from "@/components/modals/FailurePopUp.vue";
import { calcDateDelta, compareDate } from "../../functions/date";
import { checkBadWord } from "../../functions/checkText";
const store = useStore();
const router = useRouter();

interface cocktailReviewData {
  commentId: number;
  userId: number;
  nickname: string;
  profileImg: string | null;
  cocktailId: number;
  comment: string;
  createdDate: number[];
  modifiedDate: number[];
}

const props = defineProps<{
  review: cocktailReviewData;
}>();

// 유저 정보 불러오기
const userInfo = computed(() => store.getters["personalInfo/getUserInfo"]);
const userId = computed(() => store.getters["personalInfo/getUserInfoUserId"]);

const uid = props.review.userId

const img: string = userInfo.value.profileImg;
const name: string = userInfo.value.nickname;

// 칵테일 날짜
const calc = calcDateDelta(props.review.createdDate);
const modicalc = calcDateDelta(props.review.modifiedDate);

// 수정시간 / 생성시간 차이
const createdDate = props.review.createdDate;
const modifiedDate = props.review.modifiedDate;
const compare = compareDate(createdDate, modifiedDate);
// button
const reviewEditValue = ref("");

// button color
const buttonColor = computed(() => {
  return "sub-blank";
});

// 칵테일 아이디 가져오기
const cocktailData = computed(
  () => store.getters["cocktailDesc/getCurrentCocktailData"]
);
const cocktailId = Number(cocktailData.value.id);

const goProfile = () => {
  router.push({name: "TheUserProfileView", params:{userId: uid}})
}

// 후기 수정
const reviewEdit = (data: object) =>
  store.dispatch("cocktailReview/updateCocktailReview", data);
let oldData = {
  cocktailId,
  commentId: props.review.commentId,
  comment: props.review.comment,
};
let isEditing = ref(false);

// textarea 데이터
const reviewInputData: object = reactive({
  button: false,
  placeholder:
    "광고 및 욕설, 비속어나 타인을 비방하는 문구를 사용하면 비공개 될 수 있습니다.",
  type: "text",
  maxlength: 100,
});

// textarea value
const reviewInputValue = ref(oldData.comment);
const reviewInputStyle = ref("normal");

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

const clickEditReview = () => {
  // 유효성 검사
  const reviewCondition = checkBadWord(reviewInputValue.value);
  // 전달할 데이터
  const editData = {
    cocktailId,
    commentId: props.review.commentId,
    comment: reviewInputValue.value,
  };
  // 제출
  if (reviewCondition) {
    reviewInputStyle.value = "error";
    occurredError.value = true;
    toggleFailPopUp(true);
  } else {
    reviewEdit(editData);
  }
};
const clickCancel = () => {
  reviewInputValue.value = oldData.comment;
  isEditing.value = !isEditing;
};

// 후기 삭제
const reviewDelete = (data: object) =>
  store.dispatch("cocktailReview/deleteCocktailReview", data);

const commentId = props.review.commentId;

const clickDeleteReview = () => {
  store.dispatch("cocktailReview/toggleDeleteModal", true)
  store.dispatch("cocktailReview/setDeleteReviewId", commentId)
};
</script>

<style scoped lang="scss">
.cocktail-desc-review {
  @include flex;
  width: 100%;
  .cocktail-desc-review-profile-content {
    @include flex;
    align-items: flex-start;
    width: 100%;
    gap: 12px;
    .cocktail-desc-review-profile {
      @include flex;
      align-items: center;
    }
    .cocktail-desc-review-content {
      @include flex;
      @include font($fs-md, $fw-regular);
      flex-direction: column;
      width: 100%;
      gap: 5px;

      .cocktail-decs-review-front {
        @include flex;
        justify-content: space-between;
        align-items: flex-start;
        padding: 0px;
        .cocktail-desc-review-name-time {
          @include font($fs-main, $fw-medium);
          padding: 0px;
          gap: 8px;
          .cocktail-desc-review-time-compare {
            @include flex;
            @include font($fs-sm, $fw-regular);
            gap: 5px;
            color: $navy600;
            .cocktail-desc-review-compare {
              @include font($fs-sm, $fw-regular);
              color: $navy400;
            }
          }
        }
        .cocktail-desc-review-button {
          @include flex;

          .buttonstyle {
            @include font($fs-sm, $fw-regular);
            color: $navy400;
            padding: 0px;
          }
        }
      }
    }
  }
}
.modal-basic {
  .delete-modal-content {
    @include flex(column);
    justify-content: center;
    align-items: center;
    gap: 36px;
    width: 300px;

    @media (max-width: 300px) {
      width: 80vw;
    }

    p {
      @include font($fs-lg, $fw-medium);
      text-align: center;
    }
  }

  .delete-modal-button {
    @include flex-xy(center, center);
    gap: 20px;
    width: 100%;

    button {
      @include p-component(md);
      @include shadow-modal;
    }
  }
}
</style>
