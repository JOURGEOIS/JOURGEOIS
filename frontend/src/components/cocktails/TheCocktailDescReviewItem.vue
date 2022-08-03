<template>
  <div
    class="cocktail-desc-review"
    v-for="(review, id) in reviews"
    :key="`main-${id}`"
  >
    <div class="cocktail-desc-review-profile-content">
      <div class="cocktail-desc-review-profile">
        <round-image
          :round-image="{ image: review.image, width: '45px', height: '45px' }"
        ></round-image>
      </div>
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

const img: string = userInfo.value.profileImg
const name: string = userInfo.value.nickname

// profile image
const reviews = ref([
  {
    image: img,
    name: name,
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
</script>

<style scoped lang="scss">
.cocktail-desc-review {
  @include flex-center;
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
