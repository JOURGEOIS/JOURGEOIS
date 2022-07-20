import { createRouter, createWebHistory } from "vue-router";

const routes: any[] = [
  // 홈 화면
  {
    path: "/",
    name: "Home",
    component: () => import("@/views/Home.vue"),
  },

  // 로그인 화면
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/Login.vue"),
  },

  // 연습 화면
  {
    path: "/practice",
    name: "PracticeView",
    component: () => import("@/views/PracticeView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
