const HOST: string = "http://13.209.206.237/api/";

const ACCOUNTS = "member/";
const EMAIL = "email/cert/";
const COCKTAIL = "cocktail/";
const LOOKUP = "lookup/";
const POST = "posts/";

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
};
