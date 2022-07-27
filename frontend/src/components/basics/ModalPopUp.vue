<!-- 
  :modal-color="색상"
  modal-color 프롭스에 따라 색상이 변합니다.
  modal-color가 primary이면 프라이머리컬러 pop-up이 생성됩니다.
  modal-color가 white이면 하얀색 pop-up이 생성됩니다.
  modal-color가 danger이면 빨간색 pop-up이 생성됩니다.
-->

<template>
	<teleport to="body">
		<div class="modal-container">
			<div class="modal-popup" :class="modalStyle">
				<slot></slot>
			</div>
		</div>
	</teleport>
</template>

<script setup lang="ts">
import { selectModalColor } from "../../functions/modalEvent";

const props = defineProps<{
	modalColor: string;
}>();

const { modalStyle }: any = selectModalColor(props.modalColor);
</script>

<style scoped lang="scss">
@keyframes fade-in {
	from {
		opacity: 0;
		transform: translate3d(0, 3%, 0);
	}
	to {
		opacity: 1;
		transform: translateZ(0);
	}
}

.modal-container {
	@include flex-xy(center, flex-start);
	width: 100vw;
	height: 100vh;
	position: fixed;
	top: 0;
	animation: fade-in 1s;
}

.modal-popup {
	@include popup;
	@include font($fs-md, $fw-regular);
	margin-top: 10vh;
	width: calc(100vw - 32px);
	max-height: 60vh;

	@media #{$tablet} {
		width: 70vw;
		font-size: $fs-main;
	}

	@media #{$pc} {
		width: 70vw;
		max-width: 1000px;
	}
}

.primary-modal {
	color: $white;
	background-color: $primary-color;
}

.white-modal {
	color: $main-color;
	background-color: $white;
}

.danger-modal {
	color: $white;
	background-color: $fail-color;
}
</style>
