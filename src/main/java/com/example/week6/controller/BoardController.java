package com.example.week6.controller;

import com.example.week6.dto.BoardDTO;
import com.example.week6.entity.Board;
import com.example.week6.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글 하나 띄우기
    @GetMapping("/getBoard")
    public Optional<Board> getBoard(@RequestParam(name = "boardId") Long boardId) {
        return boardService.getBoard(boardId);
    }

    // 게시글 작성하기
    @PostMapping("/postBoard")
    public void postBoard(@RequestBody BoardDTO boardDTO) {
        Board board = Board.builder() // new board -> board.setter()과 동일?
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();
        boardService.postBoard(board);
    }

    // 게시글 수정하기
    @PutMapping("/putBoard")
    public void putBoard(@RequestBody BoardDTO boardDTO) {
        boardService.putBoard(boardDTO);
    }

    // 게시글 삭제하기
    @DeleteMapping("/deleteBoard/{boardId}")
    public void deleteBoard(@PathVariable(name="boardId") Long boardId) {
        boardService.deleteBoard(boardId);
    }

    // 이미지 포함 게시글 올리기
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@ModelAttribute BoardDTO imageBoardDTO) {
        try{
            BoardDTO request = BoardDTO.builder()
                    .title(imageBoardDTO.getTitle())
                    .content(imageBoardDTO.getContent())
                    .image(imageBoardDTO.getImage())
                    .writer(imageBoardDTO.getWriter())
                    .build();

            boardService.ImageBoard(request);

            return ResponseEntity.ok("파일 업로드 성공");
        } catch (Exception e) {
            log.error("파일 업로드 실패", e);
            return ResponseEntity.status(400).build();
        }
    }

    // 이미지 포함 게시글 수정하기 (PUT)
    @PutMapping("/upload/{boardId}")
    public ResponseEntity<String> updateFile(
            @PathVariable Long boardId,
            @ModelAttribute BoardDTO imageBoardDTO) {
        try {
            BoardDTO request = BoardDTO.builder()
                    .boardId(boardId)
                    .title(imageBoardDTO.getTitle())
                    .content(imageBoardDTO.getContent())
                    .image(imageBoardDTO.getImage())
                    .writer(imageBoardDTO.getWriter())
                    .build();

            boardService.updateImageBoard(request);
            return ResponseEntity.ok("파일 수정 성공");
        } catch (Exception e) {
            log.error("파일 수정 실패", e);
            return ResponseEntity.status(400).build();
        }
    }

    // 이미지 포함 게시글 삭제하기 (DELETE)
    @DeleteMapping("/upload/{boardId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long boardId) {
        try {
            boardService.deleteBoard(boardId);
            return ResponseEntity.ok("게시글 및 파일 삭제 성공");
        } catch (Exception e) {
            log.error("파일 삭제 실패", e);
            return ResponseEntity.status(400).build();
        }
    }

}
