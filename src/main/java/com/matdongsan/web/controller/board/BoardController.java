package com.matdongsan.web.controller.board;

import com.matdongsan.domain.board.Board;
import com.matdongsan.domain.board.BoardRepository;
import com.matdongsan.service.BoardService;
import com.matdongsan.web.dto.BoardFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @GetMapping("/board/new")
    public String createNewPostPage() {

        return "board/createBoard";
    }

    @PostMapping("/board/new")
    public String createNewPost(@RequestParam String title, @RequestParam String content) {
        BoardFormDto boardFormDto = new BoardFormDto(title, content);
        Board savedBoard = boardService.saveBoard(boardFormDto);



        return "board/detail";
    }

}
