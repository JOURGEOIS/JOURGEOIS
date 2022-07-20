import { Module } from "vuex";
import { RootState } from "../index";

// ModuleA의 State 타입을 설정한다.
export interface ModuleAState {
  counter: number;
}

// state의 타입추론이 잘 되게 하기 위해 사용한다.
export const moduleA: Module<ModuleAState, RootState> = {
  namespaced: true,
  state: {
    counter: 0,
  },
  getters: {
    getCounter: (state) => {
      return state.counter;
    },
  },
  mutations: {
    SET_COUNTER: (state, value) => {
      state.counter = state.counter + value;
    },
  },
  actions: {
    plusOneCounter: ({ commit }) => {
      commit("SET_COUNTER", 1);
    },
  },
};
