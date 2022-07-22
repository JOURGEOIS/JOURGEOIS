import { createRouter, createWebHistory } from "vue-router";

const routes: any[] = [
  // 홈 화면
  {
    path: "/",
    name: "Home",
    component: () => import("@/views/HomeView.vue"),
  },

  // 회원가입 화면
  {
    path: "/signup",
    name: "Signup",
    component: () => import("@/views/SignupView.vue"),
  },

  // 로그인 화면
  {
    path: "/user/login",
    name: "Login",
    component: () => import("@/views/TheLoginView.vue"),
  },

  // 연습 화면
  {
    path: "/practice",
    name: "PracticeView",
    component: () => import("@/views/PracticeView.vue"),
  },

  // 비밀번호 찾기 화면
  {
    path: "/user/help/password",
    name: "ForgotPwView",
    component: () => import("@/views/ForgotPwView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
