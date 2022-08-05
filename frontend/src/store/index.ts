import { createStore } from "vuex";
import { navbar, NavbarState } from "../store/basics/navbar";
import { scroll, ScrollState } from "../store/basics/scroll";
import { post, PostState } from "../store/basics/post";
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
  cocktailReview,
  CocktailReviewState,
} from "../store/cocktails/cocktailReview";

import {
  customCocktail,
  CustomCocktailState,
} from "../store/cocktails/customCocktail";

import {
  customCocktailDesc,
  CustomCocktailDescState,
} from "../store/cocktails/customCocktailDesc";

// 모듈의 state를 공유한다.
export interface RootState {
  navbar: NavbarState;
  scroll: ScrollState;
  post: PostState;
  account: AccountState;
  signup: SignupState;
  personalInfo: PersonalInfoState;
  password: PasswordState;
  cocktailSearch: CocktailSearchState;
  cocktailDesc: CocktailDescState;
  searchResult: SearchResultState;
  cocktailReview: CocktailReviewState;
  customCocktail: CustomCocktailState;
  customCocktailDesc: CustomCocktailDescState;
}

// store를 생성한다.
export default createStore({
  modules: {
    navbar,
    scroll,
    post,
    account,
    signup,
    personalInfo,
    password,
    cocktailDesc,
    cocktailSearch,
    searchResult,
    cocktailReview,
    customCocktail,
    customCocktailDesc,
  },
});
