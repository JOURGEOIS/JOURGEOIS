import { createStore } from "vuex";
import { account, AccountState } from "../store/accounts/account";
import { signup, SignupState } from "../store/accounts/signup";
import {
  personalInfo,
  PersonalInfoState,
} from "../store/accounts/PersonalInfo";

// 모듈의 state를 공유한다.
export interface RootState {
  Account: AccountState;
  Signup: SignupState;
  personalInfo: PersonalInfoState;
}

// store를 생성한다.
export default createStore({
  modules: { account, signup, personalInfo },
});
