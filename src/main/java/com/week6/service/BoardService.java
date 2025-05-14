package com.week6.service;

import com.example.week6.dto.BoardDTO;
import com.example.week6.entity.Board;
import com.example.week6.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor // final이 붙은 필드만 생성자 생성
@Slf4j // log 찍을 수 있게 해주는 어노테이션
// 왜 print가 아니고 log?
// 로그 레벨(level) 제공 INFO, DEBUG, WARN, ERROR
// 로그 파일(.log)로 저장하여 운영 환경에서도 확인 가능 등 여러가지 사유로 print 대신 log로 출력 찍어봄
public class BoardService {
    private final BoardRepository boardRepository;

    // 들어온 boardId 값과 db의 boardId 값이 일치하는 row 가져오기
    public Optional<Board> getBoard(Long boardId) {
        return boardRepository.findByBoardId(boardId);
    }

    // 게시글 하나 작성
    public void postBoard(Board board) {
        boardRepository.save(board);
    }

    // 게시글 수정
    @Transactional
    public void putBoard(BoardDTO boardDTO) {
        Board board = Board.builder()
                .boardId(boardDTO.getBoardId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .postDate(LocalDate.now())
                .build();
        
        //save시 기존에 있는 객체라면 merge
        //id를 전달하지 않을 경우 Insert 수행
        //id를 전달할 경우 해당 id에 대한 데이터가 있다면 Update 수행
        //id를 전달할 경우 해당 id에 대한 데이터가 없다면 Insert 수행
        boardRepository.save(board);
    }

    @Transactional // 현재 쓰레드에서 트랜젝션이 없기 때문에 remove 호출 신뢰 불가 / 어노테이션으로 생성해줘야 함
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
