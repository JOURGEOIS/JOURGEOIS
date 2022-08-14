<template>
  <div class="the-user-profile-private-select-form">
    <p>계정 공개 범위</p>
    <div class="private-mode-set">
      <p><span class="material-icons-outlined">lock</span>
        비공개 계정
      </p>
      <div class="toggle normal">
        <label for="toggle" class="toggleSwitch" :class="{active: isActive}" @click="toggleClick">
        <span class="toggleButton"></span>
      </label>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { useStore } from "vuex";
const store = useStore();

const isActivea = computed(() => 
  store.getters["profileDesc/getPrivateModeSet"]
)

const isActive = ref(isActivea.value)

const toggleClick = () => {
  isActive.value = !isActive.value
  if (isActive.value) {
    store.dispatch("profileDesc/changePrivateModeSet", 0)
  } else {
    store.dispatch("profileDesc/changePrivateModeSet", 1)
  }
}
</script>

<style scoped lang="scss">
// .private-mode-set {
//   @include flex;
// }
.toggleSwitch {
  width: 3rem;
  height: 1.5rem;
  display: block;
  position: relative;
  border-radius: 2rem;
  border: 1px solid $primary300;
  cursor: pointer;
}

.toggleSwitch .toggleButton {
  width: 1rem;
  height: 1rem;
  position: absolute;
  top: 50%;
  left: .2rem;
  transform: translateY(-50%);
  border-radius: 50%;
  background: $primary300;
}

/* 체크박스가 체크되면 변경 이벤트 */
.toggleSwitch.active {
  background: $primary300;
}

.toggleSwitch.active .toggleButton {
  left: calc(100% - 1.1rem);
  background: $white;
}

.toggleSwitch, .toggleButton {
  transition: all 0.2s ease-in;
}
</style>