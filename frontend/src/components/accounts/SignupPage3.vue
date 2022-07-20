<template>
	<title-block :contents="titleContent"></title-block>
	<input-basic></input-basic>

	<condition-checker :props="props11" />
</template>

<script setup lang="ts">
import ConditionChecker from "@/components/accounts/ConditionChecker.vue";
import { useStore } from "vuex";
import { ref, reactive } from "vue";
import TitleBlock from "./TitleBlock.vue";
import InputBasic from "../basics/InputBasic.vue";

const titleContent = reactive({
	mainList: ["비밀번호를 입력해주세요"],
	subList: [],
});

const props11 = reactive({
	checkContent: "그냥 대충 내용",
	isChecked: true,
	isIconTypeDanger: true,
});
</script>

<style scoped lang="scss"></style>
