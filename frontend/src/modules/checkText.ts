// * 비속어 체크 필터
// badWordList 내부의 비속어가 포함되면 true return
function checkBadWord(targetText: string): boolean {
	const badWordList = ["씨발", "시발", "바보", "새끼", "병신"];
	return badWordList.some((badWord) => targetText.includes(badWord));
}

// * 텍스트가 한글 또는 영어만을 포함
// 정규식을 이용한 숫자/영어/한글/특수문자 판별
// 참고 : https://kingofbackend.tistory.com/79
function checkEnKr(text: string): boolean {
	const pattern1 = /[0-9]/; //숫자
	const pattern2 = /[a-zA-Z]/; //영어
	const pattern3 = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; //한글
	const pattern4 = /[~!@#\#$%<>^&*]/; //특수문자

	// 숫자 또는 특수문자를 포함하는 경우 return false
	// 즉, 한글 또는 영어만을 포함한다면 return true
	return !(pattern1.test(text) || pattern4.test(text));
}

// * 텍스트에 특수문자 포함
// 특수문자를 포함하는 경우 true 반환
function checkAsterisk(text: string): boolean {
	const patternAsterisk = /[~!@#\#$%<>^&*]/; //특수문자
	return patternAsterisk.test(text);
}

// * 텍스트가 이메일인지 확인
// @와 .을 가지면 true 반환
function checkIsEmail(text: string): boolean {
	const charA = /[@]/;
	const charB = /[.]/;
	return charA.test(text) && charB.test(text);
}

// * 비밀번호 영어 대문자, 소문자, 숫자, 특수문자 중 3종류 이상 조합
// 위 경우를 만족하면 true 반환
function checkTripleCombination(text: string) {
	const pattern1 = /[0-9]/; //숫자
	const pattern2 = /[a-z]/; //영어 소문자
	const pattern3 = /[A-Z]/; //영어 대문자
	const pattern4 = /[~!@#\#$%<>^&*]/; //특수문자

	let cnt = 0;
	if (pattern1.test(text)) cnt++;
	if (pattern2.test(text)) cnt++;
	if (pattern3.test(text)) cnt++;
	if (pattern4.test(text)) cnt++;
	return cnt > 2;
}

// * 비밀번호 길이 체크
// 8자 이상 20자 이하이면 true 반환
function checkPasswordLength(text: string) {
	let ret = false;
	let passwordLength = text.length;
	if (8 <= passwordLength && passwordLength <= 20) {
		ret = true;
	}
	return ret;
}

// * 닉네임 길이 체크
// 8자 이상 20자 이하이면 true 반환
function checkNicknameLength(text: string) {
	let ret = false;
	let NicknameLength = text.length;
	if (2 <= NicknameLength && NicknameLength <= 12) {
		ret = true;
	}
	return ret;
}

// * 비밀번호/비밀번호 확인 동일 여부 체크
function checkIsEqualPassword(pw1: string, pw2: string) {
	return pw1 === pw2;
}

// * 이메일 형식 체크
function checkEmailCondition(value: string): boolean {
	const emailValid =
		/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	return emailValid.test(value);
}

// * 생일 형식 체크
function checkBirthFormat(text: string) {
	const [Y, M, D] = text.split("/");
	const year = Number(Y);
	const month = Number(M);
	const day = Number(D);
	const now = new Date();

	if (isNaN(year) || isNaN(month) || isNaN(day)) return false;
	if (1900 > year || year > now.getFullYear()) return false;
	if (month > 12 || month < 1) return false;
	if (day > 31 || day < 1) return false;
	return true;
}

export {
	// * 비속어 필터
	checkBadWord,

	// * 한글+영어 체크
	checkEnKr,

	// * 텍스트에 특수문자 포함
	checkAsterisk,

	// * 영어 대문자/소문자/숫자/특수문자 중 3개 이상 포함
	checkTripleCombination,

	// * 텍스트가 이메일인지 확인
	checkIsEmail,

	// * 비밀번호 길이 체크
	checkPasswordLength,

	// * 닉네임 길이 체크
	checkNicknameLength,

	// * 비밀번호/비밀번호 확인 동일 여부 확인
	checkIsEqualPassword,

	// * 이메일 형식 체크
	checkEmailCondition,

	// * 생일 형식 체크
	checkBirthFormat,
};
