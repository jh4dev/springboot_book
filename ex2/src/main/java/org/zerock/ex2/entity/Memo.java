package org.zerock.ex2.entity;

import lombok.*;

import javax.persistence.*;

@Entity                             //Entity 선언, 자동 테이블 생성 가능, 멤버 변수에 따라 컬럼도 생성
@Table(name = "tbl_memo")           //테이블 정보, option 확인
@ToString                           //lombok
@Getter                             //lombok
@Setter                             //lombok
@Builder                            //생성자
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id     //PK지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //auto_increment
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoTxt;

    @Transient  //테이블 컬럼과 무관한 필드
    private String nonColumnField;
}
