package com.test.blog_spring.dto;

import com.test.blog_spring.entity.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class UserRequest {

    @Data
    public static class UpdateDTO {
        String password;
        MultipartFile profileImg;
        String deleteProfileImg;

        public void validate() {
            if(password == null || password.isBlank()){
                throw new IllegalArgumentException("비밀번호는 필수 입니다.");
            }

            if(password.length() < 4) {
                throw new IllegalArgumentException("비밀번호는 4자 이상입니다.");
            }
        }

        public boolean isDeleteProfileImg(){
            return deleteProfileImg.equals("true");
        }
    }

    @Data
    public static class joinDTO {
        private String username;
        private String password;
        private String email;

        // 프로필 이미지(선택사항, null이 될 수 있음)
        // MultipartFile : Spring에서 제공해주고 있는 파일 업로드를 처리하기 위한 Interface
        private MultipartFile profileImg;


        public void validate() {
            if(username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("ID는 필수 입니다.");
            }
            if(password == null || password.trim().isEmpty()){
                throw new IllegalArgumentException("패스워드는 필수 입니다.");
            }
            if(password.length() < 4) {
                throw new IllegalArgumentException("비밀번호는 4자 이상입니다.");
            }
            if(email == null || email.trim().isEmpty()){
                throw new IllegalArgumentException("이메일은 필수 입니다.");
            }
            if(!email.contains("@")){
                throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
            }
        }

        public User toEntity(String profileImg) {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .profileImg(profileImg)
                    .build();
        }


    }

}
