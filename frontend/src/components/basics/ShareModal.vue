<!--  on, off시 트랜지션 효과를 준다.  -->
<template>
  <teleport to="body">
    <div class="share-modal">
      <div
        class="container"
        :class="animation"
        :style="[isIphone ? { height: '350px' } : { height: '250px' }]"
      >
        <!-- filter: header -->
        <section class="header-section">
          <span class="material-icons invisible"> close </span>
          <p>공유</p>
          <span class="material-icons icon-close" @click="clickXIcon">
            close
          </span>
        </section>
        <hr />
        <!-- 버튼 부분 -->
        <section class="btn-section">
          <!-- 링크 복사 -->
          <article
            class="super-custom-cocktail-post post-btn"
            @click="clickShareLink"
          >
            <div class="btn-icon">
              <span class="material-icons"> link </span>
            </div>
            <div class="btn-text">링크 복사</div>
          </article>
          <!-- 카카오 공유 -->
          <article class="normal-post post-btn" @click="clickShareKakao">
            <div class="btn-icon">
              <img
                class="icon-social icon-kakao"
                src="https://raw.githubusercontent.com/JaeKP/image_repo/main/img/kakao.png"
              />
            </div>
            <div class="btn-text">카카오톡</div>
          </article>
          <!-- 페이스북 공유 -->
          <article class="normal-post post-btn" @click="clickShareFacebook">
            <div class="btn-icon">
              <img
                class="icon-social icon-facebook"
                src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/Facebook_f_logo_%282019%29.svg/1365px-Facebook_f_logo_%282019%29.svg.png"
              />
            </div>
            <div class="btn-text">페이스북</div>
          </article>
          <!-- 트위터 공유 -->
          <article class="normal-post post-btn" @click="clickShareTwitter">
            <div class="btn-icon">
              <img
                class="icon-social icon-facebook"
                src="https://www.pngkey.com/png/full/2-27646_twitter-logo-png-transparent-background-logo-twitter-png.png"
              />
            </div>
            <div class="btn-text">트위터</div>
          </article>
        </section>
      </div>
    </div>
  </teleport>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import { useStore } from "vuex";
import { useRouter, onBeforeRouteLeave } from "vue-router";
const router = useRouter();
const store = useStore();

// 애니메이션 상태
const animation = computed(() => store.getters["share/getShareModalClass"]);

// 필터 off
const clickXIcon = () => {
  store.dispatch("share/changeShareModalClass", "end");
  setTimeout(() => store.dispatch("share/toggleShareModal", false), 200);
};

// 개발환경 URL
const url = encodeURI(window.location.href);
// 서비스 환경 URL
// const originUrl = encodeURI(window.location.href);
// const path = originUrl.split("http://127.0.0.1:5173").splice(1).pop();
// const url = "http://jourgeois.com" + path;

// 링크 복사 클릭
const clickShareLink = () => {
  navigator.clipboard.writeText(window.location.href);
  alert("링크가 복사되었습니다.");
};

// 카카오톡 공유 클릭
const clickShareKakao = () => {
  window.Kakao.Link.sendDefault({
    objectType: "feed",
    content: {
      title: "주류주아",
      description: "당신을 위한 칵테일 지침서",
      imageUrl:
        "https://user-images.githubusercontent.com/86189596/183279153-91bfcbef-fa3f-4639-b93d-bfc7e4bcea26.png",
      link: {
        webUrl: url,
        mobileWebUrl: url,
      },
    },
    buttons: [
      {
        title: "웹으로 이동",
        link: {
          webUrl: url,
          mobileWebUrl: url,
        },
      },
    ],
  });
};

// 페이스북 공유 클릭
const clickShareFacebook = () => {
  window.open("http://www.facebook.com/sharer/sharer.php?u=" + url);
};

// 트위터 공유 클릭
const clickShareTwitter = () => {
  const text = "주류주아에서 즐겨보세요";
  window.open("https://twitter.com/intent/tweet?text=" + text + "&url=" + url);
};

// 아이폰인지 확인
const deviceType: string = store.getters["navbar/getDeviceType"];
const isIphone = computed(() => {
  return deviceType === "iphone";
});

const shareModalStatus = computed(
  () => store.getters["share/getShareModalStatus"]
);

onBeforeRouteLeave((to, from, next) => {
  if (shareModalStatus.value) {
    clickXIcon();
  } else {
    // next();
  }
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
    transform: translate3d(0);
  }
}

.share-modal {
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
    height: 200px;
    border-top-left-radius: 30px;
    border-top-right-radius: 30px;
    padding: 0 16px;
    background-color: white;

    @media #{$tablet} {
      width: 64%;
    }

    @media (min-height: 750px) {
      height: 200px;
    }

    .header-section {
      @include flex-xy(space-between, flex-start);
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

      .icon-close {
        @include for-click;
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
    width: 60px;
    height: 60px;
    border: 1px solid $white150;
    border-radius: 20px;
    background-color: $white100;
    @include shadow-feed;
    @include flex-center;
    @include font-size-sub(15px);

    .material-icons-outlined {
      @include font-size-sub(30px);
    }

    .material-icons {
      @include font-size-sub(30px);
    }
  }
  .btn-text {
    @include font-size-sub(12px);
  }
}

.icon-social {
  width: 30px;
  aspect-ratio: 1/1;
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
