package com.jourgeois.backend.util;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class S3Util {
    private final AmazonS3Client amazonS3Client;

    @Autowired
    S3Util(AmazonS3Client amazonS3Client){ this.amazonS3Client = amazonS3Client;}

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile, dirName)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return upload(uploadFile);
    }
    public String localUpload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile, dirName)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        return uploadFile.getName();
//        return upload(uploadFile, dirName);
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile) {
        String fileName = uploadFile.getName();   // S3에 저장된 파일 이름
        System.out.println(fileName);
        putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        System.out.println(fileName);
        return fileName;
    }

    // S3로 업로드
    private void putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file, String dirName) throws IOException {
//        File convertFile = new File(System.getProperty("user.dir") + "/src/main/resources/img/" + file.getOriginalFilename());
        File convertFile = new File(System.getProperty("user.dir") + "/img/" + dirName + "_" +file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public void deleteFile(final String url){
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, url);
        amazonS3Client.deleteObject(request);
    }
}
