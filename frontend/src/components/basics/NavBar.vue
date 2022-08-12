<template>
  <div id="nav-bar">
    <span
      class="nav-icon material-icons-outlined"
      :class="{ selected: navIconStatus[0], unselected: !navIconStatus[0] }"
      @click="clickHome"
    >
      home
    </span>
    <span
      class="nav-icon material-icons-outlined"
      :class="{ selected: navIconStatus[1], unselected: !navIconStatus[1] }"
      @click="clickNewsFeed"
    >
      groups
    </span>
    <span
      class="nav-icon-important nav-icon material-icons-outlined"
      :class="{ selected: navIconStatus[2], unselected: !navIconStatus[2] }"
      @click="clickPost"
      >add_circle_outline</span
    >
    <span
      class="nav-icon material-icons-outlined"
      :class="{ selected: navIconStatus[3], unselected: !navIconStatus[3] }"
      @click="clickSearch"
    >
      search
    </span>
    <span
      class="nav-icon material-icons-outlined"
      :class="{ selected: navIconStatus[4], unselected: !navIconStatus[4] }"
      @click="clickProfile"
      >account_circle</span
    >
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";
import { onBeforeRouteLeave, useRouter } from "vue-router";
const router = useRouter();
const store = useStore();
const navIconStatus = computed(() => store.getters["navbar/getNavIconStatus"]);
const userId = computed(() => store.getters["personalInfo/getUserInfoUserId"]);


const setNavIconStatus = (index: number) => {
  store.dispatch("navbar/setNavIconStatus", index);
};

// 홈 버튼 클릭
const clickHome = () => {
  setNavIconStatus(0);
  router.push({ name: "TheHomeView" });
};

// 뉴스피드 버튼 클릭
const clickNewsFeed = () => {
  setNavIconStatus(1);
  router.push({ name: "TheNewsFeedView" });
};

// 게시 버튼 클릭
const clickPost = () => {
  setNavIconStatus(2);
  store.dispatch("createFeed/changeCreateFeedModalClass", "start");
  store.dispatch("createFeed/toggleCreateFeedModal", true);
};

// 검색 버튼 클릭
const clickSearch = () => {
  setNavIconStatus(3);
  store.dispatch("cocktailSearch/toggleFilter", false);
  router.push({ name: "TheCocktailSearchView" });
};

// 프로필 버튼 클릭
const clickProfile = () => {
  setNavIconStatus(4);
  router.push({ name: "TheUserProfileView", params: { userId : userId.value || 0 } });
};
const createFeedModalStatus = computed(
  () => store.getters["createFeed/getCreateFeedModalStatus"]
);

onBeforeRouteLeave((to, from, next) => {
  if (createFeedModalStatus.value) {
    store.dispatch("createFeed/changeCreateFeedModalClass", "end");
    setTimeout(
      () => store.dispatch("createFeed/toggleCreateFeedModal", false),
      200
    );
  } else {
    next();
  }
});
</script>

<style scoped lang="scss">
#nav-bar {
  position: fixed;
  bottom: 0;
  @include flex-xy(space-around, center);
  width: 100%;
  height: 64px;
  background-color: $white;
  z-index: 1;
  box-shadow: 0px -1.5px 11px rgba(56, 55, 68, 0.1);

  .nav-icon {
    cursor: pointer;
    @include font(30px);
  }

  // .nav-icon-important {
  //   @include font(40px);
  // }
}

.selected {
  color: $primary-color;
}

.unselected {
  color: $sub-color;
}
</style>
