package com.test.blog_spring.repository;

import com.test.blog_spring.dto.BoardResponse;
import com.test.blog_spring.entity.Board;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT b FROM Board b JOIN FETCH b.user ORDER BY b.id DESC")
    List<Board> findAllJoinUser();

    // 전체 게시글 조회 + 페이징 처리
    @Query(value = """
            SELECT DISTINCT b FROM Board b JOIN FETCH b.user ORDER BY b.id DESC
            """, countQuery = """
            SELECT count(DISTINCT b) FROM Board b
            """)
    Page<Board> findAllWithUserOrderByCreatedAtDesc(Pageable pageable);

    // select b.*, u.username
    //from board_tb b
    //inner join user_tb u on b.user_id = u.id
    //order by b.created_at desc
    //limit 3 offset 9;

    @Query("SELECT b FROM Board b JOIN FETCH b.user WHERE b.id = :id")
    Optional<Board> findByIdJoinUser(Integer id);
}
