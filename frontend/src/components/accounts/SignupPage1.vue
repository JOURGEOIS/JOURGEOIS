<template>
	<!-- <title-block :contents="contents1" /> -->
	<agree-checker order="0" />
	<agree-checker order="1" />
	<agree-checker order="2" />
	<agree-checker order="3" />
</template>

<script setup lang="ts">
import AgreeChecker from "@/components/accounts/AgreeChecker.vue";
import { useStore } from "vuex";
</script>

<style scoped lang="scss"></style>
