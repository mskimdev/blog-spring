package com.test.blog_spring.repository;

import com.test.blog_spring.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Query("SELECT c FROM Reply c JOIN FETCH c.user JOIN FETCH c.board b JOIN FETCH b.user WHERE c.board.id = :boardId")
    List<Reply> findAllByBoardIdJoinUser(Integer boardId);

    @Query("SELECT c FROM Reply c JOIN FETCH c.user JOIN FETCH c.board b JOIN FETCH b.user WHERE c.id = :id")
    Optional<Reply> findByIdJoinAll(Integer id);

    @Modifying
    @Query("DELETE FROM Reply r WHERE r.board.id = :boardId")
    void deleteByBoardId(@Param("boardId") Integer boardId);

}
