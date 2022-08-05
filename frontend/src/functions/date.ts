// * 현재 일시를 기준으로 한글로 얼마 전인지 표시
// 전달인자는 [2022, 8, 5, 1, 22, 18, 83204000]처럼 작성시각, 수정시각 자체
// 현재 시각을 기준으로 `... 전` 정보를 현재 시각을 기준으로 delta값 계산 후 반환
function calcDateDelta(date: number[]): string {
  const [Y, M, D, hour, min, sec, nanoSec] = date;
  const date1: number = new Date(Y, M - 1, D, hour, min, sec).getTime();
  const date2: number = new Date().getTime();

  let ret: number = Math.ceil((date2 - date1) / 1000);
  if (ret < 60) {
    return `${ret}초 전`;
  }
  ret = Math.floor(ret / 60);
  if (ret < 60) {
    return `${ret}분 전`;
  }
  ret = Math.floor(ret / 60);
  if (ret < 24) {
    return `${ret}시간 전`;
  }
  ret = Math.floor(ret / 24);
  if (ret < 7) {
    return `${ret}일 전`;
  }
  ret = Math.floor(ret / 7);
  if (ret < 4) {
    return `${ret}주 전`;
  }
  ret = Math.floor(ret / 4);
  if (ret < 12) {
    return `${ret}달 전`;
  }
  ret = Math.floor(ret / 12);
  return `${ret}년 전`;
}

export {
  // * 현재 일시를 기준으로 한글로 얼마 전인지 표시
  calcDateDelta,
};
