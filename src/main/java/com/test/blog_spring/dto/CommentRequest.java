package com.test.blog_spring.dto;

import com.test.blog_spring._core.errors.Exception400;
import com.test.blog_spring.entity.Board;
import com.test.blog_spring.entity.Comment;
import com.test.blog_spring.entity.User;
import lombok.Data;

public class CommentRequest {

    @Data
    public static class ListDTO{
        private Integer id;
        private Integer userId;
        private String username;
        private String content;
        private String createdAt;
        private boolean myComment;

        public ListDTO(Comment comment, Integer sessionUserId){
            this.id = comment.getId();
            this.userId = comment.getUser().getId();
            this.username = comment.getUser().getUsername();
            this.content = comment.getContent();
            this.createdAt = comment.getTime();
            this.myComment = sessionUserId != null && comment.getUser().getId().equals(sessionUserId);
        }
    }

    @Data
    public static class SaveDTO{
        private Integer boardId;
        private String content;

        public SaveDTO(Integer boardId, String content){
            this.boardId = boardId;
            this.content = content;
        }

        public void validate(){
            if(boardId == null)
                throw new Exception400("잘못된 접근.");
            if(content == null || content.trim().isEmpty())
                throw new IllegalArgumentException("내용은 필수입니다.");
        }

        public Comment toEntity(Board board, User user){
            return Comment.builder()
                    .board(board)
                    .user(user)
                    .content(content)
                    .build();

        }
    }
}