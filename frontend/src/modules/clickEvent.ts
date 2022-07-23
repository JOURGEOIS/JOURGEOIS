import router from "../router";

// 이전 페이지로 이동 버튼 클릭
function clickHome() {
  router.push({
    path: "/",
    name: "Home",
  });
}

export { clickHome };
