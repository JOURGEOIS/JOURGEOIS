<template>
	<div class="title-container">
		<div class="title-content" v-for="main in mainList">
			{{ main }}
		</div>
		<div class="subtitle-content" v-for="sub in subList">
			{{ sub }}
		</div>
	</div>
</template>

<script setup lang="ts">
import { ref } from "vue";
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
	padding: 1em 0;
	margin-top: 1em;
	background-color: $unchecked-color;
	color: $main-color;

	@include flex(column);

	.title-content {
		@include flex-xy(flex-start);
		@include font($fs-main, $fw-medium);
		color: $main-color;
	}

	.subtitle-content {
		@include flex-xy(flex-start);
		padding: $fs-xs 0;
		font-size: $fs-sm;
		font-weight: $fw-light;
		color: $main-color;
	}
}
</style>
