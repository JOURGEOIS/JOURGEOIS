<template>
	<div>
		<div>data: {{ getters }}</div>
		<button @click="actions"></button>
	</div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";
const store = useStore();
const state = computed(() => store.state.moduleA.counter);
const getters = computed(() => store.getters["moduleA/getCounter"]);
const mutations = () => store.commit("moduleA/SET_COUNTER");
const actions = () => store.dispatch("moduleA/plusOneCounter");
</script>

<style scoped lang="scss">
div {
	color: $primary-color;
}
</style>
