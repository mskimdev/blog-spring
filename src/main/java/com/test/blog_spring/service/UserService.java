package com.test.blog_spring.service;

import com.test.blog_spring._core.errors.Exception400;
import com.test.blog_spring._core.errors.Exception401;
import com.test.blog_spring._core.errors.Exception404;
import com.test.blog_spring._core.errors.Exception500;
import com.test.blog_spring.dto.UserRequest;
import com.test.blog_spring.entity.User;
import com.test.blog_spring.repository.UserRepository;
import com.test.blog_spring.util.FileUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository ur;


    public User login(String username, String pwd){
        log.info("[로그인 시도] username: {}", username);

        User user = ur.findByIdAndPassword(username, pwd).orElseThrow(
                () -> new Exception401("로그인 정보가 올바르지 않습니다."));

        log.info("[로그인 성공] username: {}", user.getUsername());
        return user;
    }

    @Transactional
    public User update(Integer id, UserRequest.UpdateDTO updateDTO, HttpSession session){
        log.info("[정보 수정] ID : {}", id);
        User userEntity = ur.findById(id).orElseThrow(
                () -> new Exception404("회원 정보를 찾을 수 없습니다.")
        );

        // 기존 이미지
        String profileImgFileName = userEntity.getProfileImg();

        if(updateDTO.isDeleteProfileImg()){
            try{
                FileUtil.deleteFile(profileImgFileName);
                profileImgFileName = null;
            } catch(Exception e){
                log.error("프로필 이미지 삭제 실패 - fileName: {}, error: {}", profileImgFileName, e.getMessage(), e);
                throw new Exception400("프로필 이미지 삭제 오류");
            }
        }

        // 새 이미지
        MultipartFile newImg = updateDTO.getProfileImg();
        if(newImg != null && !newImg.isEmpty()){
            if(!FileUtil.isImageFile(newImg)) throw new Exception400("이미지 파일만 업로드 가능합니다.");
            try{
                FileUtil.deleteFile(profileImgFileName);
                profileImgFileName = FileUtil.saveFile(newImg, FileUtil.IMG_UPLOAD_DIR);
            } catch(Exception e){
                throw new Exception500("프로필 이미지 저장 실패");
            }
        }

        userEntity.update(updateDTO, profileImgFileName);

        log.info("[정보 수정 성공] ID : {}", userEntity.getId());
        session.setAttribute("sessionUser", userEntity);
        return userEntity;
    }


    @Transactional
    public void join(UserRequest.joinDTO joinDTO) {
        log.info("[회원가입 요청] username : {}", joinDTO.getUsername());

        // 회원가입시 사용자 일므 중복 체크
        ur.findByUsername(joinDTO.getUsername()).ifPresent(user -> {
            log.warn("회원가입 실패 - 중복된 사용자명 : {}", user.getUsername());
            throw new Exception400("이미 존재하는 사용자명 입니다.");
        });

        // 프로필 이미지 저장 기능 구현 (선택 사항 임)
        String profileImgFileName = null;
        if(joinDTO.getProfileImg() != null && !joinDTO.getProfileImg().isEmpty()) {
            try{
                // 이미지 파일이 맞는지 검증
                if(!FileUtil.isImageFile(joinDTO.getProfileImg())){
                    throw new Exception400("이미지 파일만 업로드 가능합니다.");
                }
                profileImgFileName = FileUtil.saveFile(joinDTO.getProfileImg(), FileUtil.IMG_UPLOAD_DIR);
            } catch(Exception e){
                // 디스크 공간이 없거나, 권한 없음
                throw new Exception500("프로필 이미지 저장 실패");
            }
        }

        User savedUser = ur.save(joinDTO.toEntity(profileImgFileName));
        log.info("[회원가입 성공] username : {}, ID : {}", savedUser.getUsername(), savedUser.getId());
    }
}
