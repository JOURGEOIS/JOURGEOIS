// 정규식을 이용한 숫자/영어/한글/특수문자 판별
// 참고 : https://kingofbackend.tistory.com/79

function enkrCheck(text: string): boolean {
	const pattern1 = /[0-9]/; //숫자
	const pattern2 = /[a-zA-Z]/; //영어
	const pattern3 = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; //한글
	const pattern4 = /[~!@#\#$%<>^&*]/; //특수문자

	// 숫자 또는 특수문자를 포함하는 경우 return false
	// 즉, 한글 또는 영어만을 포함한다면 return true
	return !(pattern1.test(text) || pattern4.test(text));
}

export default {
	enkrCheck,
};
