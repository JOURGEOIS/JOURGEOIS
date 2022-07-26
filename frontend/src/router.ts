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
    path: "/user/signup",
    name: "Signup",
    component: () => import("@/views/SignupView.vue"),
  },

  // 로그인 화면
  {
    path: "/user/login",
    name: "Login",
    component: () => import("@/views/TheLoginView.vue"),
  },

  // 로그아웃 화면
  {
    path: "/user/sign-out",
    name: "SignOut",
    component: () => import("@/views/SignOutView.vue"),
  },

  // 유저 정보 수정
  {
    path: "/user/my-info",
    name: "ChangeUserView",
    component: () => import("@/views/ChangeUserView.vue"),
  },

  // 비밀번호 찾기 화면
  {
    path: "/user/help/password",
    name: "ForgotPwView",
    component: () => import("@/views/ForgotPwView.vue"),
  },

  // 비밀번호 변경 화면
  {
    path: "/user/my-info/password",
    name: "ChangePwView",
    component: () => import("@/views/ChangePwView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
