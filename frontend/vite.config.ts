import { defineConfig, loadEnv } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path";

// https://vitejs.dev/config/
export default ({ mode }) => {
  process.env = { ...process.env, ...loadEnv(mode, process.cwd()) };
  return defineConfig({
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: `
            @import "@/styles/style.scss";
          `,
        },
      },
    },
    server: {
      proxy: {
        "/api": {
          target: "http://www.jourgeois.com/",
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, ""),
        },
      },
      open: true, //Auto Open new window
    },
    plugins: [vue()],
    resolve: {
      alias: {
        "@": path.resolve(__dirname, "./src"),
      },
    },
  });
};
