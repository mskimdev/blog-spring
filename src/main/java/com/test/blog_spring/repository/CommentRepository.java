package com.test.blog_spring.repository;

import com.test.blog_spring.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.user JOIN FETCH c.board b JOIN FETCH b.user WHERE c.board.id = :boardId")
    List<Comment> findAllByBoardIdJoinUser(Integer boardId);

    @Query("SELECT c FROM Comment c JOIN FETCH c.user JOIN FETCH c.board b JOIN FETCH b.user WHERE c.id = :id")
    Optional<Comment> findByIdJoinAll(Integer id);

}
