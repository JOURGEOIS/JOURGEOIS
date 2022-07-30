const HOST: string = "http://13.209.206.237/api/";

const ACCOUNTS = "member/";
const EMAIL = "email/cert/";
const AUTH = "auth/";
const LOOKUP = "lookup/";

export default {
  token: {
    token: () => HOST + "token",
  },
  accounts: {
    signUp: () => HOST + ACCOUNTS + "signUp",
    signUpCheckEmail: () => HOST + ACCOUNTS + "signup/checkEmail",
    signUpCheckNickname: () => HOST + ACCOUNTS + "signup/checkNickname",
    login: () => HOST + ACCOUNTS + "login",
    logout: () => HOST + ACCOUNTS + "logout",
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

  lookups: {
    cocktail: () => HOST + LOOKUP + "cocktail",
    user: () => HOST + LOOKUP + "users",
    autoComplete: () => HOST + LOOKUP + "search",
  },
};
