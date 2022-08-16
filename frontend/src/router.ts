import { createRouter, createWebHistory } from "vue-router";
import createStore from "../src/store/index";
import { computed } from "vue";

const routes: any[] = [
  // 홈 화면
  {
    path: "/",
    name: "TheHomeView",
    component: () => import("@/views/homes/TheHomeView.vue"),
  },

  //======================= accounts =======================
  // 회원가입 화면
  {
    path: "/user/signup",
    name: "TheSignupView",
    component: () => import("@/views/accounts/TheSignupView.vue"),
  },

  // 로그인 화면
  {
    path: "/user/login",
    name: "TheLoginView",
    component: () => import("@/views/accounts/TheLoginView.vue"),
  },

  // 카카오 로그인
  {
    path: "/user/login/kakaologin",
    name: "Kakaologin",
    component: () => import("@/views/accounts/KakaoLogin.vue"),
  },

  // 회원 탈퇴 화면
  {
    path: "/user/sign-out",
    name: "TheSignOutView",
    component: () => import("@/views/accounts/TheSignOutView.vue"),
  },

  // 유저 정보 수정
  {
    path: "/user/my-info",
    name: "TheChangeUserView",
    component: () => import("@/views/accounts/TheChangeUserView.vue"),
  },

  // 비밀번호 찾기 화면
  {
    path: "/user/help/password",
    name: "TheForgotPwView",
    component: () => import("@/views/accounts/TheForgotPwView.vue"),
  },

  // 비밀번호 변경 화면
  {
    path: "/user/my-info/password",
    name: "TheChangePwView",
    component: () => import("@/views/accounts/TheChangePwView.vue"),
  },

  // 유저 프로필로 이동
  {
    path: "/user/profile/:userId",
    name: "TheUserProfileView",
    component: () => import("@/views/accounts/TheUserProfileView.vue"),
  },

  // 프로필 공개 설정 화면
  {
    path: "/user/profile/private",
    name: "TheUserProfilePrivateModeView",
    component: () =>
      import("@/views/accounts/TheUserProfilePrivateModeView.vue"),
  },

  // 팔로워 리스트 보기
  {
    path: "/user/follower/:userId",
    name: "TheFollowerListView",
    component: () => import("@/views/accounts/TheFollowerListView.vue"),
  },

  // 팔로잉 리스트 보기
  {
    path: "/user/followee/:userId",
    name: "TheFollowingListView",
    component: () => import("@/views/accounts/TheFollowingListView.vue"),
  },

  // 이메일 인증 성공 페이지
  {
    path: "/email/success",
    name: "TheEmailSuccessFormView",
    component: () => import("@/views/TheEmailSuccessFormView.vue"),
  },

  // 이메일 인증 실패 페이지
  {
    path: "/email/fail",
    name: "TheEmailFailFormView",
    component: () => import("@/views/TheEmailFailFormView.vue"),
  },

  //======================= search =======================
  // 칵테일 검색 페이지
  {
    path: "/cocktail/search",
    name: "TheCocktailSearchView",
    component: () => import("@/views/search/TheCocktailSearchView.vue"),
  },

  // 칵테일 검색 결과(재료) 페이지
  {
    path: "/cocktail/search/byingredients/:ingredientId",
    name: "TheSearchCocktailByIngredientView",
    component: () =>
      import("@/views/search/TheSearchCocktailByIngredientView.vue"),
  },

  // 칵테일 검색 결과 페이지
  {
    path: "/cocktail/search/:searchValue",
    name: "TheSearchResultView",
    component: () => import("@/views/search/TheSearchResultView.vue"),
  },
  // 칵테일 필터 검색 결과 페이지
  {
    path: "/cocktail/search/filter",
    name: "TheSearchFilterResultView",
    component: () => import("@/views/search/TheSearchFilterResultView.vue"),
  },

  // 칵테일 전체 리스트 페이지
  {
    path: "/cocktail/whole",
    name: "TheWholeCocktailView",
    component: () => import("@/views/search/TheWholeCocktailView.vue"),
  },

  // 필터 카테고리
  {
    path: "/cocktail/search/filter/:category",
    name: "TheCocktailFilterCategoryView",
    component: () => import("@/views/search/TheCocktailFilterCategoryView.vue"),
  },

  // 칵테일 상세 페이지
  {
    path: "/cocktail/:cocktailId",
    name: "TheCocktailDescView",
    component: () => import("@/views/cocktailDesc/TheCocktailDescView.vue"),
  },

  // 칵테일 상세 페이지 북마크
  {
    path: "/cocktail/:cocktailId/bookmark",
    name: "TheCocktailDescBookmarkView",
    component: () =>
      import("@/views/cocktailDesc/TheCocktailDescBookmarkView.vue"),
  },

  // 커스텀 칵테일 제작 페이지
  {
    path: "/cocktail/:cocktailId/custom/form",
    name: "TheCustomCocktailFormView",
    component: () =>
      import("@/views/cocktailDesc/TheCustomCocktailFormView.vue"),
  },

  // 커스텀 칵테일 상세 페이지
  {
    path: "/cocktail/:cocktailId/custom/:feedId",
    name: "TheCustomCocktailDescView",
    component: () =>
      import("@/views/cocktailDesc/TheCustomCocktailDescView.vue"),
  },

  // 커스텀 칵테일 수정
  {
    path: "/cocktail/:cocktailId/custom/:feedId/update",
    name: "TheCustomCocktailUpdateFormView",
    component: () =>
      import("@/views/cocktailDesc/TheCustomCocktailUpdateFormView.vue"),
  },
  ,
  // 좋아요한 유저 리스트 페이지
  {
    path: "/feeds/:feedId/liked",
    name: "TheLikedUserListView",
    component: () => import("@/views/feeds/TheLikedUserListView.vue"),
  },

  //======================= feeds =======================
  // 뉴스피드 리스트
  {
    path: "/feeds",
    name: "TheNewsFeedView",
    component: () => import("@/views/feeds/TheNewsFeedView.vue"),
  },

  // 뉴스피드 상세 페이지 (커칵, 일반 )
  {
    path: "/feeds/:feedId",
    name: "TheCommunityDescView",
    component: () => import("@/views/feeds/TheCommunityDescView.vue"),
  },

  // 뉴스피드 게시글 form
  {
    path: "/feeds/form",
    name: "TheCommunityFormView",
    component: () => import("@/views/feeds/TheCommunityFormView.vue"),
  },

  // 뉴스피드 게시글 form 수정
  {
    path: "/feeds/:feedId/update",
    name: "TheCommunityUpdateFormView",
    component: () => import("@/views/feeds/TheCommunityUpdateFormView.vue"),
  },

  // 뉴스피드 상세 페이지 (커칵)
  {
    path: "/feeds/:feedId/custom",
    name: "TheSuperCustomCocktailDescView",
    component: () => import("@/views/feeds/TheSuperCustomCocktailDescView.vue"),
  },

  // 슈커칵 게시글 form
  {
    path: "/feeds/custom/form",
    name: "TheSuperCustomCocktailFormView",
    component: () => import("@/views/feeds/TheSuperCustomCocktailFormView.vue"),
  },

  // 슈커칵 게시글 수정 form
  {
    path: "/feeds/:feedId/custom/update",
    name: "TheSuperCustomCocktailUpdateFormView",
    component: () =>
      import("@/views/feeds/TheSuperCustomCocktailUpdateFormView.vue"),
  },

  // 알림
  {
    path: "/feeds/notice",
    name: "TheNoticeView",
    component: () => import("@/views/feeds/TheNoticeView.vue"),
  },

  //======================= cocktailAwards =======================
  // cocktailAwards home
  {
    path: "/feeds/awards",
    name: "TheCocktailAwardsView",
    component: () => import("@/views/cocktailAwards/TheCocktailAwardsView.vue"),
  },

  // cocktailAwards 게시글 form
  {
    path: "/feeds/awards/form",
    name: "TheCocktailAwardsFormView",
    component: () =>
      import("@/views/cocktailAwards/TheCocktailAwardsFormView.vue"),
  },

  // cocktailAwards 게시글 상세
  {
    path: "/feeds/:feedId/awards",
    name: "TheCocktailAwardsDescView",
    component: () =>
      import("@/views/cocktailAwards/TheCocktailAwardsDescView.vue"),
  },

  //======================= homes =======================
  // 신규 커스텀 칵테일 리스트
  {
    path: "/latestCustom",
    name: "TheAllLatestCustomCocktailView",
    component: () => import("@/views/homes/TheAllLatestCustomCocktailView.vue"),
  },

  // 주류주아 인기 칵테일 리스트
  {
    path: "/hot",
    name: "TheAllHotCocktailView",
    component: () => import("@/views/homes/TheAllHotCocktailView.vue"),
  },

  // 주류주아 주간 인기 커스텀칵테일 리스트
  {
    path: "/weeklyHot",
    name: "TheAllWeeklyHotCocktailView",
    component: () => import("@/views/homes/TheAllWeeklyHotCocktailView.vue"),
  },

  // 주류주아 주간 인기 커스텀칵테일 리스트
  {
    path: "/theme/:theme",
    name: "TheAllThemeCocktailView",
    component: () => import("@/views/homes/TheAllThemeCocktailView.vue"),
  },

  // 주류주아 북마크 기반 추천 칵테일 리스트
  {
    path: "/likeRecommended",
    name: "TheAllLikeRecommendedCocktailView",
    component: () =>
      import("@/views/homes/TheAllLikeRecommendedCocktailView.vue"),
  },

  //======================= chats =======================
  // ! 채팅
  {
    path: "/chats/list",
    name: "TheChatRoomListView",
    component: () => import("@/views/chats/TheChatRoomListView.vue"),
  },

  {
    path: "/chats/:userId",
    name: "TheChatRoomView",
    component: () => import("@/views/chats/TheChatRoomView.vue"),
  },

  //======================= 404 Page ======================
  {
    path: "/:pathMatch(.*)*",
    name: "TheNotFoundView",
    component: () => import("@/views/TheNotFoundView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,

  // 스크롤 무조건 맨 위로 이동
  scrollBehavior(to, from, savedPosition) {
    return { top: 0 };
  },
});

// 스토어 정의
const store = createStore;

// Navigation Guard 설정
router.beforeEach((to: any, from, next) => {
  // 로그인 여부 확인
  const isLoggedIn = computed(() => store.getters["personalInfo/isLoggedIn"]);

  // 로그인이 필요한 페이지
  const authPages = [
    "TheSignOutView",
    "TheChangeUserView",
    "TheChangePwView",
    "TheUserProfileView",
    "TheCocktailDescBookmarkView",
    "TheCustomCocktailFormView",
    "TheCustomCocktailDescView",
    "TheCustomCocktailUpdateFormView",
    "TheLikedUserListView",
    "TheNewsFeedView",
    "TheCommunityDescView",
    "TheCommunityFormView",
    "TheCommunityUpdateFormView",
    "TheSuperCustomCocktailDescView",
    "TheSuperCustomCocktailFormView",
    "TheSuperCustomCocktailUpdateFormView",
    "TheCocktailAwardsFormView",
    "TheCocktailAwardsDescView",
    "TheNoticeView",
    "TheChatRoomListView",
    "TheChatRoomView",
  ];

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
