<template>
	<section class="checker-line">
		<span class="checker-container">
			<span
				class="checker-icon material-icons"
				:class="{ checked: isChecked, unchecked: !isChecked }"
				@click="clickCheckIconInner"
			>
				check_circle_outline
			</span>
			<span class="checker-content">{{ checkContent }}</span>
		</span>
		<span
			:style="[
				isModalBtn ? { visibility: 'visible' } : { visibility: 'hidden' },
			]"
			class="checker-link"
			@click="showModal"
		>
			<span class="material-icons checker-arrow-icon"> arrow_forward_ios </span>
		</span>
	</section>
</template>

<script setup lang="ts">
import { toRefs } from "vue";

const props = defineProps<{
	contents: object;
}>();

let { order, isChecked, checkContent, isModalBtn, modalContent } =
	props.contents;

const emit = defineEmits<{ (e: "clickCheckIcon", order: number): void }>();
// 체크 표시 switch
const clickCheckIconInner = () => {
	emit("clickCheckIcon", order);
};

const showModal = () => {
	alert(modalContent);
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
		padding: 0.2rem 0;
		// align-items: center;

		// 체크 아이콘
		.checker-icon {
			user-select: none;
		}

		// 체크 항목 내용
		.checker-content {
			@include font(13px, $fw-medium);
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
