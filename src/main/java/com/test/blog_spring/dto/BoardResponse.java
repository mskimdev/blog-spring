package com.test.blog_spring.dto;

import com.test.blog_spring.entity.Board;
import lombok.Data;

public class BoardResponse {

    @Data
    public static class ListDTO {
        private Integer id;
        private String title;
        private String content;
        private String username;
        private String createdAt;

        public ListDTO(Board board){
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.username = board.getUser().getUsername();
            this.createdAt = board.getTime();
        }
    }

    @Data
    public static class DetailDTO{
        private Integer id; // board PK
        private Integer userId; // user PK
        private String title;
        private String content;
        private String username;
        private String createdAt;

        public DetailDTO(Board board){
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            if(board.getUser() != null) {
                this.username = board.getUser().getUsername();
                this.userId = board.getUser().getId();
            }
            this.createdAt = board.getTime();
        }
    }
}
