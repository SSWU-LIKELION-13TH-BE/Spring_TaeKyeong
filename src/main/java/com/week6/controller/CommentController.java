package com.week6.controller;

import com.example.week6.dto.CommentDTO;
import com.example.week6.entity.Comment;
import com.example.week6.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 목록 조회하기 (게시글 ID로)
    @GetMapping("/getComment")
    public Optional<Comment> getComment(@RequestParam(name = "commentId") Long commentId) {
        return commentService.getComment(commentId);
    }

    // 댓글 작성하기
    @PostMapping("/postComment")
    public void postComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .content(commentDTO.getContent())
                .writer(commentDTO.getWriter())
                .postDate(LocalDate.now())
                .commentId(commentDTO.getCommentId())
                .build();
        commentService.postComment(comment);
    }

    // 댓글 수정하기
    @PutMapping("/putComment")
    public void putComment(@RequestBody CommentDTO commentDTO) {
        commentService.putComment(commentDTO);
    }

    // 댓글 삭제하기
    @DeleteMapping("/deleteComment/{commentId}")
    public void deleteComment(@PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }
}
