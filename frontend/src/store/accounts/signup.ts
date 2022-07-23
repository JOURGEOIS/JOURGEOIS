import { ref, toRefs } from "vue";
import { Module } from "vuex";
import { RootState } from "../index";
import drf from "../../api/drf";
import axios from "axios";
import { checkEmailCondition } from "../../modules/checkText";

// Signup의 State 타입을 설정한다.
export interface SignupState {
	currentPage: number;
	totalPage: number;
	personalInfoUseModalStatus: boolean;
	errorModalStatus: boolean;

	// 유저 개인정보
	signUpEmail: string;
	emailAuthentication: boolean;
}

// state의 타입추론이 잘 되게 하기 위해 사용한다.
export const signup: Module<SignupState, RootState> = {
	namespaced: true,
	state: {
		currentPage: 1,
		totalPage: 3,
		personalInfoUseModalStatus: false,
		errorModalStatus: false,

		// 유저 개인정보
		signUpEmail: localStorage.getItem("sigunUpEmail") || "",
		emailAuthentication: false,
	},
	getters: {
		getCurrentPage: (state) => {
			return state.currentPage;
		},

		getTotalPage: (state) => {
			return state.totalPage;
		},

		getProgress: (state) => {
			return (state.currentPage / (state.totalPage + 1)) * 100;
		},

		getPersonalInfoUseModalStatus: (state) => {
			return state.personalInfoUseModalStatus;
		},

		getErrorModalStatus: (state) => {
			return state.errorModalStatus;
		},

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

		// * 개인정보 이용 동의 모달 toggle
		SET_PERSONAL_INFO_USE_MODAL: (state) => {
			state.personalInfoUseModalStatus = !state.personalInfoUseModalStatus;
		},

		// * 이메일 인증 임시 저장
		SET_SIGNUP_EMAIL: (state, email) => {
			state.signUpEmail = email;
		},

		// * 이메일 임시 인증 완료 체크
		SET_EMAIL_AUTHENTICATION: (state) => {
			state.emailAuthentication = true;
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

		// * 개인정보 이용 동의 모달 toggle
		togglePersonalInfoUseModal: ({ commit }) => {
			commit("SET_PERSONAL_INFO_USE_MODAL");
		},

		// * 중복 이메일 확인
		checkEmailDuplication: ({ commit, dispatch }, payload) => {
			const { emailInputValue, showButtonContainer, showDuplicateAlert } =
				toRefs(payload);
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
						const payload = {
							email: emailInputValue.value,
							showButtonContainer,
						};
						dispatch("sendEmailAuthentication", payload);
					}
					// 이메일 중복 있음
					else {
						showDuplicateAlert.value = true;
						showButtonContainer.value = false;
						emailInputValue.value = "";
					}
				})
				.catch((err) => {
					console.error(err);
					alert("문제가 발생했습니다!");
				});
		},

		// * 인증용 이메일 전송
		sendEmailAuthentication: ({ commit }, payload) => {
			const { email, showButtonContainer } = toRefs(payload);
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
						alert("인증 메일을 전송했습니다.");
						// 버튼 컨테이너를 보이게 하기
						showButtonContainer.value = true;
					} else {
						// 인증 이메일 전송 실패
						alert("인증 메일 전송에 실패했습니다.");
					}
				})
				.catch((err) => console.error(err));
		},

		// * 이메일 인증 확인
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
					console.log("1st");
					if (res.data.success) {
						commit("SET_EMAIL_AUTHENTICATION");
					} else {
						// 인증 안 되거나 문제 있음
						alert("인증 문제 있다!");
					}
				})
				.then((res) => {
					console.log("2nd");
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

		// * 에러 모달 toggle 함수
		toggleErrorModal: ({ commit }) => {
			commit("TOGGLE_ERROR_MODAL");
		},
	},
};
