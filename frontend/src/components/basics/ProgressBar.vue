<template>
	<section id="progress-bar">{{ progress }}</section>
</template>

<script setup lang="ts">
const props = defineProps({
	progress: String,
});
</script>

<style scoped lang="scss">
#progress-bar {
	width: 100vw;
	height: $fs-xs;
	border: 1px solid black;
	@include ignoreAppMargin;
}
</style>
