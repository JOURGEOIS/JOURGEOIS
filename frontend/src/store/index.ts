import { createStore } from "vuex";
import { navbar, NavbarState } from "../store/basics/navbar";
import { scroll, ScrollState } from "../store/basics/scroll";
import { modal, ModalState } from "../store/basics/modal";
import { post, PostState } from "../store/basics/post";
import { share, ShareState } from "../store/basics/share";
import { account, AccountState } from "../store/accounts/account";
import { signup, SignupState } from "../store/accounts/signup";
import { socialLogin, SocialLoginState } from "../store/accounts/socialLogin";
import { password, PasswordState } from "../store/accounts/password";
import { follow, FollowState } from "../store/accounts/follow";

// 개인 정보
import {
  personalInfo,
  PersonalInfoState,
} from "../store/accounts/personalInfo";

// 칵테일 정보
import {
  cocktailDesc,
  CocktailDescState,
} from "../store/cocktails/cocktailDesc";

// 검색
import {
  cocktailSearch,
  CocktailSearchState,
} from "../store/cocktails/cocktailSearch";

// 검색 결과
import {
  searchResult,
  SearchResultState,
} from "../store/cocktails/searchResult";

// 칵테일 후기
import {
  cocktailReview,
  CocktailReviewState,
} from "../store/cocktails/cocktailReview";

// 커스텀 칵테일
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

// profile
import { settings, SettingsState } from "../store/profile/settings";
import { profileDesc, ProfileDescState } from "../store/profile/profileDesc";

//contest
import { contest, ContestState } from "../store/feeds/contest";

// 모듈의 state를 공유한다.
export interface RootState {
  navbar: NavbarState;
  scroll: ScrollState;
  modal: ModalState;
  post: PostState;
  share: ShareState;
  account: AccountState;
  signup: SignupState;
  socialLogin: SocialLoginState;
  personalInfo: PersonalInfoState;
  password: PasswordState;
  follow: FollowState;
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
  settings: SettingsState;
  profileDesc: ProfileDescState;
  contest: ContestState;
}

// store를 생성한다.
export default createStore({
  modules: {
    navbar,
    scroll,
    modal,
    post,
    share,
    account,
    signup,
    socialLogin,
    personalInfo,
    password,
    follow,
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
    settings,
    profileDesc,
    contest,
  },
});
