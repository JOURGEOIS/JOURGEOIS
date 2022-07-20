<template>
	<div id="title-container">
		<!-- <div class="title-content" v-for="content in contents">
			{{ content }}
		</div>
		{{ prop1 }} -->
		<!-- {{ props }} -->
		{{ main }}
		{{ sub }}
	</div>
</template>

<script setup lang="ts">
import { ref } from "vue";
interface titleContent {
	main: string[];
	sub: string[];
}

const props = defineProps({
	contents: Object,
});

const main = props.contents.main;
const sub = props.contents.sub;
</script>

<style scoped lang="scss">
#title-container {
	color: $main-color;
	border: 1px solid $main-color;
	padding: 1em 0;
	@include flex(column);

	#title-content {
		@include flex-xy(flex-start);
		font-size: $fs-title;
		font-weight: $fw-bold;
		color: $main-color;
	}
}
</style>
