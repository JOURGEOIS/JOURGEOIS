import Compressor from "compressorjs";

// 정해진 파일 크기보다 큰 파일이라면 false를 반환한다. (max: 최대 이미지 mb, fileSize: 업로드한 이미지 용량)
function checkImageSize(fileData: { max: number; fileSize: number }): boolean {
  const { max, fileSize } = fileData;

  // 제한되는 사이즈
  const maxSize = max * 1024 * 1024;
  if (maxSize < fileSize) {
    return false;
  }
  return true;
}

// 이미지 compressor (file: 업로드한 파일 )
function compressorImage(file: File) {
  // 이미지 compressor
  const result = new Compressor(file, {
    quality: 0.8,

    // 이미지 compressor에 성공 할 경우, 이미지 크기를 확인 한 뒤 이미지를 서버에 보낸다.
    success(result: File) {
      const fileData = {
        max: 10,
        fileSize: result.size,
      };
      const imageSize = checkImageSize(fileData);

      // compressor를 했음에도 이미지 사이즈가 클 경우,
      if (!imageSize) {
        return false;
      }

      // compressor한 이미지 처리
      const formData = new FormData();
      formData.append("file", result, result.name);
      return formData;
    },

    // 이미지 compressor에 실패할 경우, 알럿을 보낸다.
    error(error) {
      return false;
    },
  });
  return result;
}

// 정해진 파일 확장자가 아니라면 false를 반환한다. (fileName: 이미지 이름)
function checkImageExtension(fileName: string): boolean {
  // 이미지 확장자
  const imageExtensions = ["gif", "jpg", "jpeg", "png", "bmp", "ico", "apng"];

  // 업로드한 파일 확장자 ('.'이후의 문자를 소문자로 변환)
  const extension = fileName
    .substring(fileName.lastIndexOf(".") + 1)
    .toLocaleLowerCase();

  // 업로드한 파일 확장자가 이미지 확장자인지 확인한다.
  const result = imageExtensions.some((item) => item === extension);

  return result;
}

export { checkImageSize, compressorImage, checkImageExtension };
