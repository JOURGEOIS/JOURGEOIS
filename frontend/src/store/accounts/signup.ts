import { Module } from "vuex";
import { RootState } from "../index";

// Signup의 State 타입을 설정한다.
export interface SignupState {
	currentPage: number;
	totalPage: number;
	page1allCheck: boolean;
	page1isCheckedList: boolean[];
	page1checkContentList: string[];
	page1checkDetailContentList: string[];
}

// state의 타입추론이 잘 되게 하기 위해 사용한다.
export const signup: Module<SignupState, RootState> = {
	namespaced: true,
	state: {
		currentPage: 0,
		totalPage: 4,
		page1isCheckedList: [false, false, false, false],
		page1allCheck: false,
		page1checkContentList: [
			"만 14세 이상입니다.",
			"[필수] 주류주아 계정 약관",
			"[필수] 개인정보 수집 및 이용 동의",
			"[선택] 프로필정보 추가 수집 동의",
		],
		page1checkDetailContentList: ["modal1", "modal2", "modal3", "modal4"],
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

		getPage1isChecked: (state) => {
			return state.page1isCheckedList;
		},
		getPage1checkContent: (state) => {
			return state.page1checkContentList;
		},
		getPage1checkDetailContent: (state) => {
			return state.page1checkDetailContentList;
		},
	},
	mutations: {
		SWITCH_ISCHECKED: (state, order) => {
			state.page1isCheckedList[order] = !state.page1isCheckedList[order];
		},
		NEXT_SIGNUP_PAGE: (state) => {
			if (state.currentPage < state.totalPage) {
				state.currentPage++;
			}
		},
		PREV_SIGNUP_PAGE: (state) => {
			if (state.currentPage > 0) {
				state.currentPage--;
			}
		},
	},
	actions: {
		switchIsChecked: ({ commit }, order) => {
			commit("SWITCH_ISCHECKED", order);
		},
		nextSignupPage: ({ commit }) => {
			commit("NEXT_SIGNUP_PAGE");
		},
		prevSignupPage: ({ commit }) => {
			commit("PREV_SIGNUP_PAGE");
		},
	},
};
