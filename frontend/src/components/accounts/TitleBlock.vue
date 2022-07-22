<template>
	<div class="title-container">
		<div
			class="main-title"
			v-if="mainList !== undefined && mainList.length !== 0"
		>
			<div class="title-content" v-for="(main, idx) in mainList" :key="idx">
				{{ main }}
			</div>
		</div>
		<div class="sub-title" v-if="subList !== undefined && subList.length !== 0">
			<div class="subtitle-content" v-for="(sub, idx) in subList" :key="idx">
				{{ sub }}
			</div>
		</div>
	</div>
</template>

<script setup lang="ts">
import { computed } from "vue";
interface titleContent {
	mainList: string[];
	subList: string[];
}

const props = defineProps({
	contents: Object,
});

const { mainList, subList } = props.contents;
</script>

<style scoped lang="scss">
.title-container {
	padding-bottom: 2rem;
	color: $main-color;
	@include flex(column);

	.sub-title {
		padding: $fs-xs 0;
	}
	.title-content {
		@include flex-xy(flex-start);
		@include font($fs-main, $fw-medium);
		color: $main-color;
	}

	.subtitle-content {
		@include flex-xy(flex-start);
		font-size: $fs-sm;
		font-weight: $fw-light;
		color: $main-color;
	}
}
</style>
