<template>
  <div>
    <the-log-out-modal></the-log-out-modal>
  </div>
</template>

<script setup lang="ts">
import TheLogOutModal from "@/components/accounts/TheLogOutModal.vue";
import { ref, reactive } from "vue";

const profileImage = reactive({
  image:
    "https://github.com/JaeKP/image_repo/blob/main/img/%EB%91%98%EC%BD%94%EC%BD%94!!.png?raw=true",
  width: "100px",
});

const trueValue = ref(true);

const data: object = reactive({
  button: true,
  id: "id-input",
  label: "아이디",
  placeholder: "example@gmail.com",
});

const inputStyle = ref("normal");

const colors = reactive([
  "primary",
  "red",
  "emerald",
  "blue",
  "navy",
  "main",
  "sub",
]);
</script>

<style scoped>
div {
  margin: 1rem 0;
}
</style>
