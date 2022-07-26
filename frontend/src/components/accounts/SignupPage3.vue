<template>
	<div class="content-container">
		<section>
			<title-block :contents="titleContents" />
			<!-- 비밀번호 입력 -->
			<section class="input-section">
				<div>
					<input-basic
						:data="pwInputData"
						:input-style="pwInputStyle"
						v-model="pwInputValue"
					></input-basic>
					<section class="condition-checker-section">
						<condition-checker :props="pwLengthCheckerProps" />
						<condition-checker :props="pwTripleCheckerProps" />
					</section>
				</div>
				<!-- 비밀번호 확인 입력 -->
				<div>
					<input-basic
						:data="pwInputCheckData"
						:input-style="pwInputStyle"
						v-model="pwInputCheckValue"
					></input-basic>
					<section class="condition-checker-section">
						<condition-checker :props="pwEqualCheckerProps" />
					</section>
				</div>
			</section>
		</section>
		<!-- 다음 -->
		<button-basic
			:button-style="[nextButtonColor, 'long', 'small']"
			class="next-button"
			:disabled="!isFulfillToNext"
			@click="clickNextPage"
		>
			다음
		</button-basic>
	</div>
</template>

<script setup lang="ts">
import TitleBlock from "@/components/accounts/TitleBlock.vue";
import ConditionChecker from "@/components/accounts/ConditionChecker.vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { reactive, ref, computed, watchEffect } from "vue";
import { useStore } from "vuex";
import { react } from "@babel/types";
import {
	checkTripleCombination,
	checkPasswordLength,
} from "../../modules/checkText";

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

// 조건 체크에 대한 props들
const pwLengthCheckerProps = reactive({
	isChecked: false,
	checkContent: "최소 8자 이상, 최대 20자 이하로 입력해주세요.",
	isIconTypeDanger: false,
});

const pwTripleCheckerProps = reactive({
	isChecked: false,
	checkContent:
		"영어 대문자, 소문자, 숫자, 특수문자 중 3종류 이상 입력해주세요.",
	isIconTypeDanger: false,
});

const pwEqualCheckerProps = reactive({
	isChecked: false,
	checkContent: "비밀번호와 비밀번호 확인이 일치합니다.",
	isIconTypeDanger: false,
});

// input값 변경에 따른 함수 실행
watchEffect(() => {
	pwLengthCheckerProps.isChecked = checkPasswordLength(pwInputValue.value)
		? true
		: false;
});

watchEffect(() => {
	pwTripleCheckerProps.isChecked = checkTripleCombination(pwInputValue.value)
		? true
		: false;
});

watchEffect(() => {
	pwEqualCheckerProps.isChecked =
		pwInputValue.value && pwInputValue.value === pwInputCheckValue.value
			? true
			: false;
});

const isFulfillToNext = computed(() => {
	return (
		pwLengthCheckerProps.isChecked &&
		pwTripleCheckerProps.isChecked &&
		pwEqualCheckerProps.isChecked
	);
});

// 완료 버튼 색 설정
const nextButtonColor = computed(() => {
	return isFulfillToNext.value ? "primary" : "disabled";
});

// 다음 버튼 눌렀을 때
const clickNextPage = (password: string) => {
	// 이동 전에 pwInputValue.value vuex에 저장
	store.dispatch("signup/saveSignUpPw", pwInputValue.value);
	// 다음 페이지로 이동
	const nextSignupPage = () => {
		store.dispatch("signup/nextSignupPage");
	};
	nextSignupPage();
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

.condition-checker-section {
	margin-top: 5px;
}
</style>
