<template>
	<section class="checker-line">
		<span class="checker-container">
			<span
				class="checker-icon material-icons"
				:class="{ checked: isChecked, unchecked: !isChecked }"
				@click="switchIsChecked(order)"
			>
				check_circle_outline
			</span>
			<span class="checker-content">{{ checkContent }}</span>
		</span>
		<span class="checker-link" @click="showModal">
			<span class="material-icons checker-arrow-icon"> arrow_forward_ios </span>
		</span>
	</section>
</template>

<script setup lang="ts">
import { useStore } from "vuex";
import { computed } from "vue";
const store = useStore();
const props = defineProps<{
	order: String;
}>();

const order = Number(props.order);

// 체크 여부 확인
const page1isCheckedList = computed(
	() => store.getters["signup/getPage1isChecked"]
);
let isChecked = computed(() => page1isCheckedList.value[order]);

// 한 줄 내용 가져오기
const page1checkContentList = computed(
	() => store.getters["signup/getPage1checkContent"]
);
const checkContent = page1checkContentList.value[order];

// 세부내용 모달
const page1checkDetailContentList = computed(
	() => store.getters["signup/getPage1checkDetailContent"]
);
const checkDetailContent = page1checkDetailContentList.value[order];
const showModal = () => {
	alert(checkDetailContent);
};

// 체크 표시 switch
const switchIsChecked = (order: number) => {
	store.dispatch("signup/switchIsChecked", order);
};
</script>

<!-- style -->
<style scoped lang="scss">
.checked {
	color: $primary-color;
}

.unchecked {
	color: $unchecked-color;
}

.checker-line {
	@include flex;
	align-self: stretch;
	justify-content: space-between;

	.checker-container {
		@include flex;
		gap: 0.3em;
		// align-items: center;

		// 체크 아이콘
		.checker-icon {
			user-select: none;
		}

		// 체크 항목 내용
		.checker-content {
			@include font($fs-sm, $fw-medium);
		}
	}
	.checker-link {
		@include flex-center;
		user-select: none;
		.checker-arrow-icon {
			color: $unchecked-color;
			font-size: $fs-main;
		}
	}
}
</style>
