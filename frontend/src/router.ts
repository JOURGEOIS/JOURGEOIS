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
];

const router = createRouter({
	history: createWebHistory(),
	routes,
});

export default router;
