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
			@click="nextSignupPage"
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

import { checkBadWord, checkAsterisk } from "../../modules/checkText";

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
	checkContent: "이름은 숫자 또는 영어만 입력해주세요.",
	isIconTypeDanger: false,
});

const birthLengthCheckerProps = reactive({
	isChecked: false,
	checkContent: "생년월일 8자리를 입력해주세요.",
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
	birthLengthCheckerProps.isChecked =
		birthInputValue.value.length === 8 ? true : false;
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
		birthLengthCheckerProps.isChecked &&
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
const nextSignupPage = () => {
	store.dispatch("signup/nextSignupPage");
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
