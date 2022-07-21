import { createStore } from "vuex";
import { moduleA, ModuleAState } from "../store/modules/moduleA";
// import { moduleB, ModuleBState } from "../store/modules/moduleB";
import { signup, SignupState } from "../store/accounts/signup";

// 모듈의 state를 공유한다.
export interface RootState {
	ModuleA: ModuleAState;
	// ModuleB: ModuleBState;
	Signup: SignupState;
}

// store를 생성한다.
export default createStore({
	modules: { moduleA, signup },
});
