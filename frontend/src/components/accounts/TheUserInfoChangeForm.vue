<template>
  <form
    class="user-info-change-form"
    @submit.prevent="submitChangeUserInfoForm"
  >
    <div class="user-info-change-image">
      <round-image :round-image="profileImage"></round-image>
      <label for="my-profile-image">프로필 사진 변경</label>
      <input
        type="file"
        id="my-profile-image"
        @change="changeProfileImage"
        accept="image/*"
      />
    </div>
    <input-basic
      :input-style="nameInputStyle"
      :data="nameInputData"
      v-model.trim="nameInputValue"
    ></input-basic>
    <div v-if="nameErrorStatus" class="change-error-message">
      <p>이름은 한글 또는 영어만 입력이 가능합니다.</p>
    </div>
    <input-basic
      :input-style="nickNameInputStyle"
      :data="nickNameInputData"
      v-model.trim="nickNameInputValue"
    ></input-basic>
    <div v-if="nickNameErrorStatus" class="change-error-message">
      <p v-for="(msg, index) in nickNameErrorMsg" :key="index">{{ msg }}</p>
    </div>
    <div v-if="nickNameDuplicateError" class="change-error-message">
      <p>중복된 닉네임입니다.</p>
    </div>
    <input-basic
      :input-style="introduceInputStyle"
      :data="introduceInputData"
      v-model.trim="introduceInputValue"
    ></input-basic>
    <button-basic
      :button-style="[buttonColor, 'long', 'small']"
      :disabled="nameInputValue == '' || nickNameInputValue == ''"
    >
      확인
    </button-basic>
  </form>
</template>

<script setup lang="ts">
import RoundImage from "@/components/basics/RoundImage.vue";
import InputBasic from "@/components/basics/InputBasic.vue";
import ButtonBasic from "@/components/basics/ButtonBasic.vue";
import { checkNicknameDuplication } from "../../functions/checkUserInfo";
import {
  checkBadWord,
  checkEnKr,
  checkNicknameLength,
} from "../../functions/checkText";
import {
  checkImageSize,
  compressorImage,
  checkImageExtension,
} from "../../functions/image";
import { computed, reactive, ref } from "vue";
import { useStore } from "vuex";

const store = useStore();

// 기존 데이터
const personalInfo = computed(() => store.getters["personalInfo/getUserInfo"]);
const name: string = personalInfo.value.name;
const nickName: string = personalInfo.value.nickname;
const image: string = personalInfo.value.profileImg;
const introduce: string = personalInfo.value.introduce;

// input: 이미지
const profileImage = reactive({
  image: image,
  width: "112px",
});

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
  label: "한줄 소개",
  type: "text",
  maxlength: 30,
});
const introduceInputValue = introduce ? ref(introduce) : ref("");
const introduceInputStyle = ref("normal");

// button-style
const buttonColor = computed(() => {
  if (nameInputValue.value !== "" && nickNameInputValue.value !== "") {
    return "primary";
  } else {
    return "unchecked";
  }
});

// 에러 처리
const nameConditionErrorMessage: string =
  "이름은 한글 또는 영어만 입력이 가능합니다.";
const nickNameConditionErrorMessageA: string =
  "닉네임은 2자 이상 12자 이하입니다.";
const nickNameConditionErrorMEssageB: string =
  "비속어나 타인을 비방하는 표현은 사용할 수 없습니다.";
const nickNameErrorMsg: string[] = reactive([]);
const nickNameDuplicateError = ref(false);

interface file {
  file: File;
}

// 이미지 업로드
let presentImage: object;
const changeProfileImage = (event: Event) => {
  let file = (event.target as HTMLInputElement).files![0];

  // 1. 파일을 업로드 하지 않고 취소 버튼을 누르는 경우
  // 2. 이미지 확장자가 아닌 경우
  // 바로 return 한다.
  if (!file || !checkImageExtension(file.name)) {
    alert("꺼지라마");
    return;
  }
  console.log(file);
  // 이미지 용량이 5mb초과면 compressor를 진행한다.
  if (!checkImageSize({ max: 5, fileSize: file.size })) {
    console.log("hi");
    const result = compressorImage(file);
    // const data: Compressor  = result.file;
    // file = data;
    // if (compressor instanceof FormData ){
    //   file = result
    // }
  }

  const data = {
    imageFile: file,
    profileImage,
  };
  presentImage = data.imageFile;
  store.dispatch("personalInfo/changeProfileImage", data);
};

const submit = (data: object) =>
  store.dispatch("personalInfo/submitChangeUserInfoForm", data);

// 제출
const submitChangeUserInfoForm = () => {
  // 리셋
  nickNameErrorMsg.length = 0;
  nameInputStyle.value = "normal";
  nickNameInputStyle.value = "normal";
  introduceInputStyle.value = "normal";
  nameErrorStatus.value = false;
  nickNameErrorStatus.value = false;
  nickNameDuplicateError.value = false;

  // 유효성 검사 결과
  const nameCondition = checkEnKr(nameInputValue.value);
  const nickNameConditionA = checkNicknameLength(nickNameInputValue.value);
  const nickNameConditionB = !checkBadWord(nickNameInputValue.value);

  // 정보 수정시 전달할 데이터
  const data = {
    name: nameInputValue.value,
    nickname: nickNameInputValue.value,
    profileLink: presentImage,
    introduce: introduceInputValue.value,
  };

  // 제출
  if (nameCondition && nickNameConditionA && nickNameConditionB) {
    // 닉네임을 수정 안한 경우 (중복체크 할 필요가 없다.)
    if (nickNameInputValue.value === nickName) {
      // 제출 axios
      submit(data);
    } else {
      // 닉네임을 수정한 경우 (중복체크를 해야 한다. )
      let result: boolean;
      const func = async () => {
        result = await checkNicknameDuplication(nickNameInputValue.value);
      };
      func().then(() => {
        if (result) {
          // 제출
          submit(data);
        } else {
          nickNameDuplicateError.value = true;
        }
      });
    }
  } else {
    if (!nameCondition) {
      nameInputStyle.value = "error";
      nameErrorStatus.value = true;
    }
    if (!nickNameConditionA) {
      nickNameInputStyle.value = "error";
      nickNameErrorStatus.value = true;
      nickNameErrorMsg.push(nickNameConditionErrorMessageA);
    }
    if (!nickNameConditionB) {
      nickNameInputStyle.value = "error";
      nickNameErrorStatus.value = true;
      nickNameErrorMsg.push(nickNameConditionErrorMEssageB);
    }
  }
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

.change-error-message {
  margin-top: -25px;
  color: $danger-color;
  @include font($fs-sm, $fw-regular);

  p {
    margin: 0;
    padding: 0;
  }
}
</style>
