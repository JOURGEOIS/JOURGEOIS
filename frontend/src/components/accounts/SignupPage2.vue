<template>
	<div class="content-container">
		<section>
			<title-block :contents="titleContents" />
			<form @submit.prevent="submitCheckEmailForm">
				<input-basic
					:data="emailInputData"
					:input-style="emailInputStyle"
					v-model="emailInputValue"
				></input-basic>
				<title-block :contents="infoContents" v-if="showButtonContainer" />
				<!-- 인증메일 전송 버튼 -->
				<div>
					<button-basic
						:button-style="[submitButtonColor, 'long', 'small']"
						class="submit-check-email-button"
						v-if="!showButtonContainer"
						:disabled="!emailInputValue"
						@click="postCheckEmail"
					>
						인증메일 전송
					</button-basic>
				</div>
			</form>
		</section>
		<section class="button-container" v-if="showButtonContainer">
			<!--인증메일 재전송 버튼 -->
			<button-basic
				:button-style="['primary-outline', '60%', 'small']"
				@click="postCheckEmail"
			>
				인증메일 재전송
			</button-basic>

			<!-- 다음 버튼: 다음 페이지로 이동 -->
			<button-basic
				:button-style="[nextButtonColor, '38%', 'small']"
				:disabled="!isFullfillToNext"
				@click="nextSignupPage"
			>
				다음
			</button-basic>
		</section>
	</div>
</template>

<script setup lang="ts">
import TitleBlock from "@/components/accounts/TitleBlock.vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { reactive, ref, computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

import { checkEmailCondition } from "../../modules/checkText";

// 제목 컴포넌트
const titleContents = reactive({
	mainList: ["이메일을 입력해주세요"],
	subList: ["입력하신 이메일로 확인 메일을 보내드릴게요."],
});

const infoContents = reactive({
	mainList: [],
	subList: [
		"인증은 3분 이내에 진행되어야 해요.",
		"3분을 초과하셨다면 인증메일 재전송을 눌러주세요.",
	],
});

// input-value
const emailInputValue = ref("");

// 이메일 input 데이터
const emailInputData: object = reactive({
	button: true,
	id: "signup-email-input",
	placeholder: "example@naver.com",
	type: "text",
});

// email input스타일 normal로 정의
const emailInputStyle = ref("normal");

// [인증메일 재전송] / [완료] 있는 buttonContainer를 보일지 여부
let showButtonContainer = ref(false);

// 제출
const submitCheckEmailForm = () => {
	showButtonContainer = ref(true);
};

// 이메일이 1자라도 입력되면 true 반환
const isFullfillToSubmit = computed(() => {
	// 입력이 있다면
	return checkEmailCondition(emailInputValue.value);
});

const submitButtonColor = computed(() => {
	return isFullfillToSubmit.value ? "primary" : "unchecked";
});

// 인증메일 전송 로직
const postCheckEmail = () => {
	alert("인증메일 전송이 완료되었습니다.");
	showButtonContainer.value = true;
};

// 인증메일 전송 이후

// 이메일 인증이 완료되어 다음 페이지로 넘어가도 될 때 true 반환
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

	.submit-check-email-button {
		margin-top: 3rem;
	}

	.button-container {
		display: inline-flex;
		justify-content: space-between;
	}
}
</style>
