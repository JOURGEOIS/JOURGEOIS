<template>
	<div class="tmp-container">
		<router-link to="/">Home</router-link> <br />
		<router-link to="/signup">회원가입</router-link> <br />
		<router-link to="/login">로그인</router-link>
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
.tmp-container {
	@include flex(column);
	align-items: center;
	padding: 30px;
	@include font(20px, $fw-bold);
}
</style>
