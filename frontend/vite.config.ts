import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path";

// https://vitejs.dev/config/
export default defineConfig({
  css : {
    loaderOptions : {
      sass : {
        additionalData: `
          @import "@/styles/style.scss";
        `
      }
    }
  },

  plugins: [vue()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
});
