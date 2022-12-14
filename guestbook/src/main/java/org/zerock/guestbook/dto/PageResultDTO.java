package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Desc Page<Entity> 의 entity 객체들을 DTO 객체로 변환하여 자료구조로 담아야 함
 * */
@Data
public class PageResultDTO<DTO, EN>{

    //DTO리스트
    private List<DTO> dtoList;

    private int totalPage;              //총 페이지 수
    private int page;                   //현재 페이지 번호
    private int size;                   //목록 사이즈
    private int start, end;             //시작 페이지 번호, 끝 페이지 번호
    private boolean prev, next;         //이전, 다음
    private List<Integer> pageList;     //페이지 번호 목록

    private void makePageList(Pageable pageable) {

        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page/10.0) * 10);

        start = tempEnd -9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }



}
