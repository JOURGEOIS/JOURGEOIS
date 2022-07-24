<template>
	<the-personal-info-use-modal></the-personal-info-use-modal>
	<div class="content-container">
		<section>
			<title-block :contents="titleContents" />
			<agree-checker
				:contents="allCheckerContent"
				@clickCheckIcon="switchAllIsChecked"
			/>
			<div
				class="description"
				v-for="(description, idx) in descriptionList"
				:key="idx"
			>
				{{ description }}
			</div>
			<hr class="hr1" />
			<agree-checker
				:contents="agreeContents[0]"
				@clickCheckIcon="switchIsChecked"
			/>
			<agree-checker
				:contents="agreeContents[1]"
				@clickCheckIcon="switchIsChecked"
			/>
			<agree-checker
				:contents="agreeContents[2]"
				@clickCheckIcon="switchIsChecked"
				@clickModalIcon="togglePersonalInfoUseModal"
			/>
			<agree-checker
				:contents="agreeContents[3]"
				@clickCheckIcon="switchIsChecked"
			/>
		</section>
		<button-basic
			:button-style="[buttonColor, 'long', 'small']"
			:disabled="!isFulfillToNext"
			@click="nextSignupPage"
		>
			다음
		</button-basic>
	</div>
</template>

<script setup lang="ts">
import ThePersonalInfoUseModal from "@/components/accounts/ThePersonalInfoUseModal.vue";
import TitleBlock from "@/components/accounts/TitleBlock.vue";
import AgreeChecker from "@/components/accounts/AgreeChecker.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { ref, reactive, computed, watchEffect, toRefs } from "vue";
import { useStore } from "vuex";
const store = useStore();

const titleContents = reactive({
	mainList: ["안녕하세요!", "서비스 약관에 동의해주세요"],
});

const descriptionList = [
	"전체 동의는 필수 및 선택 정보에 대한 동의도 포함되어 있으며, 개별적으로 동의를 선택하실 수 있습니다.",
	"선택항목에 대한 동의를 거부하시는 경우에도 서비스는 이용이 가능합니다.",
];

const signUpAgreeChecked = computed(
	() => store.getters["signup/getSignUpAgreeChecked"]
);

const [isChecked0, isChecked1, isChecked2, isChecked3, isChecked4]: any =
	toRefs(signUpAgreeChecked.value);

// 전체 동의 조건 리스트
const allCheckerContent = reactive({
	order: 0,
	isChecked: isChecked0,
	checkContent: "모두 동의합니다.",
	isModalBtn: false,
});

// 동의 조건 리스트
const agreeContents = reactive([
	{
		order: 1,
		isChecked: isChecked1,
		checkContent: "만 14세 이상입니다.",
		isModalBtn: false,
		modalContent: "modal content 1",
	},
	{
		order: 2,
		isChecked: isChecked2,
		checkContent: "[필수] 주류주아 계정 약관",
		isModalBtn: true,
		modalContent: "modal content 2",
	},
	{
		order: 3,
		isChecked: isChecked3,
		checkContent: "[필수] 개인정보 수집 및 이용 동의",
		isModalBtn: true,
		modalContent: "modal content 3",
	},
	{
		order: 4,
		isChecked: isChecked4,
		checkContent: "[선택] 프로필정보 추가 수집 동의",
		isModalBtn: true,
		modalContent: "modal content 4",
	},
]);

// 전체 동의 체크 toggle 함수
const switchAllIsChecked = () => {
	store.dispatch("signup/toggleAllAgreeChecked");
};

// 조건별 체크표시 toggle
const switchIsChecked = (order: number) => {
	store.dispatch("signup/toggleAgreeChecked", order);
};

// 개인정보 이용
const togglePersonalInfoUseModal = () => {
	store.dispatch("signup/togglePersonalInfoUseModal");
};

// 다음 회원가입 페이지로 이동시키는 함수
const nextSignupPage = () => {
	store.dispatch("signup/nextSignupPage");
};

// 다음 페이지로 이동 조건 만족 여부 boolean
const isFulfillToNext = computed(() => {
	for (let i = 0; i < 3; i++) {
		if (!agreeContents[i].isChecked) return false;
	}
	return true;
});

// 조건 만족 여부에 따라 변하는 다음 버튼 색상
const buttonColor = computed(() => {
	return isFulfillToNext.value ? "primary" : "unchecked";
});
</script>

<style scoped lang="scss">
.content-container {
	@include flex(column);
	gap: 3rem;

	.description {
		margin-left: 30px;
		@include font(11px);
		color: $sub-color;
	}

	.hr1 {
		@include hr;
		margin-left: 30px;
		width: calc(100% - 30px);
	}
}
</style>
