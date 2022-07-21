// * 비속어 체크 필터
// badWordList 내부의 비속어가 포함되면 true return
function checkBadWord(targetText: string): boolean {
	const badWordList = ["씨발", "시발", "바보", "새끼", "병신"];
	return badWordList.some((badWord) => targetText.includes(badWord));
}

// * 정규식을 이용한 숫자/영어/한글/특수문자 판별
// 참고 : https://kingofbackend.tistory.com/79
function checkENKR(text: string): boolean {
	const pattern1 = /[0-9]/; //숫자
	const pattern2 = /[a-zA-Z]/; //영어
	const pattern3 = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; //한글
	const pattern4 = /[~!@#\#$%<>^&*]/; //특수문자

	// 숫자 또는 특수문자를 포함하는 경우 return false
	// 즉, 한글 또는 영어만을 포함한다면 return true
	return !(pattern1.test(text) || pattern4.test(text));
}

// * 비밀번호 체크
function checkIsEqualPassword(pw1: string, pw2: string) {
	return pw1 === pw2;
}

export default {
	// 비속어 필터
	checkBadWord,
	// 한글+영어 체크
	checkENKR,
	// 비밀번호/비밀번호 확인 동일 여부 확인
	checkIsEqualPassword,
};
