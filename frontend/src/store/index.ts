import { createStore } from "vuex";
import { navbar, NavbarState } from "../store/basics/navbar";
import { scroll, ScrollState } from "../store/basics/scroll";
import { post, PostState } from "../store/basics/post";
import { share, ShareState } from "../store/basics/share";
import { account, AccountState } from "../store/accounts/account";
import { signup, SignupState } from "../store/accounts/signup";
import { socialLogin, SocialLoginState } from '../store/accounts/socialLogin';
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
  customCocktailInfo,
  CustomCocktailInfoState,
} from "../store/cocktails/customCocktailInfo";

import { newsFeed, NewsFeedState } from "../store/feeds/newsFeed";

// ? Feed
import { createFeed, CreateFeedState } from "../store/feeds/createFeed";

import { feedDescInfo, FeedDescState } from "../store/feeds/feedDescInfo";

import { comment, Comment } from "../store/basics/comment";

// 모듈의 state를 공유한다.
export interface RootState {
  navbar: NavbarState;
  scroll: ScrollState;
  post: PostState;
  share: ShareState;
  account: AccountState;
  signup: SignupState;
  socialLogin: SocialLoginState;
  personalInfo: PersonalInfoState;
  password: PasswordState;
  cocktailSearch: CocktailSearchState;
  cocktailDesc: CocktailDescState;
  searchResult: SearchResultState;
  cocktailReview: CocktailReviewState;
  customCocktail: CustomCocktailState;
  customCocktailInfo: CustomCocktailInfoState;
  createFeed: CreateFeedState;
  feedDescInfo: FeedDescState;
  comment: Comment;
  newsFeed: NewsFeedState;
}

// store를 생성한다.
export default createStore({
  modules: {
    navbar,
    scroll,
    post,
    share,
    account,
    signup,
    socialLogin,
    personalInfo,
    password,
    cocktailDesc,
    cocktailSearch,
    searchResult,
    cocktailReview,
    customCocktail,
    customCocktailInfo,
    createFeed,
    feedDescInfo,
    comment,
    newsFeed,
  },
});
