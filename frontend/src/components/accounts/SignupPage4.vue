<template>
	<div class="content-container">
		<div class="content-section">
			<!-- 이름 입력 -->
			<section>
				<title-block
					:contents="nameTitleContents"
					:style="{ 'padding-bottom': '1rem' }"
				/>
				<div>
					<input-basic
						:data="nameInputData"
						:input-style="nameInputStyle"
						v-model="nameInputValue"
					></input-basic>
					<section class="condition-checker-section">
						<condition-checker :props="nameEnKrCheckerProps" />
					</section>
				</div>
			</section>

			<!-- 생년월일 입력 -->
			<section>
				<title-block
					:contents="birthTitleContents"
					:style="{ 'padding-bottom': '1rem' }"
				/>
				<div>
					<input-basic
						:data="birthInputData"
						:input-style="birthInputStyle"
						v-model="birthInputValue"
					></input-basic>
					<section class="condition-checker-section">
						<condition-checker :props="birthLengthCheckerProps" />
						<condition-checker :props="birthFormatCheckerProps" />
					</section>
				</div>
			</section>

			<!-- 닉네임 입력 -->
			<section>
				<title-block
					:contents="nicknameTitleContents"
					:style="{ 'padding-bottom': '1rem' }"
				/>
				<div>
					<input-basic
						:data="nicknameInputData"
						:input-style="nicknameInputStyle"
						v-model="nicknameInputValue"
					></input-basic>
					<section class="condition-checker-section">
						<condition-checker :props="nicknameLengthCheckerProps" />
						<condition-checker :props="duplicatedNicknameCheckerProps" />
						<condition-checker :props="badWordsCheckerProps" />
						<condition-checker :props="numEnKrCheckerProps" />
					</section>
				</div>
			</section>
		</div>

		<!-- 다음 -->
		<button-basic
			:button-style="[nextButtonColor, 'long', 'small']"
			class="next-button"
			:disabled="!isFullfillToNext"
			@click="completeSignupPage"
		>
			완료
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
const store = useStore();

import {
	checkBadWord,
	checkAsterisk,
	checkENKR,
	checkBirthFormat,
} from "../../modules/checkText";

// 제목 컴포넌트
const nameTitleContents = reactive({
	mainList: ["이름를 입력해주세요"],
	subList: [],
});

const birthTitleContents = reactive({
	mainList: ["생년월일을 입력해주세요"],
	subList: [],
});

const nicknameTitleContents = reactive({
	mainList: ["닉네임을 입력해주세요"],
	subList: [],
});

// input-value
const nameInputValue = ref("");
const birthInputValue = ref("");
const nicknameInputValue = ref("");

// input 데이터
const nameInputData: object = reactive({
	button: true,
	id: "signup-name-input",
	// label: "이름",
	placeholder: "이름을 입력하세요.",
	type: "text",
});

const birthInputData: object = reactive({
	button: true,
	id: "signup-birth-input",
	// label: "생년월일",
	placeholder: "YYYYMMDD",
	type: "text",
	maxlength: 10,
});

const nicknameInputData: object = reactive({
	button: true,
	id: "signup-nickname-input",
	// label: "닉네임",
	placeholder: "닉네임 (2 ~ 12글자)",
	type: "text",
});

const nameInputStyle = ref("normal");
const birthInputStyle = ref("normal");
const nicknameInputStyle = ref("normal");

// 조건 체크에 대한 props들
const nameEnKrCheckerProps = reactive({
	isChecked: false,
	checkContent: "이름은 한글 또는 영어만 입력해주세요.",
	isIconTypeDanger: false,
});

const birthLengthCheckerProps = reactive({
	isChecked: false,
	checkContent: "생년월일 8자리를 입력해주세요.",
	isIconTypeDanger: false,
});

const birthFormatCheckerProps = reactive({
	isChecked: false,
	checkContent: "입력형식이 일치합니다.",
	isIconTypeDanger: false,
});

const nicknameLengthCheckerProps = reactive({
	isChecked: false,
	checkContent: "최소 2자 이상, 최대 12자 이내로 입력해주세요.",
	isIconTypeDanger: false,
});

const duplicatedNicknameCheckerProps = reactive({
	isChecked: false,
	checkContent: "중복된 닉네임입니다.",
	isIconTypeDanger: true,
});

const badWordsCheckerProps = reactive({
	isChecked: false,
	checkContent: "부적절한 단어가 포함되었습니다.",
	isIconTypeDanger: true,
});

const numEnKrCheckerProps = reactive({
	isChecked: false,
	checkContent: "영어, 숫자, 한글이 아닌 문자가 포함되었습니다.",
	isIconTypeDanger: true,
});

// input값 변경에 따른 함수 실행
watchEffect(() => {
	nameEnKrCheckerProps.isChecked =
		!!nameInputValue.value && checkENKR(nameInputValue.value) ? true : false;
});

watchEffect(() => {
	const t = ref(birthInputValue.value);
	const birthLength = t.value.length;
	const lastChar = t.value[birthLength - 1];

	// 입력 완료 시
	if (birthLength === 10) {
		birthLengthCheckerProps.isChecked =
			birthInputValue.value.length === 10 ? true : false;

		birthFormatCheckerProps.isChecked = checkBirthFormat(t.value);
	} else if (birthLength === 5 || birthLength === 8) {
		// 지울 때
		if (lastChar === "/") {
			birthInputValue.value = t.value.substring(0, birthLength - 1);
		} else if (isNaN(Number(lastChar))) {
			birthInputValue.value = t.value.substring(0, birthLength - 2);
		} else {
			// 입력할 때
			birthInputValue.value =
				birthInputValue.value.substring(0, birthLength - 1) + "/" + lastChar;
		}
	}
	// else if (birthLength == 7 || birthLength == 12) {
	// 	birthInputValue.value = birthInputValue.value.substring(0, birthLength - 3);
	// }
});

watchEffect(() => {
	const nicknameLength = nicknameInputValue.value.length;
	nicknameLengthCheckerProps.isChecked =
		2 <= nicknameLength && nicknameLength <= 12 ? true : false;
});

// watchEffect(() => {
// duplicatedNicknameCheckerProps.isChecked = isDuplicatedNickname(nicknameInputValue.value)
// 	? true
// 	: false;
// });

watchEffect(() => {
	badWordsCheckerProps.isChecked = checkBadWord(nicknameInputValue.value)
		? true
		: false;
});

watchEffect(() => {
	numEnKrCheckerProps.isChecked = checkAsterisk(nicknameInputValue.value)
		? true
		: false;
});

const isFullfillToNext = computed(() => {
	return (
		nameEnKrCheckerProps.isChecked &&
		birthLengthCheckerProps.isChecked &&
		birthFormatCheckerProps.isChecked &&
		nicknameLengthCheckerProps.isChecked &&
		!duplicatedNicknameCheckerProps.isChecked &&
		!badWordsCheckerProps.isChecked &&
		!numEnKrCheckerProps.isChecked
	);
});

// 완료 버튼 색 설정
const nextButtonColor = computed(() => {
	return isFullfillToNext.value ? "primary" : "disabled";
});

// 다음 페이지로 이동
const completeSignupPage = () => {
	store.dispatch("signup/nextSignupPage");
	setTimeout(() => {
		alert("됐다!");
	}, 500);
};
</script>

<style scoped lang="scss">
.content-container {
	@include flex(column);
	gap: 3rem;

	.content-section {
		@include flex(column);
		gap: 4rem;
	}
	.input-section {
		@include flex(column);
		gap: 1rem;
	}
}

.condition-checker-section {
	margin-top: 5px;
}
</style>