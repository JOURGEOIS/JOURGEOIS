<template>
  <form class="user-info-change-form" @submit="submitChangeUserInfoForm">
    <div class="user-info-change-image">
      <the-profile-image :profile-image="profileImage"></the-profile-image>
      <label for="my-profile-image">프로필 사진 변경</label>
      <input type="file" id="my-profile-image" @change="changeProfileImage" />
    </div>
    <input-basic
      :input-style="nameInputStyle"
      :data="nameInputData"
      v-model="nameInputValue"
    ></input-basic>
    <div v-if="nameErrorStatus"></div>
    <input-basic
      :input-style="nickNameInputStyle"
      :data="nickNameInputData"
      v-model="nickNameInputValue"
    ></input-basic>
    <div v-if="nickNameErrorStatus"></div>
    <input-basic
      :input-style="introduceInputStyle"
      :data="introduceInputData"
      v-model="introduceInputValue"
    ></input-basic>
    <div v-if="introduceErrorStatus"></div>
  </form>
</template>

<script setup lang="ts">
import TheProfileImage from "@/components/basics/TheProfileImage.vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import { computed, reactive, ref } from "vue";
import { useStore } from "vuex";

const store = useStore();

const personalInfo = computed(() => store.getters["personalInfo/getUserInfo"]);
const name: string = personalInfo.value.name;
const nickName: string = personalInfo.value.nickname;
const image: string = personalInfo.value.profileImg;
const introduce: string = personalInfo.value.introduce;

// 프로필 이미지
const profileImage = {
  // image,
  width: "112px",
};

// input: name
const nameInputData: object = reactive({
  button: true,
  id: "my-change-name-input",
  label: "이름",
  type: "text",
});

const nameInputValue = ref(name);
const nameInputStyle = ref("normal");
const nameErrorStatus = ref(false);

// input: nickname
const nickNameInputData: object = reactive({
  button: true,
  id: "my-change-nickname-input",
  label: "닉네임",
  type: "text",
  maxlength: 12,
});
const nickNameInputValue = ref(nickName);
const nickNameInputStyle = ref("normal");
const nickNameErrorStatus = ref(false);

// input: introduceInput
const introduceInputData: object = reactive({
  button: true,
  id: "my-change-introduce-input",
  label: "소개",
  type: "text",
  maxlength: 100,
});
const introduceInputValue = introduce ? ref(introduce) : "";
const introduceInputStyle = ref("normal");
const introduceErrorStatus = ref(false);

// 에러 처리
const nameConditionErrorMessage: string =
  "이름은 한글 또는 영어만 입력이 가능합니다.";
const badWordConditionErrorMessage: string =
  "비속어나 타인을 비방하는 표현은 사용할 수 없습니다.";
const nickNameConditionErrorMEssage: string =
  "닉네임은 2자 이상 12자 이하입니다.";
const nickNameConditionErrorMessage: string = "중복된 닉네임입니다.";

// 이미지 업로드
const changeProfileImage = () => {};

// 제출
const submitChangeUserInfoForm = () => {
  // 리셋
  nameInputStyle.value = "normal";
  nickNameInputStyle.value = "normal";
  introduceInputStyle.value = "normal";

  // 유효성 검사 결과
};
</script>

<style scoped lang="scss">
.user-info-change-form {
  @include flex(column);
  gap: 32px;
  width: 100%;

  .user-info-change-image {
    @include flex(column);
    align-items: center;
    justify-content: center;
    gap: 16px;

    label {
      @include font($fs-md, $fw-medium);
      color: $primary-color;
    }
    input {
      display: none;
    }
  }

  input {
    width: 100%;
  }
}
</style>
