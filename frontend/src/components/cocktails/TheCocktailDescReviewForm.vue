<template>
  <!-- {{ profile }} -->
  <form class="cocktail-desc-review-profile-input" @submit.prevent="">
    <div class="cocktail-desc-review-profile">
      <round-image
        :round-image="{ image: profile.image, width: '45px' }"
      ></round-image>
      <p>{{ profile.name }}</p>
    </div>
    <div class="cocktail-desc-review-input-notice">
      <div class="cocktail-desc-review-input">
        <textarea-basic
          :data="reviewInputData"
          :textarea-style="reviewInputStyle"
          v-model="reviewInputValue"
        ></textarea-basic>

        <!-- 유효성 검사 -->
        <div class="review-auth-error" v-if="occurredError">
          <p v-for="(msg, index) in errorMessage" :key="index">{{ msg }}</p>
        </div>
        <!-- catch 메시지-->
        <p class="review-Fail-message" v-if="reviewFailStatus">
          비밀번호가 틀렸습니다. 다시 확인해주세요.
        </p>
        <button-basic
          :button-style="[buttonColor, '66px', 'small']"
          :disabled="!reviewInputValue"
          class="buttonstyle"
        >
          등록
        </button-basic>
      </div>
      <p>
        광고 및 욕설, 비속어나 타인을 비방하는 문구를 사용하면 비공개 될 수
        있습니다.
      </p>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import TextareaBasic from '@/components/basics/TextareaBasic.vue'
import RoundImage from '@/components/basics/RoundImage.vue'
import ButtonBasic from '@/components/basics/ButtonBasic.vue'
import { checkBadWord } from '../../functions/checkText'

const text = ref('')
// profile image
const profile = reactive({
  image:
    'https://mblogthumb-phinf.pstatic.net/MjAxNzA4MjJfMjcw/MDAxNTAzMzU1NTI5Mjg0.OBV0OZkJQHRZzIWAtVDM60JLl9wq5WwiwnRTwgYqDq4g.II9maLicfuatQ8bxN7F6uUt1ZVa_95hP2OVB0Ig4uf8g.JPEG.doghter4our/IMG_0907.jpg?type=w800',
  name: '홍길동',
})

// textarea-form
const reviewInputValue = ref('')
// console.log(reviewInputValue)

const reviewInputData: object = reactive({
  button: false,
  placeholder: '후기를 남겨주세요.',
  type: 'text',
  maxlength: 100,
})

const reviewInputStyle = ref('normal')

// 유효성 검사
const reviewCondition = checkBadWord(reviewInputValue.value)

const errorMessage: string[] = reactive([])
const occurredError = ref(false)
const reviewConditionErrorMessage: string =
  '리뷰에는 욕설을 포함할 수 없습니다.'

// 에러 메시지
const reviewFailStatus = ref(false)

// button color
const buttonColor = computed(() => {
  if (reviewInputValue.value) {
    return 'primary-blank'
  } else {
    return 'unchecked-blank'
  }
})
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
        padding: 15px;
      }
    }
  }
}
</style>
