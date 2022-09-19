package org.zerock.ex3.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data       // Getter/Setter, toString(), equals(), hashCode()를 자동 생성
@Builder(toBuilder = true)
public class SampleDto {
    private Long sno;
    private String first;
    private String last;
    private LocalDateTime regTime;
}
