import { createRouter, createWebHistory, useRouter } from "vue-router";
import createStore from "../src/store/index";
import { computed } from "vue";

// 스토어 정의
const store = createStore;

const routes: any[] = [
  // 홈 화면
  {
    path: "/",
    name: "TheHomeView",
    component: () => import("@/views/TheHomeView.vue"),
  },

  // 회원가입 화면
  {
    path: "/user/signup",
    name: "TheSignupView",
    component: () => import("@/views/TheSignupView.vue"),
  },

  // 로그인 화면
  {
    path: "/user/login",
    name: "TheLoginView",
    component: () => import("@/views/TheLoginView.vue"),
  },

  // 로그아웃 화면
  {
    path: "/user/sign-out",
    name: "TheSignOutView",
    component: () => import("@/views/TheSignOutView.vue"),
  },

  // 유저 정보 수정
  {
    path: "/user/my-info",
    name: "TheChangeUserView",
    component: () => import("@/views/TheChangeUserView.vue"),
  },

  // 비밀번호 찾기 화면
  {
    path: "/user/help/password",
    name: "TheForgotPwView",
    component: () => import("@/views/TheForgotPwView.vue"),
  },

  // 비밀번호 변경 화면
  {
    path: "/user/my-info/password",
    name: "TheChangePwView",
    component: () => import("@/views/TheChangePwView.vue"),
  },

  // 칵테일 검색 페이지
  {
    path: "/cocktail/search",
    name: "TheCocktailSearchView",
    component: () => import("@/views/TheCocktailSearchView.vue"),
  },

  // 칵테일 검색 결과(재료) 페이지
  {
    path: "/cocktail/search/byingredients/:ingredientId",
    name: "TheSearchCocktailByIngredientView",
    component: () => import("@/views/TheSearchCocktailByIngredientView.vue"),
  },

  // 칵테일 검색 결과 페이지
  {
    path: "/cocktail/search/:searchValue",
    name: "TheSearchResultView",
    component: () => import("@/views/TheSearchResultView.vue"),
  },
  // 칵테일 필터 검색 결과 페이지
  {
    path: "/cocktail/search/filter",
    name: "TheSearchFilterResultView",
    component: () => import("@/views/TheSearchFilterResultView.vue"),
  },

  // 칵테일 전체 리스트 페이지
  {
    path: "/cocktail/whole",
    name: "TheWholeCocktailView",
    component: () => import("@/views/TheWholeCocktailView.vue"),
  },

  // 필터 카테고리
  {
    path: "/cocktail/search/filter/:category",
    name: "TheCocktailFilterCategoryView",
    component: () => import("@/views/TheCocktailFilterCategoryView.vue"),
  },

  // 칵테일 상세 페이지
  {
    path: "/cocktail/:cocktailId",
    name: "TheCocktailDescView",
    component: () => import("@/views/TheCocktailDescView.vue"),
  },

  // 칵테일 상세 페이지 북마크
  {
    path: "/cocktail/:cocktailId/bookmark",
    name: "TheCocktailDescBookmarkView",
    component: () => import("@/views/TheCocktailDescBookmarkView.vue"),
  },

  // 커스텀 칵테일 제작 페이지
  {
    path: "/cocktail/:cocktailId/custom/form",
    name: "TheCustomCocktailFormView",
    component: () => import("@/views/TheCustomCocktailFormView.vue"),
  },

  // 커스텀 칵테일 상세 페이지
  {
    path: "/cocktail/:cocktailId/custom/:customCocktailId",
    name: "TheCustomCocktailDescView",
    component: () => import("@/views/TheCustomCocktailDescView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Navigation Guard 설정
router.beforeEach((to: any, from, next) => {
  // 로그인 여부 확인
  const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);

  // 로그인이 필요한 페이지
  const authPages = ["TheSignOutView", "TheChangeUserView", "TheChangePwView"];

  // 로그인이 되어있지 않을 때만 가능한 페이지
  const notAuthPages = ["TheSignupView", "TheLoginView"];

  // 1. 현재 이동하고자 하는 페이지가 로그인이 필요한지 확인
  const isAuthRequired = authPages.includes(to.name);
  const isNotAuthRequired = notAuthPages.includes(to.name);

  // 2. 로그인이 필요한 페이지인데 로그인이 되어있지 않다면 로그인 페이지(/login)로 이동
  if (isAuthRequired && !isLoggedIn.value) {
    next({ name: "TheLoginView" });

    // 3. 로그인이 되어있는데 /login, /signup 페이지로 이동한다면 메인 페이지(/)로 이동
  } else if (isNotAuthRequired && isLoggedIn.value) {
    next({ name: "TheHomeView" });

    // 4.  원래 이동 페이지
  } else {
    next();
  }
});

export default router;
