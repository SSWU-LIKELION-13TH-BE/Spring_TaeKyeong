package com.example.week6.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.week6.dto.CommentDTO;
import com.example.week6.entity.Comment;
import com.example.week6.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    // 댓글 하나 조회하기
    public Optional<Comment> getComment(Long commentId) {
        return commentRepository.findByCommentId(commentId);
    }

    @Transactional
    public Comment postComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // 댓글 수정하기
    @Transactional
    public void putComment(CommentDTO commentDTO) {
        // 댓글 수정 시 기존 데이터와 함께 업데이트
        Comment comment = Comment.builder()
                .commentId(commentDTO.getCommentId())
                .content(commentDTO.getContent())
                .writer(commentDTO.getWriter())
                .commentId(commentDTO.getCommentId())
                .postDate(LocalDate.now())
                .build();
        commentRepository.save(comment);
    }

    // 댓글 삭제하기
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteByCommentId(commentId);
    }

}