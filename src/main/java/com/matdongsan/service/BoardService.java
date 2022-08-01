package com.matdongsan.service;

import com.matdongsan.domain.board.Board;
import com.matdongsan.domain.board.BoardRepository;
import com.matdongsan.web.dto.BoardFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public Board saveBoard(BoardFormDto boardFormDto) {
        Board newBoard = Board.builder()
                .title(boardFormDto.getTitle())
                .content(boardFormDto.getContent())
                .build();

        Board savedBoard = boardRepository.save(newBoard);

        return savedBoard;
    }
    // Local
}
