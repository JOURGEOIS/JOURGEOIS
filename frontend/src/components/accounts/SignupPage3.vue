<template>
	<div class="content-container">
		<section>
			<title-block :contents="titleContents" />
			<!-- 비밀번호 입력 -->
			<section class="input-section">
				<input-basic
					:data="pwInputData"
					:input-style="pwInputStyle"
					v-model="pwInputValue"
				></input-basic>
				<!-- 비밀번호 확인 입력 -->
				<input-basic
					:data="pwInputCheckData"
					:input-style="pwInputStyle"
					v-model="pwInputCheckValue"
				></input-basic>
			</section>
		</section>
		<!-- 다음 -->
		<button-basic
			:button-style="[nextButtonColor, 'long', 'small']"
			class="next-button"
			:disabled="!isFullfillToNext"
			@click="nextSignupPage"
		>
			다음
		</button-basic>
	</div>
</template>

<script setup lang="ts">
import TitleBlock from "@/components/accounts/TitleBlock.vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { reactive, ref, computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

// 제목 컴포넌트
const titleContents = reactive({
	mainList: ["비밀번호를 입력해주세요"],
	subList: [],
});

// input-value
const pwInputValue = ref("");
const pwInputCheckValue = ref("");

// 비밀번호 input 데이터
const pwInputData: object = reactive({
	button: true,
	id: "signup-pw-input",
	// label: "비밀번호",
	placeholder: "비밀번호를 입력하세요.",
	type: "password",
});

// 비밀번호 확인 input 데이터
const pwInputCheckData: object = reactive({
	button: true,
	id: "signup-pw-check-input",
	// label: "비밀번호 확인",
	placeholder: "비밀번호를 한 번 더 입력하세요.",
	type: "password",
});

const pwInputStyle = ref("normal");

const isFullfillToNext = computed(() => {
	return true;
});

// 완료 버튼 색 설정
const nextButtonColor = computed(() => {
	return isFullfillToNext ? "primary" : "disabled";
});

// 다음 페이지로 이동
const nextSignupPage = () => {
	store.dispatch("signup/nextSignupPage");
};
</script>

<style scoped lang="scss">
.content-container {
	@include flex(column);
	gap: 3rem;
	.input-section {
		@include flex(column);
		gap: 1rem;
	}
}
</style>
