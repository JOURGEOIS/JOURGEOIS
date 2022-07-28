import axios from "axios";
import drf from "../api/drf";

// * 닉네임 중복 확인
// 중복되면 false 반환
// 사용법 : https://www.notion.so/0537897e1fdb4d608db584af5ed480d8?p=8ec6f1ad51fa4e538fd8cd92002bc24e&pm=c
async function checkNicknameDuplication(text: string): Promise<boolean> {
  console.log("저예유~");
  if (!text) return false;
  const res = await axios({
    url: drf.accounts.signUpCheckNickname(),
    method: "get",
    params: {
      nickname: text,
    },
  });
  console.log(res);
  return res.data.available;
}

export {
  // * 닉네임 중복 확인
  checkNicknameDuplication,
};
