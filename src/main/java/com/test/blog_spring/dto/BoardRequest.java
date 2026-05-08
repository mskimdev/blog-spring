package com.test.blog_spring.dto;


import com.test.blog_spring.entity.Board;
import com.test.blog_spring.entity.User;
import lombok.Data;
import org.springframework.transaction.InvalidIsolationLevelException;

public class BoardRequest {

    @Data
    public static class SaveDTO{
        String title;
        String content;

        public void validate(){
            if(title == null || title.trim().isEmpty())
                throw new IllegalArgumentException("제목은 필수 입니다.");

            if(content == null || content.trim().isEmpty())
                throw new IllegalArgumentException("내용은 필수 입니다.");

            if(content.length() < 3)
                throw new IllegalArgumentException("내용은 3자 이상입니다.");
        }

        public Board toEntity(User user){
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO{
        String title;
        String content;

        public void validate(){
            if(title == null || title.trim().isEmpty())
                throw new IllegalArgumentException("제목은 필수 입니다.");
            if(content == null || content.trim().isEmpty())
                throw new IllegalArgumentException("내용은 필수 입니다.");
        }

        public Board toEntity(User user){
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }
}
