<template>
	<div class="signup-view">
		<progress-bar :progress="progress"></progress-bar>
		<header-basic :prev="true" :success="false"> 회원가입 </header-basic>
		<section class="signup-section">
			<signup-page1 v-if="currentPage === 0" />
			<signup-page2 v-if="currentPage === 1" />
			<signup-page3 v-if="currentPage === 2" />
			<signup-page4 v-if="currentPage === 3" />
			<hr />
			현재 페이지 : {{ currentPage }}
			<button @click="prevSignupPage">이전</button>
			<button @click="nextSignupPage">다음</button>
		</section>
	</div>
</template>

<script setup lang="ts">
import SignupPage1 from "@/components/accounts/SignupPage1.vue";
import SignupPage2 from "@/components/accounts/SignupPage2.vue";
import SignupPage3 from "@/components/accounts/SignupPage3.vue";
import SignupPage4 from "@/components/accounts/SignupPage4.vue";
import { computed } from "vue";
import { useStore } from "vuex";
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import ProgressBar from "@/components/basics/ProgressBar.vue";
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
.signup-view {
	@include flex(column);
	justify-content: flex-start;
	align-items: center;
	@include accountLayOut;

	.signup-section {
		width: 100%;
		margin-top: 2rem;

		@media #{$tablet} {
			width: 80%;
		}

		@media #{$pc} {
			width: 70%;
		}
	}
}
</style>
