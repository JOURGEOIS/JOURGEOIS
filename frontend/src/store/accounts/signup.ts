import { ref, reactive, toRefs } from "vue";
import { Module } from "vuex";
import { RootState } from "../index";
import drf from "../../api/drf";
import axios from "axios";
import { checkEmailCondition } from "../../modules/checkText";

// Signup의 State 타입을 설정한다.
export interface SignupState {
	currentPage: number;
	totalPage: number;
	serviceUseModalStatus: boolean;
	personalInfoUseModalStatus: boolean;
	profileUseModalStatus: boolean;
	errorModalStatus: boolean;

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
		currentPage: 0,
		totalPage: 3,
		serviceUseModalStatus: false,
		personalInfoUseModalStatus: false,
		profileUseModalStatus: false,
		errorModalStatus: false,

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

		getErrorModalStatus: (state) => {
			return state.errorModalStatus;
		},

		// * [Page1] 동의 체크와 관련
		getSignUpAgreeChecked: (state) => {
			return state.signUpAgreeChecked;
		},

		// * [Page2] 이메일 정보 및 인증 확인 여부 가져오기
		getSignUpEmail: (state) => {
			return state.signUpEmail;
		},

		getEmailAuthentication: (state) => {
			return state.emailAuthentication;
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
			for (let i = 1; i < 5; i++) {
				if (state.signUpAgreeChecked[i]) cnt++;
			}
			// 전체 미체크라면
			if (cnt === 0) {
				state.signUpAgreeChecked[0] = false;
			}
			// 전체 체크라면
			else if (cnt === 4) {
				state.signUpAgreeChecked[0] = true;
			}
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
		TOGGLE_ERROR_MODAL: (state) => {
			state.errorModalStatus = !state.errorModalStatus;
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

		toggleprofileUseModal: ({ commit }) => {
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
				url: drf.accounts.signUpCheckEmail(),
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
					console.error(err);
					loadingStatus.value = false;
					alert("문제가 발생했습니다!");
				});
		},

		// * [Page2] 인증용 이메일 전송
		sendEmailAuthentication: ({ commit }, payload) => {
			const { email, showButtonContainer, loadingStatus } = toRefs(payload);
			axios({
				url: drf.email.emailCert(),
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
						alert("인증 메일 전송에 실패했습니다.");
					}
				})
				.catch((err) => console.error(err));
		},

		// * [Page2] 이메일 인증 확인
		confirmEmailAuthentication: (
			{ commit, state, getters, dispatch },
			payload
		) => {
			const { email } = toRefs(payload);
			axios({
				url: drf.email.emailConfirmed(),
				method: "post",
				data: {
					email: state.signUpEmail,
				},
			})
				.then((res) => {
					if (res.data.success) {
						commit("SET_EMAIL_AUTHENTICATION");
					} else {
						// 인증 안 되거나 문제 있음
						alert("인증 문제 있다!");
					}
				})
				.then((res) => {
					if (getters.getEmailAuthentication) {
						dispatch("nextSignupPage");
					} else {
						alert("문제 있다 뭐냐 이거");
					}
				})
				.catch((err) => {
					console.error(err);
					console.dir(err);
					alert("에러 발생!");
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
		submitSignUp: ({ commit, state, dispatch }) => {
			axios({
				method: "POST",
				url: drf.accounts.signUp(),
				data: {
					email: state.signUpEmail,
					password: state.signUpPw,
					name: state.signUpName,
					nickname: state.signUpNickName,
					birthday: state.signUpBirth,
				},
			})
				.then((res) => {
					// 회원가입 성공
					if (res.data.success) {
						alert("회원가입에 성공하였습니다.");
						const loginUserInfo = {
							email: state.signUpEmail,
							password: state.signUpPw,
						};
						dispatch("account/login", loginUserInfo, { root: true });
					}
					// 회원가입 실패
					else {
						alert("회원가입에 실패하였습니다.");
					}
				})
				.catch((err) => console.error(err.response));
		},

		// * 에러 모달 toggle 함수
		toggleErrorModal: ({ commit }) => {
			commit("TOGGLE_ERROR_MODAL");
		},
	},
};
