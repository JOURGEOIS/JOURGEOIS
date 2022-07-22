<template>
	<div class="content-container">
		<section>
			<title-block :contents="titleContents" />
			<agree-checker
				:contents="agreeContents[0]"
				@clickCheckIcon="switchIsChecked"
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
				:contents="agreeContents[1]"
				@clickCheckIcon="switchIsChecked"
			/>
			<agree-checker
				:contents="agreeContents[2]"
				@clickCheckIcon="switchIsChecked"
			/>
			<agree-checker
				:contents="agreeContents[3]"
				@clickCheckIcon="switchIsChecked"
			/>
			<agree-checker
				:contents="agreeContents[4]"
				@clickCheckIcon="switchIsChecked"
			/>
		</section>
		<button-basic
			:button-style="[buttonColor, 'long', 'small']"
			:disabled="!isFullfillToNext"
			@click="nextSignupPage"
		>
			다음
		</button-basic>
	</div>
</template>

<script setup lang="ts">
import TitleBlock from "@/components/accounts/TitleBlock.vue";
import AgreeChecker from "@/components/accounts/AgreeChecker.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { reactive, watch, computed } from "vue";
import { useStore } from "vuex";
const store = useStore();

const titleContents = reactive({
	mainList: ["안녕하세요!", "서비스 약관에 동의해주세요"],
});

const descriptionList = [
	"전체 동의는 필수 및 선택 정보에 대한 동의도 포함되어 있으며, 개별적으로 동의를 선택하실 수 있습니다.",
	"선택항목에 대한 동의를 거부하시는 경우에도 서비스는 이용이 가능합니다.",
];

const agreeContents = reactive([
	reactive({
		order: 0,
		isChecked: false,
		checkContent: "모두 동의합니다.",
		isModalBtn: false,
	}),
	reactive({
		order: 1,
		isChecked: false,
		checkContent: "만 14세 이상입니다.",
		isModalBtn: false,
		modalContent: "modal content 1",
	}),
	reactive({
		order: 2,
		isChecked: false,
		checkContent: "[필수] 주류주아 계정 약관",
		isModalBtn: true,
		modalContent: "modal content 2",
	}),
	reactive({
		order: 3,
		isChecked: false,
		checkContent: "[필수] 개인정보 수집 및 이용 동의",
		isModalBtn: true,
		modalContent: "modal content 3",
	}),
	reactive({
		order: 4,
		isChecked: false,
		checkContent: "[선택] 프로필정보 추가 수집 동의",
		isModalBtn: true,
		modalContent: "modal content 4",
	}),
]);

const switchIsChecked = (order: number) => {
	agreeContents[order].isChecked = !agreeContents[order].isChecked;
};

watch(agreeContents, () => {});

const nextSignupPage = () => {
	store.dispatch("signup/nextSignupPage");
};

const isFullfillToNext = computed(() => {
	return true;
});

const buttonColor = isFullfillToNext ? "primary" : "unchecked";
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
