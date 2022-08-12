const HOST: string = "https://jourgeois.com/api/";

const ACCOUNTS = "member/";
const EMAIL = "email/cert/";
const COCKTAIL = "cocktail/";
const LOOKUP = "lookup/";
const POST = "posts/";
const AWARDS = "awards/";
const PROFILE = "profile/";
const HOME = "home/";
const NOTICE = "notification/auth/";

export default {
  token: {
    token: () => HOST + "token",
  },
  accounts: {
    signUp: () => HOST + ACCOUNTS + "signUp",
    signUpCheckEmail: () => HOST + ACCOUNTS + "signup/checkEmail",
    signUpCheckNickname: () => HOST + ACCOUNTS + "signup/checkNickname",
    login: () => HOST + ACCOUNTS + "login",
    logout: () => HOST + ACCOUNTS + "auth/logout",
    forgotPassword: () => HOST + ACCOUNTS + "password",
    changePassword: () => HOST + ACCOUNTS + "auth/password",
    signOut: () => HOST + ACCOUNTS + "auth/signOut",
    profile: () => HOST + ACCOUNTS + "auth/profile",
    googleLogin: () => HOST + ACCOUNTS + "login/google",
    kakaoLogin: () => HOST + ACCOUNTS + "login/kakao",
    naverLogin: () => HOST + ACCOUNTS + "login/naver",
    follow: () => HOST + ACCOUNTS + "auth/follow",
    unfollow: () => HOST + ACCOUNTS + "auth/unfollow",
    profileUserInfo: () => HOST + ACCOUNTS + PROFILE + "auth",
    profileCommunity: () => HOST + ACCOUNTS + PROFILE + "auth/post",
    profileCustom: () => HOST + ACCOUNTS + PROFILE + "auth/cocktail",
    profileBookmark: () => HOST + ACCOUNTS + PROFILE + "auth/bookmark",
    profileReview: () => HOST + ACCOUNTS + PROFILE + "auth/comment",
  },
  email: {
    emailCert: () => HOST + "email/cert",
    emailVerified: () => HOST + EMAIL + "verified",
    emailConfirmed: () => HOST + EMAIL + "confirmed",
  },
  cocktail: {
    getCocktailData: () => HOST + COCKTAIL + "cocktail",
    cocktailList: () => HOST + COCKTAIL + "list",
    cocktailReview: () => HOST + COCKTAIL + "comment",
    customCocktailList: () => HOST + "/cocktail",
  },

  lookups: {
    cocktail: () => HOST + LOOKUP + "cocktail",
    cocktailall: () => HOST + LOOKUP + "cocktailall",
    wholeCocktail: () => HOST + LOOKUP + "cocktailwhole",
    user: () => HOST + LOOKUP + "user",
    autoComplete: () => HOST + LOOKUP + "search",
    weeklyHotKeyword: () => HOST + LOOKUP + "weeklyhotkeyword",
    hotKeyword: () => HOST + LOOKUP + "hotkeyword",
    filterCnt: () => HOST + LOOKUP + "filter",
    filterResult: () => HOST + LOOKUP + "filter/list",
  },

  post: {
    postCocktail: () => HOST + POST + "auth",
    uploadImage: () => HOST + POST + "auth/tmp",
    comment: () => HOST + POST + "auth/review",
    toggleBookmark: () => HOST + POST + "auth/bookmark",
    toggleLike: () => HOST + POST + "auth/like",
    likedUsers: () => HOST + POST + "auth/like/list",
    listFeed: () => HOST + POST + "auth/feed",
  },

  awards: {
    joinContest: () => HOST + AWARDS + "/auth",
    voteContest: () => HOST + AWARDS + "/auth/like",
    voteCOntestList: () => HOST + AWARDS,
    resultCOntestList: () => HOST + AWARDS + "/result",
    contestDetail: () => HOST + AWARDS + "/auth/info",
  },

  homes: {
    // 홈
    latestCustomCocktail: () => HOST + HOME + "custom/latest5", // 신규 커칵/슈커칵 5개
    latestCustomCocktailView: () => HOST + HOME + "custom/latest", // 신규 커칵/슈커칵 목록
    hotCocktail: () => HOST + HOME + "cocktail/hot5", // 주류주아 인기 칵테일 5개
    hotCocktailView: () => HOST + HOME + "cocktail/hot", // 주류주아 인기 칵테일 목록
  },

  notice: {
    readNotice: () => HOST + NOTICE + "read",
    readNoticeAll: () => HOST + NOTICE + "readall",
  },
};
