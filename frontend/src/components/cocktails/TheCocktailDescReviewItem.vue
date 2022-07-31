<template>
  <div
    class="cocktail-desc-review"
    v-for="(review, id) in reviews"
    :key="`main-${id}`"
  >
    <div class="cocktail-desc-review-profile">
      <round-image
        :round-image="{ image: review.image, width: '45px' }"
      ></round-image>
      <div class="cocktail-desc-review-content">
        <div class="cocktail-decs-review-front">
          <div class="cocktail-desc-review-name-time">
            <p class="cocktail-desc-review-profile-name">{{ review.name }}</p>
            <p class="cocktail-desc-review-time">{{ review.time }}</p>
          </div>
          <div
            v-if="userInfo.nickname == review.name"
            class="cocktail-desc-review-button"
          >
            <button-basic
              :button-style="[buttonColor, '50px', 'small']"
              :disabled="!reviewEditValue"
              class="buttonstyle"
            >
              수정
            </button-basic>
            <button-basic
              :button-style="[buttonColor, '50px', 'small']"
              :disabled="!reviewEditValue"
              class="buttonstyle"
            >
              삭제
            </button-basic>
          </div>
        </div>
        <p>
          {{ review.content }}
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { useStore } from 'vuex'
import RoundImage from '@/components/basics/RoundImage.vue'
import ButtonBasic from '@/components/basics/ButtonBasic.vue'

// 유저 정보 불러오기
const store = useStore()
const userInfo = computed(() => store.getters['personalInfo/getUserInfo'])

// profile image
const reviews = ref([
  {
    image:
      'https://mblogthumb-phinf.pstatic.net/MjAxNzA4MjJfMjcw/MDAxNTAzMzU1NTI5Mjg0.OBV0OZkJQHRZzIWAtVDM60JLl9wq5WwiwnRTwgYqDq4g.II9maLicfuatQ8bxN7F6uUt1ZVa_95hP2OVB0Ig4uf8g.JPEG.doghter4our/IMG_0907.jpg?type=w800',
    name: '홍길동',
    time: '방금 전',
    content:
      '첫 맛에 인위적이지 않은 라임맛이 강하게 느껴진다. 가볍고 상큼하기만 하다. 진하지가 않다. 맛있다 맛있다 맛있다',
  },
  {
    Image:
      'https://postfiles.pstatic.net/20131008_266/jane0014_1381217202727MJPj3_JPEG/2_5347259.jpg?type=w3',
    name: '레몬이좋아',
    time: '두 시간 전',
    content:
      '첫 맛에 인위적이지 않은 레몬맛이 강하게 느껴진다. 가볍고 상큼하기만 하다. 진하지가 않다. 맛있다 맛있다 맛있다',
  },
])

// button
const reviewEditValue = ref('')
// button color
const buttonColor = computed(() => {
  return 'sub-blank'
})

// // 리뷰 삭제
// const deleteReview = (deleteReviewId) => {
//   const deleteReviewIndex = reviews.value.findIndex(
//     (review) => review.id == deleteReviewId,
//   )

//   reviews.value.splice(deleteReviewIndex, 1)
// }
</script>

<style scoped lang="scss">
.cocktail-desc-review-profile {
  @include flex-center;
  width: 100%;
  gap: 12px;
  .cocktail-desc-review-content {
    @include flex;
    @include font($fs-md, $fw-regular);
    flex-direction: column;
    width: 100%;

    .cocktail-decs-review-front {
      @include flex;
      width: 100%;
      flex-direction: row;
      justify-content: space-between;
      padding: 0px;
      gap: 159px;
      .cocktail-desc-review-name-time {
        @include flex;
        flex-direction: row;
        align-items: flex-end;
        padding: 0px;
        gap: 8px;
        .cocktail-desc-review-time {
          @include font($fs-md, $fw-regular);
          @include font-size-unchecked($fs-md);
        }
      }
      .cocktail-desc-review-button {
        @include flex;

        .buttonstyle {
          @include font-size-unchecked($fs-md);
          padding: 0px;
        }
      }
    }
  }
}
</style>
