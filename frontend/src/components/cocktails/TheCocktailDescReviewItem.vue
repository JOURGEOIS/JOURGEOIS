<template>
  <div class="cocktail-desc-review">
    <div class="cocktail-desc-review-profile-content">
      <div class="cocktail-desc-review-profile">
        <round-image
          :round-image="{
            image: data.profileImg,
            width: '45px',
            height: '45px',
          }"
        ></round-image>
      </div>
      <div class="cocktail-desc-review-content">
        <div class="cocktail-decs-review-front">
          <div class="cocktail-desc-review-name-time">
            <p class="cocktail-desc-review-profile-name">
              {{ data.nickname }}
            </p>
            <p>{{ calc }}</p>
          </div>
          <div
            v-if="userInfo.nickname == data.nickname"
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
          {{ data.comment }}
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
import { calcDateDelta } from '../../functions/date'

interface cocktailReviewData {
  commentId: number
  userId: number
  nickname: string
  profileImg: string | null
  cocktailId: number
  comment: string
  createdDate: number[]
  modifiedDate: number[]
}

const props = defineProps<{
  data: cocktailReviewData
}>()

// 유저 정보 불러오기
const store = useStore()
const userInfo = computed(() => store.getters['personalInfo/getUserInfo'])
const userId = computed(() => store.getters['personalInfo/getUserInfoUserId'])

const img: string = userInfo.value.profileImg
const name: string = userInfo.value.nickname

// 칵테일 날짜
const calc = calcDateDelta(props.data.createdDate)
// console.log(props.data.createdDate)
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
