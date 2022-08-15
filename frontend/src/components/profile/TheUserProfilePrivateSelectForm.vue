<template>
  <div class="the-userprofileprivate-select-form">
    <p>계정 공개 범위</p>
    <p><span class="material-icons-outlined">lock</span>
      비공개 계정
    </p>
    <div class="toggle normal">
      <label for="toggle" class="toggleSwitch" :class="{active: isActive}" @click="toggleClick">
      <span class="toggleButton"></span>
    </label>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
const isActive = ref(false)

const toggleClick = () => {
  isActive.value = !isActive.value
}
</script>

<style scoped lang="scss">
.toggleSwitch {
  width: 6rem;
  height: 3rem;
  display: block;
  position: relative;
  border-radius: 2rem;
  background-color: #fff;
  cursor: pointer;
}

.toggleSwitch .toggleButton {
  width: 2.6rem;
  height: 2.6rem;
  position: absolute;
  top: 50%;
  left: .2rem;
  transform: translateY(-50%);
  border-radius: 50%;
  background: $primary300;
}

/* 체크박스가 체크되면 변경 이벤트 */
.toggleSwitch.active {
  background: #f03d3d;
}

.toggleSwitch.active .toggleButton {
  left: calc(100% - 2.8rem);
  background: #fff;
}

.toggleSwitch, .toggleButton {
  transition: all 0.2s ease-in;
}
</style>