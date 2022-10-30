package org.zerock.mreview.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass       //해당 어노테이션이 적용된 클래스는 테이블로 생성되지 않는다.
@EntityListeners(value = {AuditingEntityListener.class})    //JPA내부에서 엔티티 객체가 생성/변경되는 것을 감지하는 역할을 하는 리스너
//@EnableJpaAuditing 설정을 해주어야 함(Main application)
@Getter
abstract class BaseEntity {

    @CreatedDate    //엔티티의 생성 시간
    @Column(name ="regDate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate   //엔티티의 최종 수정시간을 자동으로 처리
    @Column(name = "modDate")
    private LocalDateTime modDate;
}
