package com.test.blog_spring.repository;

import com.test.blog_spring.dto.BoardResponse;
import com.test.blog_spring.entity.Board;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // 전체 게시글 조회 + 페이징 처리 + Like 검색
    // 메서더 사용, JPQL, 쿼리 메서드 사용
    @Query(value = """
            SELECT DISTINCT b FROM Board b JOIN FETCH b.user
            WHERE lower(b.title) LIKE lower(concat('%', :keyword, '%'))
            OR lower(b.content) LIKE lower(concat('%', :keyword, '%'))
            ORDER BY b.createdAt DESC
            """,
            countQuery = """
                    SELECT count(DISTINCT b) FROM Board b
                    WHERE lower(b.title) LIKE lower(concat('%', :keyword, '%'))
                    OR lower(b.content) LIKE lower(concat('%', :keyword, '%'))
                    """)
    Page<Board> findByTitleContainingOrContentContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Board b JOIN FETCH b.user WHERE b.id = :id")
    Optional<Board> findByIdJoinUser(Integer id);


}
