package org.zerock.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;
    private String type;
    private String keyword;

    //기본값 설정
    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    //JPA에서 사용할 Pageable 객체를 생성하는 것이 목적
    public Pageable getPageable(Sort sort) {

        //JPA에서 페이지 번호가 0부터 시작되는 점을 감안하여, 뷰에서의 1페이지가 0이 될 수 있도록 설정
        //정렬(Sort)는 파라미터로 받아 유동성있게
        return PageRequest.of(page - 1, size, sort);
    }
}

