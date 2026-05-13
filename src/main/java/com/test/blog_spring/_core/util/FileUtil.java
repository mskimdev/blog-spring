package com.test.blog_spring.util;

import com.test.blog_spring._core.errors.Exception400;
import com.test.blog_spring.dto.UserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

// IoC 안함 (파일 기능 처리시에만 동작할 수 있도록 static method로 구현할 예정)
public class FileUtil {

    // 업로드될 파일 경로를 미리 상수로 지정
    public static final String IMG_UPLOAD_DIR = "/Users/kimminsu/Desktop/_work_java/uploads";


    // 1. 파일 저장
    public static String saveFile(MultipartFile file, String uploadDir) throws IOException {
        // 1단계 : 파일 유효성 검사 - 파일이 없거나 크기가 0이면 오류
        if(file == null || file.isEmpty()) return null;

        // 2단계 : 파일 업로드 존재 확인 / 경로 생성
        // Path : 파일 시스템 경로를 나태내는 객체
        // Path.get() : 문자열 경로를 Path 객체로 변환해주는 함수
        Path uploadPath = Paths.get(IMG_UPLOAD_DIR);
        // Files.exists(uploadPath) : 파일/디렉토리 존재 여부 확인
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath); // 상위 폴더까지 자동 생성 해 줌
        }

        // 3단계 : 원본 파일 이름 가져오기
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null || originalFileName.isBlank()){
            throw new Exception400("파일명이 존재하지 않습니다.");
        }

        // 4단계 : UUID를 사용한 고유 파일명 생성
        String uuid = UUID.randomUUID().toString(); // 난수 발생
        String savedFileName = uuid + "_" + originalFileName;

        // #wasd23safAS@#%!RFWSD@!#$%^TGSAF_a.png 파일명으로 재 생성됨 ( 난수_파일명 )

        // 5단계 : 메모리상에 존재하는 파일 데이터를 로컬 컴퓨터(디스크)에 저장
        // 5.1) 파일폴더경로 + 재생성한파일이름 ---> 정확한 위치에 파일이 생성
        Path filePath = uploadPath.resolve(savedFileName);
        Files.copy(file.getInputStream(), filePath);

        return savedFileName;
    }
    // 2. 파일 삭제
    public static void deleteFile(String fileName) throws IOException{
        if(fileName == null || fileName.isBlank()) return;
        Path filePath = Paths.get(IMG_UPLOAD_DIR, fileName);

        Files.deleteIfExists(filePath);

    }

    // 3. 편의 기능 만들 예정(이미지 파일이 맞는지 확인)
    public static boolean isImageFile(MultipartFile file){
        if(file == null || file.isEmpty()) return false;

        String contentType = file.getContentType(); // image/png, image/jpeg, image/gif
        return contentType.startsWith("image/");

    }

}
