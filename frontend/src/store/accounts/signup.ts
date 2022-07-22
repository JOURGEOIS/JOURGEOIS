import { Module } from "vuex";
import { RootState } from "../index";

// Signup의 State 타입을 설정한다.
export interface SignupState {
	currentPage: number;
	totalPage: number;
	personalInfoUseModalStatus: boolean;
}

// state의 타입추론이 잘 되게 하기 위해 사용한다.
export const signup: Module<SignupState, RootState> = {
	namespaced: true,
	state: {
		currentPage: 0,
		totalPage: 3,
		personalInfoUseModalStatus: false,
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
	},
	mutations: {
		NEXT_SIGNUP_PAGE: (state) => {
			if (state.currentPage <= state.totalPage) {
				state.currentPage++;
			}
		},
		PREV_SIGNUP_PAGE: (state) => {
			if (state.currentPage > 0) {
				state.currentPage--;
			}
		},
		SET_PERSONAL_INFO_USE_MODAL: (state) => {
			state.personalInfoUseModalStatus = !state.personalInfoUseModalStatus;
		},
	},
	actions: {
		nextSignupPage: ({ commit }) => {
			commit("NEXT_SIGNUP_PAGE");
		},
		prevSignupPage: ({ commit }) => {
			commit("PREV_SIGNUP_PAGE");
		},
		togglePersonalInfoUseModal: ({ commit }) => {
			commit("SET_PERSONAL_INFO_USE_MODAL");
		},
	},
};
