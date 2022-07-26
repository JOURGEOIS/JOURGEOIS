<template>
	<div
		class="checker-container"
		:class="{ blocked: isChecked && isIconTypeDanger }"
	>
		<span
			class="checker-icon material-icons"
			:class="{
				unchecked: !isChecked,
				checked: isChecked && !isIconTypeDanger,
			}"
		>
			{{ iconType }}
		</span>
		<span class="checker-content">{{ checkContent }}</span>
	</div>
</template>

<script setup lang="ts">
import { toRefs } from "vue";
const props = defineProps<{
	props: {
		isChecked: boolean;
		checkContent: string;
		isIconTypeDanger: boolean;
	};
}>();
let { isChecked, checkContent, isIconTypeDanger } = toRefs(props.props);

const iconType = isIconTypeDanger.value ? "block" : "check_circle_outline";
</script>

<style scoped lang="scss">
.checked {
	color: $primary-color;
}

.unchecked {
	color: $unchecked-color;
}

.blocked {
	color: $danger-color;
}

.checker-container {
	@include flex-xy(flex-start, center);
	gap: 0.3em;
	// align-items: center;

	// 체크 아이콘
	.checker-icon {
		font-size: 12px;
		user-select: none;
	}

	// 체크 항목 내용
	.checker-content {
		@include font(12px, $fw-regular);
	}
}
</style>
