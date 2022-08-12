package com.jourgeois.backend.util;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class S3Util {
    private final AmazonS3Client amazonS3Client;

    private final String IMG_TMP = "/app/DATA/tmp";
//    private final String IMG_TMP = "C:///app/DATA/tmp/";

    @Autowired
    S3Util(AmazonS3Client amazonS3Client){ this.amazonS3Client = amazonS3Client;}

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름


    private static final String s3url = "https://jourgeois-profile-image.s3.ap-northeast-2.amazonaws.com/";

    public static String s3urlFormatter(String withoutDomain) {
        return S3Util.s3url + withoutDomain;
    }

    public String upload(MultipartFile multipartFile, Long dirName, ImgType imgType) throws IOException {
        File uploadFile = convert(multipartFile, dirName, imgType)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        upload(uploadFile, dirName, imgType);
        System.out.println(uploadFile.getName());
        return URLEncoder.encode(imgType.getValue() + "/" + dirName + "/" + doFilterFilename(uploadFile.getName()), "UTF-8");
    }

    public String localUpload(MultipartFile multipartFile, Long dirName, ImgType imgType) throws IOException {
        File uploadFile = convert(multipartFile, dirName, imgType)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        String url = imgType.getValue() + "/" + dirName + "/" + doFilterFilename(uploadFile.getName());
        return url;
    }

    // S3로 파일 업로드하기
    private void upload(File uploadFile, Long dirName, ImgType imgType) {

        String fileName = imgType.getValue() + "/" + dirName + "/" + this.doFilterFilename(uploadFile.getName());   // S3에 저장된 파일 이름
        putS3(uploadFile, fileName); // s3로 업로드
        try {
            File tmp_img = new File(IMG_TMP + "/" + imgType.getValue() + "/" + dirName);
            System.out.println(tmp_img.getPath());
            removeFiles(tmp_img);
        } catch (NullPointerException e) {
            System.out.println("삭제할 파일 없음");
        }
    }

    // S3로 업로드
    private void putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    // 로컬에 저장된 이미지와 폴더 삭제
    private void removeFiles(File folderPath) {
        List<File> files = List.of(folderPath.listFiles());
        for (int i = 0; i < files.size(); i++) {
            if(files.get(i).exists()){
                files.get(i).delete();
            }
        }
        folderPath.delete();
    }

    // 로컬에 파일 업로드 하기
    public Optional<File> convert(MultipartFile file, Long dirName, ImgType imgType) throws IOException {
        String folderPath = IMG_TMP + "/" + imgType.getValue() + "/" + dirName + "/";
        System.out.println(folderPath);
        File makeFolder = new File(folderPath);
        if(!makeFolder.exists()){
            System.out.println("폴더 생기니?");
            makeFolder.mkdirs();
            System.out.println("폴더 만들었음");
        }

        File convertFile = new File(folderPath + Long.toString(System.nanoTime()) + "_" + this.doFilterFilename(file.getOriginalFilename()));
        System.out.println(convertFile.getAbsolutePath());
        URL url = ResourceUtils.getURL(folderPath);
        System.out.println("url임" + url);
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public void deleteFile(final String url) throws SdkClientException {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, url);
        amazonS3Client.deleteObject(request);
    }

    private String doFilterFilename(final String fileName) throws SdkClientException {
        return fileName.replaceAll(" ", "_").replace("^\\.+", "").replaceAll("[\\\\/:*?\"<>()|]", "");
    }
}
