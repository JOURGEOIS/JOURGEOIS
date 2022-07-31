import { createStore } from "vuex";
import { account, AccountState } from "../store/accounts/account";
import { signup, SignupState } from "../store/accounts/signup";
import { password, PasswordState } from "../store/accounts/password";
import {
  personalInfo,
  PersonalInfoState,
} from "../store/accounts/personalInfo";

import {
  cocktailDesc,
  CocktailDescState,
} from "../store/cocktails/cocktailDesc";

import {
  cocktailSearch,
  CocktailSearchState,
} from "../store/cocktails/cocktailSearch";

import {
  searchResult,
  SearchResultState,
} from "../store/cocktails/searchResult";

import {
  cocktailFilterIngredients,
  CocktailFilterIngredientsStatus
} from "../store/cocktails/cocktailFilterIngredients"

// 모듈의 state를 공유한다.
export interface RootState {
  account: AccountState;
  signup: SignupState;
  personalInfo: PersonalInfoState;
  password: PasswordState;
  cocktailDesc: CocktailDescState;
  searchResult: SearchResultState;
  cocktailFilterIngredients: CocktailFilterIngredientsStatus;
}

// store를 생성한다.
export default createStore({
  modules: {
    account,
    signup,
    personalInfo,
    password,
    cocktailDesc,
    cocktailSearch,
    searchResult,
    cocktailFilterIngredients,
  },
});
