const HOST: string = "http://13.209.206.237/api/";

const ACCOUNTS = "member/";
const EMAIL = "email/cert/";
const AUTH = "auth/";

export default {
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

  auth: {
    refreshToken: () => HOST + AUTH + "token",
  },

  email: {
    emailCert: () => HOST + "email/cert/",
    emailVerified: () => HOST + EMAIL + "verified",
    emailConfirmed: () => HOST + EMAIL + "confirmed",
  },
};
