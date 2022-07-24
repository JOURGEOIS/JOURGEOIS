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
	emailAuthentication: boolean;
	signUpAgreeChecked: Array<boolean>;
	signUpEmail: string;
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
		personalInfoUseModalStatus: false,
		errorModalStatus: false,

		// 유저 개인정보
		signUpAgreeChecked: [false, false, false, false, false],
		signUpEmail: "",
		emailAuthentication: false,
		signUpName: "",
		signUpBirth: "",
		signUpNickName: "",
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

		// 동의 체크와 관련된 것
		getSignUpAgreeChecked: (state) => {
			return state.signUpAgreeChecked;
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

		// * [Page1] 개인정보 이용 동의 모달 toggle
		SET_PERSONAL_INFO_USE_MODAL: (state) => {
			state.personalInfoUseModalStatus = !state.personalInfoUseModalStatus;
		},

		// * [Page2] 이메일 인증 임시 저장
		SET_SIGNUP_EMAIL: (state, email) => {
			state.signUpEmail = email;
		},

		// * [Page2] 이메일 임시 인증 완료 체크
		SET_EMAIL_AUTHENTICATION: (state) => {
			state.emailAuthentication = true;
		},

		// * 회원가입 완료 시 개인정보 state 저장
		SET_SIGNUP_USER_INFO: (state, userInfo) => {
			const { name, nickname, birth } = userInfo;
			state.signUpName = name;
			state.signUpNickName = nickname;
			const birthList = birth.split("/");
			birthList[0] = birthList[0].substring(2, 4);
			state.signUpBirth = birthList.join("-");
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

		// * [Page1] 전체 체크 상태 바꾸기
		toggleAllAgreeChecked: ({ commit }) => {
			console.log("다 바뀌어라 얍");
			commit("TOGGLE_ALL_AGREE_CHECKED");
		},

		// * [Page1] 일부 체크 상태 바꾸기
		toggleAgreeChecked: ({ commit }, order) => {
			commit("TOGGLE_AGREE_CHECKED", order);
		},

		// * [Page1] 개인정보 이용 동의 모달 toggle
		togglePersonalInfoUseModal: ({ commit }) => {
			commit("SET_PERSONAL_INFO_USE_MODAL");
		},

		// * 중복 이메일 확인
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
						const payload = {
							email: emailInputValue.value,
							showButtonContainer,
							loadingStatus,
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

		// * 회원가입 완료 시 개인정보 state 저장
		saveSignupUserInfo: ({ commit }, userInfo) => {
			commit("SET_SIGNUP_USER_INFO", userInfo);
		},

		// * 에러 모달 toggle 함수
		toggleErrorModal: ({ commit }) => {
			commit("TOGGLE_ERROR_MODAL");
		},
	},
};
