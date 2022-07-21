<template>hello</template>

<script setup lang="ts">
import ConditionChecker from "@/components/accounts/ConditionChecker.vue";
import { ref, reactive } from "vue";
import TitleBlock from "./TitleBlock.vue";
import InputBasic from "../basics/InputBasic.vue";
import { useStore } from "vuex";
const store = useStore();
const nextSignupPage = () => {
	store.dispatch("signup/nextSignupPage");
};

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
