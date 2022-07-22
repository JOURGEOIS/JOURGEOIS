import { useRouter } from "vue-router";
const router = useRouter();

// 이전 페이지로 이동 버튼 클릭
function clickPrevButton() {
  router.go(-1);
}

export { clickPrevButton };
