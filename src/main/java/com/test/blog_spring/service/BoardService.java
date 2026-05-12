package com.test.blog_spring.service;


import com.test.blog_spring._core.errors.Exception403;
import com.test.blog_spring._core.errors.Exception404;
import com.test.blog_spring.dto.BoardRequest;
import com.test.blog_spring.dto.BoardResponse;
import com.test.blog_spring.entity.Board;
import com.test.blog_spring.entity.User;
import com.test.blog_spring.repository.BoardRepository;
import com.test.blog_spring.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository br;
    private final ReplyRepository rr;

    public BoardResponse.PageDTO findAllJoinUser(int page, int size) {
        // 사용자가 음수값을 넣는 것을 방지
        int pageIndex = Math.max(0, page - 1);
        // 사용자가 임의로 많은 값을 던지는 것을 방지
        int validSize = Math.max(1, Math.min(50, size));

        // Pageable 이란?
        // 어떤 페이지를, 몇 개씩, 어떤 정렬로 가져올지를 한 묶음으로 표현하는 Spring Data 표준 페이징 인터페이스이다.
        // 즉, Repository에 Pageable 객체를 넘기면 자동으로 Limit, Offset 만들어준다.
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(pageIndex, validSize, sort);


        // Page<T> 이란?
        // 조회된 데이터를 한 페이지와 페이징 관련된 메타 데이터를 한꺼버에 담아주는 객체
        // getContent() : 현재 페이지의 데이터 목록
        // getNumbers() : 현재 페이지 번호 (0번부터 시작)
        // getTotalElements() : 전체 항목 수
        // getTotalPage() : 전체 페이지 수
        // isFirst() / isLast() : 첫 페이지 / 마지막 페이지 여부
        Page<Board> boardPage = br.findAllWithUserOrderByCreatedAtDesc(pageable);


        return new BoardResponse.PageDTO(boardPage);

        // return br.findAllWithUserOrderByCreatedAtDesc(pageable).stream().map(BoardResponse.ListDTO::new).toList();
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
    @Modifying
    public void delete(Integer id, User sessionUser){
        log.info("[게시글 삭제 요청] boardID : {}, user : {} ", id, sessionUser.getUsername());
        Board board = br.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("해당하는 게시물을 찾을 수 없습니다.")
        );

        if(!board.getUser().getId().equals(sessionUser.getId())){
            throw new Exception403("권한이 없습니다. (본인의 게시물이 아님)");
        }

        rr.deleteByBoardId(board.getId());


        br.deleteById(id);
        log.info("[게시글 삭제 성공] boardID : {}, user : {}", id, sessionUser.getUsername());
    }
}
