package com.test.blog_spring.dto;

import com.test.blog_spring._core.errors.Exception400;
import com.test.blog_spring.entity.Board;
import com.test.blog_spring.entity.Reply;
import com.test.blog_spring.entity.User;
import lombok.Data;

public class ReplyRequest {

    @Data
    public static class ListDTO{
        private Integer id;
        private Integer userId;
        private String username;
        private String content;
        private String createdAt;
        private boolean myComment;

        public ListDTO(Reply reply, Integer sessionUserId){
            this.id = reply.getId();
            this.userId = reply.getUser().getId();
            this.username = reply.getUser().getUsername();
            this.content = reply.getContent();
            this.createdAt = reply.getTime();
            this.myComment = sessionUserId != null && reply.getUser().getId().equals(sessionUserId);
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

        public Reply toEntity(Board board, User user){
            return Reply.builder()
                    .board(board)
                    .user(user)
                    .content(content)
                    .build();

        }
    }
}