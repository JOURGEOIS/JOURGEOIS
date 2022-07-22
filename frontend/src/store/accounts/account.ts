import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";

// Signup의 State 타입을 설정한다.
export interface AccountState {
	logOutModalStatus: boolean;
	loginFailModalStatus: boolean;
}

export const account: Module<AccountState, RootState> = {
	namespaced: true,

	state: {
		logOutModalStatus: false,
		loginFailModalStatus: false,
	},

	getters: {
		getLogOutModalStatus: (state) => {
			return state.logOutModalStatus;
		},
		getLoginFailModalStatus: (state) => {
			return state.loginFailModalStatus;
		},
	},

	mutations: {
		SET_LOGOUT_MODAL: (state, value) => {
			state.logOutModalStatus = value;
		},
		SET_LOGIN_FAIL_MODAL: (state, value) => {
			state.loginFailModalStatus = value;
		},
	},

	actions: {
		toggleLogOutModal: ({ commit }, value) => {
			commit("SET_LOGOUT_MODAL", value);
		},

		logIn: ({ commit }, { email, password }) => {
			const params: object = {
				email,
				password,
			};
			axios({
				url: "http://13.209.206.237/api/member/login",
				method: "post",
				params,
			})
				.then((response) => {
					console.log(response.data);
					console.log("성공");
				})
				.catch((error) => {
					console.log(error.response);
					commit("SET_LOGIN_FAIL_MODAL", true);
				});
		},
	},
};
