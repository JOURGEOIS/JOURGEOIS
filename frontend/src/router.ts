import { createRouter, createWebHistory } from "vue-router";
import { useStore } from "vuex";
import { computed } from "vue";
const store = useStore();

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
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Navigation Guard 설정
// router.beforeEach((to: any, from, next) => {
//   // '/' => '/articles/1'
//   // 이전에 있던 '/' 가 from, '/articles/1'이 to(목적지)

//   // 로그인 여부 확인
//   const isLoggedIn = computed(() => store.getters["personalInfo/ isLoggedIn"]);

//   // Auth가 필요한 route
//   const authPages = [
//     "SignOutView",
//     "ChangeUserView",
//     "ForgotPwView",
//     "ChangePwView",
//   ];

//   // 로그인이 되어있지 않을 때만 가능한 route
//   const notAuthPages = ["SignupView", "LoginView"];

//   // 1. 현재 이동하고자 하는 페이지가 로그인이 필요한지 확인
//   const isAuthRequired = authPages.includes(to.name);
//   const isNotAuthRequired = notAuthPages.includes(to.name);

//   // 2. 로그인이 필요한 페이지인데 로그인이 되어있지 않다면 로그인 페이지(/login)로 이동
//   if (isAuthRequired && !isLoggedIn) {
//     next({ name: "LoginView" });
//   } else {
//     // 3. 로그인이 되어 있다면 원래 이동할 곳으로 이동
//     next();
//   }

//   // 4. 로그인이 되어있는데 /login, /signup 페이지로 이동한다면 메인 페이지(/)로 이동
//   if (isNotAuthRequired && isLoggedIn) {
//     next({ name: "HomeView" });
//   } else {
//     next();
//   }
// });

export default router;
