<template>
  <div class="the-user-profile-private-mode-set">
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
    <button-basic
      :button-style="[buttonColor, 'long', 'small']"
      class="buttonStyle"
      @click="submit"
    >
      확인
    </button-basic>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { useStore } from "vuex";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { useRouter } from 'vue-router';
const store = useStore();
const router = useRouter();

const userId = computed(() => store.getters["personalInfo/getUserInfoUserId"]);

const userInfo = computed(() => store.getters['profileDesc/getCurrentUserData'])
const isPrivate = computed(() => store.getters['profileDesc/getPrivateModeSet'])

// button-style
const buttonColor = computed(() => "primary")

const isActive = computed(() => {
  if (isPrivate.value === 1) {
    return true
  } else {
    return false
  }
})

const toggleClick = () => {
  store.dispatch("profileDesc/changePrivateModeSet")
}

const submit = () => {
  router.push({name: "TheUserProfileView", params:{userId: userId.value}})
}
</script>

<style scoped lang="scss">
.the-user-profile-private-mode-set {
  @include flex(column);
  width: 100%;
  gap: 20px;
  .private-mode-set {
    @include flex-xy(space-between, center);
    p {
      @include flex-center;
    }
  }
  .buttonStyle {
    margin-top: 20px;
  }
}
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