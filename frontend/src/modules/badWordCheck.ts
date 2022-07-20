// 비속어 체크 필터
// badWordList 내부의 비속어가 포함되면 true return

export default function badWordCheck(targetText: string): boolean {
	const badWordList = ["씨발", "시발", "바보", "새끼", "병신"];
	return badWordList.some((badWord) => targetText.includes(badWord));
}
