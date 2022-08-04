<template>
  <div
    class="cocktail-desc-review"
    @load="getReview(cocktailId)"
    v-for="(review, id) in cocktailReviewData"
    :key="`main-${id}`"
  >
    <div class="cocktail-desc-review-profile-content">
      <div class="cocktail-desc-review-profile">
        <round-image
          :round-image="{
            image: review.profileImg,
            width: '45px',
            height: '45px',
          }"
        ></round-image>
      </div>
      <div class="cocktail-desc-review-content">
        <div class="cocktail-decs-review-front">
          <div class="cocktail-desc-review-name-time">
            <p class="cocktail-desc-review-profile-name">
              {{ review.nickname }}
            </p>
            <p
              v-if="
                review.createdDate[0] === year &&
                review.createdDate[1] === month &&
                review.createdDate[2] === day &&
                review.createdDate[3] === hours &&
                review.createdDate[4] === minutes
              "
            >
              방금 전
            </p>
            <p
              v-else-if="
                review.createdDate[0] === year &&
                review.createdDate[1] === month &&
                review.createdDate[2] === day &&
                review.createdDate[3] === hours &&
                review.createdDate[4] - minutes > 0
              "
            >
              {{ review.createdDate[4] - minutes }}분 전
            </p>
            <p
              v-else-if="
                review.createdDate[0] === year &&
                review.createdDate[1] === month &&
                review.createdDate[2] === day &&
                review.createdDate[3] - hours > 0
              "
            >
              {{ review.createdDate[3] - hours }}시간 전
            </p>
            <p
              v-else-if="
                review.createdDate[0] === year &&
                review.createdDate[1] === month &&
                review.createdDate[2] - day > 0
              "
            >
              {{ review.createdDate[2] - day }}일 전
            </p>
            <p
              v-else-if="
                review.createdDate[0] === year &&
                review.createdDate[1] - month > 0
              "
            >
              {{ review.createdDate[1] - month }}달 전
            </p>
            <p v-else-if="review.createdDate[0] - year > 0">
              {{ review.createdDate[0] - year }}년 전
            </p>
          </div>
          <div
            v-if="userInfo.nickname == review.nickname"
            class="cocktail-desc-review-button"
          >
            <button-basic
              :button-style="[buttonColor, '40px']"
              :disabled="!reviewEditValue"
              class="buttonstyle"
            >
              수정
            </button-basic>
            <button-basic
              :button-style="[buttonColor, '40px']"
              :disabled="!reviewEditValue"
              class="buttonstyle"
            >
              삭제
            </button-basic>
          </div>
        </div>
        <p>
          {{ review.comment }}
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
const userId = computed(() => store.getters['personalInfo/getUserInfoUserId'])

const img: string = userInfo.value.profileImg
const name: string = userInfo.value.nickname

// 칵테일 후기 정보 불러오기
const cocktailReviewData = computed(
  () => store.getters['cocktailReview/getCurrentCocktailReview'],
)
const getReview = (cocktailId: number) =>
  store.dispatch('cocktailReview/getCocktailReview', cocktailId)
// 칵테일 id
const cocktailData = computed(
  () => store.getters['cocktailDesc/getCurrentCocktailData'],
)
const cocktailId = Number(cocktailData.value.id)

getReview(cocktailId)

console.log('cacktailInfo: ', cocktailReviewData.value)
// 칵테일 날짜
const today = new Date()

const year = today.getFullYear()
const month = today.getMonth() + 1
const day = today.getDate()
const hours = today.getHours()
const minutes = today.getMinutes()
const seconds = today.getSeconds()

console.log(today, year, month, day, hours, minutes, seconds)

// button
const reviewEditValue = ref('')
// button color
const buttonColor = computed(() => {
  return 'sub-blank'
})
</script>

<style scoped lang="scss">
.cocktail-desc-review {
  @include flex;
  width: 100%;
  .cocktail-desc-review-profile-content {
    @include flex;
    gap: 12px;
    .cocktail-desc-review-profile {
      @include flex;
      align-items: center;
    }
    .cocktail-desc-review-content {
      @include flex;
      @include font($fs-md, $fw-regular);
      flex-grow: 1;
      flex-direction: column;
      width: 100%;

      .cocktail-decs-review-front {
        @include flex;

        justify-content: space-between;
        padding: 0px;
        .cocktail-desc-review-name-time {
          @include flex-center;

          padding: 0px;
          gap: 8px;
          .cocktail-desc-review-time {
            @include flex;
            @include font($fs-sm, $fw-regular);
            @include font-size-unchecked($fs-md);
          }
        }
        .cocktail-desc-review-button {
          @include flex;

          .buttonstyle {
            @include font-size-unchecked($fs-sm);
            padding: 0px;
          }
        }
      }
    }
  }
}
</style>
