<template>
	<section class="progress-bar">
		<div class="progress-bar-inner" :style="{ width: progress + 'vw' }"></div>
	</section>
</template>

<script setup lang="ts">
import { ref } from "vue";
const props = defineProps({
	progress: Number,
});
</script>

<style scoped lang="scss">
.progress-bar {
	width: 100vw;
	height: 3px;
	background-color: $unchecked-color;
	.progress-bar-inner {
		width: 10vw;
		height: 3px;
		background-color: $primary-color;
		transition: width 0.5s ease-in-out;
	}
}
</style>
