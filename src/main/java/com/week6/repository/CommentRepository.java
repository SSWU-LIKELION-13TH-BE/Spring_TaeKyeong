package com.week6.repository;

import com.example.week6.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 댓글 목록 가져오기 (게시글 ID로)
    Optional<Comment> findByCommentId(Long commentId);

    // 댓글 삭제하기
    void deleteByCommentId(Long commentId);
}
