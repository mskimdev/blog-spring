package com.test.blog_spring.service;


import com.test.blog_spring._core.errors.Exception403;
import com.test.blog_spring._core.errors.Exception404;
import com.test.blog_spring.dto.BoardRequest;
import com.test.blog_spring.dto.BoardResponse;
import com.test.blog_spring.entity.Board;
import com.test.blog_spring.entity.User;
import com.test.blog_spring.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository br;

    public List<BoardResponse.ListDTO> findAllJoinUser() {
        return br.findAllJoinUser().stream().map(BoardResponse.ListDTO::new).toList();
    }



    @Transactional
    public void save(BoardRequest.SaveDTO saveDTO, User user){
        log.info("[게시글 작성 요청] title : {}, user : {}", saveDTO.getTitle(), user.getUsername());
        br.save(saveDTO.toEntity(user));
        log.info("[게시글 작성 성공] title : {}, user : {}", saveDTO.getTitle(), user.getUsername());
    }

    public BoardResponse.DetailDTO detail(Integer id){
        Board board = br.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("해당하는 게시물을 찾을 수 없습니다.")
        );
        return new BoardResponse.DetailDTO(board);
    }

    public BoardResponse.DetailDTO updatePage(Integer id, User sessionUser){
        Board board = br.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("해당하는 게시물을 찾을 수 없습니다.")
        );

        if(!board.getUser().getId().equals(sessionUser.getId()))
            throw new Exception403("권한이 없습니다.");

        return new BoardResponse.DetailDTO(board);
    }

    @Transactional
    public void update(Integer id, BoardRequest.UpdateDTO updateDTO, User sessionUser) {
        log.info("[게시글 수정 요청] ID : {}, user : {} ", id, sessionUser.getUsername());
        Board boardEntity = br.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("해당하는 게시물을 찾을 수 없습니다.")
        );
        boardEntity.update(updateDTO);
        log.info("[게시글 수정 성공] ID : {}, user : {} ", id, sessionUser.getUsername());
    }

    @Transactional
    public void delete(Integer id, User sessionUser){
        log.info("[게시글 삭제 요청] boardID : {}, user : {} ", id, sessionUser.getUsername());
        Board board = br.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("해당하는 게시물을 찾을 수 없습니다.")
        );

        if(!board.getUser().getId().equals(sessionUser.getId())){
            throw new Exception403("권한이 없습니다. (본인의 게시물이 아님)");
        }

        br.deleteById(id);
        log.info("[게시글 삭제 성공] boardID : {}, user : {}", id, sessionUser.getUsername());
    }
}
