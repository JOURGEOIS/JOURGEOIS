<template>
  <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
    프로필
  </header-basic>
  <div class="the-user-profile-view top-view">
    <the-user-profile-basic></the-user-profile-basic>
    <!-- 프로필: 탭-->
    <section class="the-user-profile-section">
      <hr class="user-profile-hr" />
      <!-- 탭: 탭을 선택하면 해당 탭으로 컴포넌트를 바꾼다.  -->
      <div class="user-profile-tab">
        <div class="user-profile-tab-community" :class="`index-${index}`">
          <p @click="clickCommunityTab">일반</p>
        </div>
        <div class="user-profile-tab-custom" :class="`index-${index}`">
          <p @click="clickCustomCocktailTab">커스텀</p>
          <!-- <span>99+</span> -->
        </div>
        <div class="user-profile-tab-review" :class="`index-${index}`">
          <p @click="clickReviewTab">후기</p>
          <!-- <span>99+</span> -->
        </div>
        <div class="user-profile-tab-bookmark" :class="`index-${index}`">
          <p @click="clickBookmarkTab">북마크</p>
          <!-- <span>99+</span> -->
        </div>
      </div>
      <!-- 동적 컴포넌트: 탭에 따라 변경된다. -->
      <keep-alive>
        <component :is="currentComponent"></component>
      </keep-alive>
    </section>
  
  </div>
  <nav-bar></nav-bar>
</template>

<script setup lang="ts">
import { computed, defineAsyncComponent, watch, onMounted } from 'vue'
import { useRoute } from "vue-router";
import { useStore } from 'vuex'
import TheUserProfileBasic from '@/components/profile/TheUserProfileBasic.vue'
import HeaderBasic from '@/components/basics/HeaderBasic.vue'
import NavBar from '@/components/basics/NavBar.vue'
const route = useRoute();
const store = useStore()

// 동적 컴포넌트 (탭)
const componentArray = [
  defineAsyncComponent(
    () => import("@/components/profile/TheCommunityPostList.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/profile/TheCustomCocktailPostList.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/profile/TheReviewPostList.vue")
  ),
  defineAsyncComponent(
    () => import("@/components/profile/TheBookmarkCocktailList.vue")
  ),
];

const index = computed(() => store.getters["profileDesc/getCurrentTab"]);
const currentComponent = computed(() => {
  return componentArray[index.value];
});

const clickCommunityTab = () => store.dispatch("profileDesc/changeCurrentTab", 0);
const clickCustomCocktailTab = () => store.dispatch("profileDesc/changeCurrentTab", 1);
const clickReviewTab = () => store.dispatch("profileDesc/changeCurrentTab", 2);
const clickBookmarkTab = () => store.dispatch("profileDesc/changeCurrentTab", 3);

// 동적 라우팅
onMounted(() => {
  store.dispatch("profileDesc/getCurrentUserData")
});

</script>

<style scoped lang="scss">
.the-user-profile-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;
  margin-bottom: 48px;
  .top-view {
    width: 100%;
  }
  section {
    @include flex(column);
    justify-content: center;
    align-items: center;
    gap: 40px;
    width: 100%;
    margin-top: 40px;

    @media #{$tablet} {
      gap: 40px;
    }

    .user-profile-hr {
      width: 100%;
      height: 1px;
      border: 0;
      background-color: $unchecked-color;
      margin: 0;
    }
    .user-profile-tab {
      @include flex-xy(space-around, center);
      width: 100%;
      div {
        @include flex-center;
        position: relative;
        width: 72px;
        border-bottom: 2px solid $white400;

        @media #{$tablet} {
          width: 96px;
        }
        
        @media #{$pc} {
          width: 120px;
        }

        p {
          color: $white400;
          @include font($fs-main, $fw-medium);
          padding-bottom: 6px;
          cursor: pointer;
        }

        span {
          position: absolute;
          right: -8px;
          top: -2px;
          @include font($fs-sm, $fw-regular);
          color: $white300;
        }
      }
      // 탭 활성화 색상 변경
      .user-profile-tab-community.index-0 {
        border-bottom: 2px solid $primary-color;
        p {
          color: $main-color;
        }
      }

      .user-profile-tab-custom.index-1 {
        border-bottom: 2px solid $primary-color;
        p {
          color: $main-color;
        }
        span {
          color: $white400;
        }
      }
      .user-profile-tab-review.index-2 {
        border-bottom: 2px solid $primary-color;
        p {
          color: $main-color;
        }
        span {
          color: $white400;
        }
      }
      .user-profile-tab-bookmark.index-3 {
        border-bottom: 2px solid $primary-color;
        p {
          color: $main-color;
        }
        span {
          color: $white400;
        }
      }   
    }
  }
}
</style>