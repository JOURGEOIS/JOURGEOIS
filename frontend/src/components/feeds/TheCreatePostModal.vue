<!--  on, off시 트랜지션 효과를 준다.  -->
<template>
  <teleport to="body">
    <div class="create-post-modal" @click.self="clickXIcon">
      <div
        class="container"
        :class="animation"
        :style="[
          isIphone || isAndroid ? { height: '350px' } : { height: '250px' },
        ]"
      >
        <!-- filter: header -->
        <section class="header-section">
          <!-- <span class="material-icons invisible"> close </span> -->
          <p>업로드</p>
          <!-- <span class="material-icons" @click="clickXIcon"> close </span> -->
        </section>
        <hr />
        <!-- 버튼 부분 -->
        <section class="btn-section">
          <!-- 나만의 칵테일 -->
          <article
            class="super-custom-cocktail-post post-btn"
            @click="createCustomCocktail"
          >
            <div class="btn-icon">
              <span class="material-icons-outlined cocktail-icon">
                wine_bar
              </span>
            </div>
            <div class="btn-text">나만의 칵테일</div>
          </article>
          <article class="normal-post post-btn" @click="createNormal">
            <div class="btn-icon">
              <span class="material-icons-outlined"> edit </span>
            </div>
            <div class="btn-text">나의 일상</div>
          </article>
        </section>
      </div>
    </div>
  </teleport>
</template>

<script setup lang="ts">
import { computed, ref, onBeforeMount } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
const router = useRouter();
const store = useStore();

// 애니메이션 상태
const animation = computed(
  () => store.getters["createFeed/getCreateFeedModalClass"]
);

// 필터 off
const clickXIcon = () => {
  store.dispatch("createFeed/changeCreateFeedModalClass", "end");
  setTimeout(
    () => store.dispatch("createFeed/toggleCreateFeedModal", false),
    200
  );
};

// 슈퍼커스텀칵테일 업로드
const createCustomCocktail = () => {
  store.dispatch("createFeed/toggleCreateFeedModal", false);
  router.push({ name: "TheSuperCustomCocktailFormView" });
};

// 일반게시물 업로드
const createNormal = () => {
  store.dispatch("createFeed/toggleCreateFeedModal", false);
  router.push({ name: "TheCommunityFormView" });
};

// 아이폰인지 확인
const deviceType: string = store.getters["navbar/getDeviceType"];
const isIphone = computed(() => {
  return deviceType === "iphone";
});

const isAndroid = computed(() => {
  return deviceType === "android";
});
</script>

<style scoped lang="scss">
@keyframes start {
  to {
    transform: translate3d(0, 0, 0);
  }
  from {
    transform: translate3d(0, 100%, 0);
  }
}

@keyframes end {
  to {
    transform: translate3d(0, 100%, 0);
  }
  from {
    transform: translate3d(0, 0, 0);
  }
}

.create-post-modal {
  @include flex-xy(center, center);
  position: fixed;
  top: 0;
  height: 100vh;
  z-index: 10;
  width: 100vw;
  background-color: rgba(0, 0, 0, 0.7);
  .container {
    @include flex(column);
    gap: 11px;
    position: absolute;
    bottom: 0px;
    width: 100%;
    max-width: 600px;
    height: 350px;
    border-top-left-radius: 30px;
    border-top-right-radius: 30px;
    padding: 0 16px;
    background-color: white;

    @media #{$tablet} {
      width: 64%;
    }

    @media (min-height: 750px) {
      height: 250px;
    }

    .header-section {
      @include flex-center;
      margin-top: 24px;

      p {
        &:first-child {
          @include font($fs-main, $fw-medium);
          color: $main-color;
        }
      }
      .material-icons-outlined {
        position: absolute;
        right: 5px;
        width: 42.78px;
        text-align: center;
        cursor: pointer;
      }
    }

    hr {
      width: calc(100% + 32px);
      margin-left: -16px;
      height: 1px;
      border: 0;
      background-color: $unchecked-color;
    }

    .btn-section {
      @include flex-xy(space-around, center);
      margin-top: 10px;
    }
  }
}

.post-btn {
  @include flex(column);
  @include flex-xy(center, center);
  @include for-click;
  gap: 10px;
  .btn-icon {
    width: 100px;
    height: 100px;
    border: 1px solid $white150;
    border-radius: 20px;
    background-color: $white100;
    @include shadow-popup1;
    @include flex-center;
    @include font-size-sub(15px);

    .material-icons-outlined {
      @include font-size-sub(50px);
    }
  }
  .btn-text {
    @include font-size-sub(15px);
  }
}

.start {
  animation: start 0.3s ease-in-out;
}

.end {
  animation: end 0.3s ease-in-out;
}

.invisible {
  visibility: hidden;
}
</style>
