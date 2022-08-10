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
function compressorImage(file: File): Promise<File> {
  return new Promise((resolve, reject) => {
    new Compressor(file, {
      checkOrientation: false,
      quality: 0.8,
      maxWidth: 1920,
      maxHeight: 1920,
      success(result: File) {
        const compressorImage = new File([result], result.name, {
          type: result.type,
        });
        resolve(compressorImage);
      },
      error(error) {
        reject(error);
      },
    });
  });
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
