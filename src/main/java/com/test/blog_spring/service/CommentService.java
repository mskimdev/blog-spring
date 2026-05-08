package com.test.blog_spring.service;

import com.test.blog_spring._core.errors.Exception403;
import com.test.blog_spring._core.errors.Exception404;
import com.test.blog_spring.dto.CommentRequest;
import com.test.blog_spring.entity.Board;
import com.test.blog_spring.entity.Comment;
import com.test.blog_spring.entity.User;
import com.test.blog_spring.repository.BoardRepository;
import com.test.blog_spring.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository cr;
    private final BoardRepository br;

    public List<CommentRequest.ListDTO> findAllByBoardIdJoinUser(Integer boardId, Integer sessionUserId){
        List<Comment> commentList = cr.findAllByBoardIdJoinUser(boardId);
        List<CommentRequest.ListDTO> commentRequestList = new ArrayList<>();

        commentList.forEach(comment -> commentRequestList.add(new CommentRequest.ListDTO(comment, sessionUserId)));

        return commentRequestList;
    }

    @Transactional
    public Integer deleteById(Integer id, User sessionUser){
        Comment comment = cr.findByIdJoinAll(id).orElseThrow(
                () -> new Exception404("해당하는 댓글을 찾을 수 없습니다.")
        );

        boolean isCommentOwner = comment.getUser().getId().equals(sessionUser.getId());
        boolean isBoardOwner = comment.getBoard().getUser().getId().equals(sessionUser.getId());

        if (!isCommentOwner && !isBoardOwner)
            throw new Exception403("권한이 없습니다.");

        log.info("[댓글 삭제 요청] 댓글 ID : {}", id);
        cr.deleteById(id);
        log.info("[댓글 삭제 성공] 댓글 ID : {}", id);
        return comment.getBoard().getId();
    }

    @Transactional
    public void save(CommentRequest.SaveDTO saveDTO, User sessionUser){
        log.info("[댓글 추가 요청] boardID : {}, content : {}", saveDTO.getBoardId(), saveDTO.getContent());

        Board board = br.findById(saveDTO.getBoardId()).orElseThrow(
                () -> new Exception404("해당하는 게시물을 찾을 수 없습니다.")
        );

        cr.save(saveDTO.toEntity(board, sessionUser));
        log.info("[댓글 추가 성공] boardID : {}, content : {}", saveDTO.getBoardId(), saveDTO.getContent());
    }
}
