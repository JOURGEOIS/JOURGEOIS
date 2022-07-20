<template>
	<div id="signup-container">
		<signup-page1 v-if="currentPage === 1" />
		<signup-page3 v-if="currentPage === 3" />
		<hr />
		현재 페이지 : {{ currentPage }} 진행률: {{ progress }}
		<button @click="prevSignupPage">이전</button>
		<button @click="nextSignupPage">다음</button>
	</div>
</template>

<script setup lang="ts">
import SignupPage1 from "@/components/accounts/SignupPage1.vue";
import SignupPage3 from "@/components/accounts/SignupPage3.vue";
import { computed } from "vue";

import { useStore } from "vuex";
const store = useStore();

// 현재 페이지 번호 및 페이지 이동
let progress = computed(() => store.getters["signup/getProgress"]);
let currentPage = computed(() => store.getters["signup/getCurrentPage"]);
const nextSignupPage = () => {
	store.dispatch("signup/nextSignupPage");
};

const prevSignupPage = () => {
	store.dispatch("signup/prevSignupPage");
};
</script>

<style scoped lang="scss">
#signup-container {
	@include flex(column);
	@include flex-xy(flex-start, flex-start);
	width: 390px;
	height: 844px;
	background-color: rgb(250, 250, 250);
}
</style>
