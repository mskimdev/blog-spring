package com.test.blog_spring.dto;

import com.test.blog_spring.entity.User;
import lombok.Data;

public class UserRequest {

    @Data
    public static class UpdateDTO {
        String password;

        public void validate() {
            if(password == null || password.isBlank()){
                throw new IllegalArgumentException("비밀번호는 필수 입니다.");
            }

            if(password.length() < 4) {
                throw new IllegalArgumentException("비밀번호는 4자 이상입니다.");
            }
        }
    }

    @Data
    public static class joinDTO {
        String username;
        String password;
        String email;

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

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
        }


    }

}
