import { ref, reactive, toRefs } from "vue";
import { Module } from "vuex";
import { RootState } from "../index";
import api from "../../api/api";
import axios from "axios";
import { checkEmailCondition } from "../../functions/checkText";

// Signup의 State 타입을 설정한다.
export interface SignupState {
  currentPage: number;
  totalPage: number;
  serviceUseModalStatus: boolean;
  personalInfoUseModalStatus: boolean;
  profileUseModalStatus: boolean;
  failModalStatus: boolean;
  emailNonAuthModalStatus: boolean;
  completeSignUpModalStatus: boolean;

  // 유저 개인정보
  emailAuthentication: boolean;
  signUpAgreeChecked: Array<boolean>;
  signUpEmail: string;
  signUpPw: string;
  signUpName: string;
  signUpBirth: string;
  signUpNickName: string;
}

// state의 타입추론이 잘 되게 하기 위해 사용한다.
export const signup: Module<SignupState, RootState> = {
  namespaced: true,
  state: {
    currentPage: 3,
    totalPage: 3,
    serviceUseModalStatus: false,
    personalInfoUseModalStatus: false,
    profileUseModalStatus: false,
    failModalStatus: false,
    emailNonAuthModalStatus: false,
    completeSignUpModalStatus: false,

    // 유저 개인정보
    signUpAgreeChecked: [false, false, false, false, false],
    signUpEmail: "",
    emailAuthentication: false,
    signUpName: "",
    signUpPw: "",
    signUpBirth: "",
    signUpNickName: "",
  },
  getters: {
    // * [Page] 회원가입 전체 페이지 및 현재 페이지, 백분율(progress)
    getCurrentPage: (state) => {
      return state.currentPage;
    },
    getTotalPage: (state) => {
      return state.totalPage;
    },
    getProgress: (state): number => {
      return (state.currentPage / (state.totalPage + 1)) * 100;
    },

    // * [Page1] 서비스 약관 동의 모달 상태 get 함수
    getServiceUseModalStatus: (state) => {
      return state.serviceUseModalStatus;
    },
    getPersonalInfoUseModalStatus: (state) => {
      return state.personalInfoUseModalStatus;
    },
    getProfileUseModalStatus: (state) => {
      return state.profileUseModalStatus;
    },

    // * [Pages] 실패 모달
    getFailModalStatus: (state) => {
      return state.failModalStatus;
    },

    // * [Page2] 이메일 인증 없이 다음 누른 경우
    getEmailNonAuthModalStatus: (state) => {
      return state.emailNonAuthModalStatus;
    },

    // * [Page5] 회원가입 성공 모달
    getCompleteSignUpModalStatus: (state) => {
      return state.completeSignUpModalStatus;
    },

    // * [Page1] 동의 체크와 관련
    getSignUpAgreeChecked: (state) => {
      return state.signUpAgreeChecked;
    },

    // * [Page2] 이메일 정보 및 인증 확인 여부 가져오기
    getSignUpEmail: (state) => {
      return state.signUpEmail;
    },

    // * [Page2] 이메일 인증 여부 가져오기
    getEmailAuthentication: (state) => {
      return state.emailAuthentication;
    },

    // * [Page Final] 회원가입 정보 가져오기
    getSignUpPw: (state) => {
      return state.signUpPw;
    },

    getSignUpBirth: (state) => {
      return state.signUpBirth;
    },

    getSignUpNickName: (state) => {
      return state.signUpNickName;
    },

    getSignUpName: (state) => {
      return state.signUpName;
    },
  },
  mutations: {
    // * 회원가입 다음 페이지 이동
    NEXT_SIGNUP_PAGE: (state) => {
      if (state.currentPage <= state.totalPage) {
        state.currentPage++;
      }
    },

    // * 회원가입 이전 페이지 이동
    PREV_SIGNUP_PAGE: (state) => {
      if (state.currentPage > 0) {
        state.currentPage--;
      }
    },

    // * [Pages] 회원가입View에서 이탈하는 유저 정보 제거
    REMOVE_SIGNUP_INFO: (state) => {
      // 동의 상태 해제
      for (let i = 4; i >= 0; i--) {
        state.signUpAgreeChecked[i] = false;
      }

      // 기타 개인정보 제거
      state.signUpEmail = "";
      state.emailAuthentication = false;
      state.signUpName = "";
      state.signUpPw = "";
      state.signUpBirth = "";
      state.signUpNickName = "";
    },

    // * [Page1] 전체 체크 상태 바꾸기
    TOGGLE_ALL_AGREE_CHECKED: (state) => {
      for (let i = 4; i >= 0; i--) {
        state.signUpAgreeChecked[i] = !state.signUpAgreeChecked[0];
      }
    },

    // * [Page1] 일부 체크 상태 바꾸기
    TOGGLE_AGREE_CHECKED: (state, order) => {
      // 해당 체크 toggle
      state.signUpAgreeChecked[order] = !state.signUpAgreeChecked[order];
      // 일부 상태가 전체 체크/미체크인치 확인
      let cnt = 0;
      for (let i = 1; i < 4; i++) {
        if (state.signUpAgreeChecked[i]) cnt++;
      }
      // 전체 체크라면
      state.signUpAgreeChecked[0] = cnt === 3 ? true : false;
    },

    // * [Page1] 서비스 이용약관 동의 모달 toggle
    SET_SERVICE_USE_MODAL: (state) => {
      state.serviceUseModalStatus = !state.serviceUseModalStatus;
    },

    SET_PERSONAL_INFO_USE_MODAL: (state) => {
      state.personalInfoUseModalStatus = !state.personalInfoUseModalStatus;
    },

    SET_PROFILE_USE_MODAL: (state) => {
      state.profileUseModalStatus = !state.profileUseModalStatus;
    },

    // * [Page2] 이메일 인증 임시 저장
    SET_SIGNUP_EMAIL: (state, email) => {
      state.signUpEmail = email;
    },

    // * [Page2] 이메일 임시 인증 완료 체크
    SET_EMAIL_AUTHENTICATION: (state) => {
      state.emailAuthentication = true;
    },

    // * [Page3] 비밀번호 임시 저장
    SET_SIGNUP_PW: (state, password) => {
      state.signUpPw = password;
    },

    // * 회원가입 완료 시 개인정보 state 저장
    SET_SIGNUP_USER_INFO: (state, userInfo) => {
      const { name, nickname, birth } = userInfo;
      state.signUpName = name;
      state.signUpNickName = nickname;
      state.signUpBirth = birth.replaceAll("/", "-");
    },

    // * 에러 모달 toggle 함수
    TOGGLE_FAIL_MODAL_STATUS: (state, value) => {
      state.failModalStatus = value;
    },

    TOGGLE_EMAIL_NON_AUTH_MODAL_STATUS: (state, value) => {
      state.emailNonAuthModalStatus = value;
    },

    // * 회원가입 성공 toggle 함수
    TOGGLE_COMPLETE_SIGNUP_MODAL_STATUS: (state, value) => {
      state.completeSignUpModalStatus = value;
    },
  },

  actions: {
    // * 회원가입 다음 페이지 이동
    nextSignupPage: ({ commit }) => {
      commit("NEXT_SIGNUP_PAGE");
    },

    // * 회원가입 이전 페이지 이동
    prevSignupPage: ({ commit }) => {
      commit("PREV_SIGNUP_PAGE");
    },

    // * [Pages] 회원가입View에서 이탈하는 유저 정보 제거
    removeSignUpInfo: ({ commit }) => {
      commit("REMOVE_SIGNUP_INFO");
    },

    // * [Page1] 전체 체크 상태 바꾸기
    toggleAllAgreeChecked: ({ commit }) => {
      commit("TOGGLE_ALL_AGREE_CHECKED");
    },

    // * [Page1] 일부 체크 상태 바꾸기
    toggleAgreeChecked: ({ commit }, order) => {
      commit("TOGGLE_AGREE_CHECKED", order);
    },

    // * [Page1] 서비스 이용 약관 동의 모달 toggle
    toggleServiceUseModal: ({ commit }) => {
      commit("SET_SERVICE_USE_MODAL");
    },

    togglePersonalInfoUseModal: ({ commit }) => {
      commit("SET_PERSONAL_INFO_USE_MODAL");
    },

    toggleProfileUseModal: ({ commit }) => {
      commit("SET_PROFILE_USE_MODAL");
    },

    // * [Page2] 중복 이메일 확인
    checkEmailDuplication: ({ commit, dispatch }, payload) => {
      const {
        emailInputValue,
        showButtonContainer,
        showDuplicateAlert,
        loadingStatus,
      } = toRefs(payload);
      axios({
        url: api.accounts.signUpCheckEmail(),
        method: "GET",
        params: {
          email: emailInputValue.value,
        },
      })
        // 서버로부터 요청 받음
        .then((res) => {
          // 이메일 중복 없음
          if (res.data.available) {
            // state에 이메일 입력 저장
            commit("SET_SIGNUP_EMAIL", emailInputValue.value);
            // 이메일 인증 보내는 모듈
            const payload = reactive({
              email: emailInputValue.value,
              showButtonContainer,
              loadingStatus,
            });
            dispatch("sendEmailAuthentication", payload);
          }
          // 이메일 중복 있음
          else {
            showDuplicateAlert.value = true;
            showButtonContainer.value = false;
            emailInputValue.value = "";
            loadingStatus.value = false;
          }
        })
        .catch((err) => {
          loadingStatus.value = false;
          dispatch("toggleFailModalStatus", false);
        });
    },

    // * [Page2] 인증용 이메일 전송
    sendEmailAuthentication: ({ commit, dispatch }, payload) => {
      const { email, showButtonContainer, loadingStatus } = toRefs(payload);
      axios({
        url: api.email.emailCert(),
        method: "post",
        data: {
          email: email.value,
        },
      })
        .then((res) => {
          if (res.data.success) {
            // 인증 메일 정상 전송
            // 로딩창 끄기
            loadingStatus.value = false;
            // 버튼 컨테이너를 보이게 하기
            showButtonContainer.value = true;
          } else {
            // 인증 이메일 전송 실패
            dispatch("toggleTimedFailModalStatus");
          }
        })
        .catch((err) => err);
    },

    // * [Page2] 이메일 인증 확인
    confirmEmailAuthentication: (
      { commit, state, getters, dispatch },
      payload
    ) => {
      const { email } = toRefs(payload);
      axios({
        url: api.email.emailConfirmed(),
        method: "post",
        data: {
          email: state.signUpEmail,
        },
      })
        .then((res) => {
          if (typeof res.data.success === "boolean") {
            // 인증 된 경우
            if (res.data.success) {
              commit("SET_EMAIL_AUTHENTICATION");
            }
            // 인증 안 된 경우
            else {
              dispatch("toggleTimedEmailNonAuthModalStatus");
            }
          } else {
            // 문제 있음
            dispatch("toggleTimedFailModalStatus");
          }
        })
        .then((res) => {
          if (getters.getEmailAuthentication) {
            dispatch("nextSignupPage");
          } else {
            dispatch("toggleTimedFailModalStatus");
          }
        })
        .catch((err) => {
          dispatch("toggleTimedFailModalStatus");
        });
    },

    // * [Page3] 비밀번호 임시 저장
    saveSignUpPw: ({ commit }, password) => {
      commit("SET_SIGNUP_PW", password);
    },

    // * [Page Final] 회원가입 완료 전 개인정보 state 저장
    saveSignupUserInfo: ({ commit }, userInfo) => {
      commit("SET_SIGNUP_USER_INFO", userInfo);
    },

    // * [Page Final] 회원가입 완료 접수
    submitSignUp: ({ state, dispatch }, userInfo) => {
      const { email, password, name, nickname, birthday } = userInfo;
      axios({
        method: "POST",
        url: api.accounts.signUp(),
        data: {
          email,
          password,
          name,
          nickname,
          birthday,
        },
      })
        .then((res) => {
          // 회원가입 성공
          if (res.data.success) {
            const loginUserInfo = {
              email: state.signUpEmail,
              password: state.signUpPw,
            };
            dispatch("nextSignupPage");
            setTimeout(() => {
              dispatch("toggleTimedCompleteSignUpModalStatus");
              dispatch("account/login", loginUserInfo, { root: true });
            }, 500);
          }
          // 회원가입 실패
          else {
            dispatch("toggleTimedFailModalStatus");
          }
        })
        .catch((err) => {
          dispatch("toggleTimedFailModalStatus");
        });
    },

    // * 에러 모달 toggle 함수
    toggleFailModalStatus: ({ commit }, value) => {
      commit("TOGGLE_FAIL_MODAL_STATUS", value);
    },

    toggleTimedFailModalStatus: ({ dispatch }, time) => {
      dispatch("toggleFailModalStatus", true);
      if (typeof time !== "number") {
        time = 2000;
      }
      setTimeout(() => dispatch("toggleFailModalStatus", false), time);
    },

    toggleEmailNonAuthModalStatus: ({ commit }, value) => {
      commit("TOGGLE_FAIL_MODAL_STATUS", value);
    },

    toggleTimedEmailNonAuthModalStatus: ({ dispatch }, time) => {
      dispatch("toggleEmailNonAuthModalStatus", true);
      if (typeof time !== "number") {
        time = 2000;
      }
      setTimeout(() => dispatch("toggleEmailNonAuthModalStatus", false), time);
    },

    // * 회원가입 성공 모달 toggle 함수
    toggleCompleteSignUpModalStatus: ({ commit }, value) => {
      commit("TOGGLE_COMPLETE_SIGNUP_MODAL_STATUS", value);
    },

    toggleTimedCompleteSignUpModalStatus: ({ dispatch }, time) => {
      dispatch("toggleCompleteSignUpModalStatus", true);
      if (typeof time !== "number") {
        time = 2000;
      }
      setTimeout(
        () => dispatch("toggleCompleteSignUpModalStatus", false),
        time
      );
    },
  },
};
