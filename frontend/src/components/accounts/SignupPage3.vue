<template>
	<condition-checker :props="props11" />
</template>

<script setup lang="ts">
import ConditionChecker from "@/components/accounts/ConditionChecker.vue";
import { useStore } from "vuex";
import { ref, reactive } from "vue";

const props11 = reactive({
	checkContent: "그냥 대충 내용",
	isChecked: true,
	isIconTypeDanger: true,
});
</script>

<style scoped lang="scss"></style>
