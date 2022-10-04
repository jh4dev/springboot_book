package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegist(){

        BoardDTO dto = BoardDTO.builder()
                .title("TEST title")
                .content("Test Content")
                .writerEmail("user23@aaa.com")
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    void testList() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for(BoardDTO boardDTO : result.getDtoList()) {

            System.out.println(boardDTO);
        }
    }

    @Test
    void getBoardByBno() {
        Long bno = 100L;

        BoardDTO boardDto = boardService.get(bno);

        System.out.println(boardDto);
    }

    @Test
    void deleteWithReplies() {
        Long bno = 3L;
        boardService.removeWithReplies(bno);
    }

    @Test
    void modifyTest() {

        BoardDTO boardDTO = BoardDTO.builder()
                .title("수정제목")
                .content("수정내용")
                .bno(1L)
                .build();

        boardService.modify(boardDTO);
    }
}
