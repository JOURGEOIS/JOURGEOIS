import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios from "axios";
import api from "./api/api";

const app = createApp(App);
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$api = api;
app.config.globalProperties.$store = store;

app.use(store).use(router);
app.mount("#app");

declare global {
  interface Window {
    Kakao: any;
  }
}
