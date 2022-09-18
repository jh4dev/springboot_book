package org.zerock.ex2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ex2.entity.Memo;

import javax.transaction.Transactional;
import java.util.List;

/**
 * JpaRepository 상속 시, 엔티티의 타입 정보, PK의 타입 지정
 * 복합키인 경우에는 어떡하지?
 *
 * Spring Data JPA는 인터페이스 선언만으로도 자동으로 빈 등록이 됨
 * 스프링 내부적으로 인터페이스 타입에 맞는 객체를 생성해 빈으로 등호
 * */
public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    void deleteMemoByMnoLessThan(Long num);

    @Query("select m from Memo m where m.mno >= 91 order by m.mno desc")
    List<Memo> getListDesc();

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoTxt = :memoTxt where m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoTxt") String memoTxt);

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoTxt = :#{#param.memoTxt} where m.mno = :#{#param.mno}")
    int updateMemoText(@Param("param") Memo memo);


    @Query(value = "select m from Memo m where m.mno > :mno",
            countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);


    @Query(value = "select m.mno, m.memoTxt, CURRENT_DATE from Memo m where m.mno > :mno"
            , countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

    @Query(value = "select * from tbl_memo where mno > 97", nativeQuery = true)
    List<Object[]> getNativeResult(@Param("mno") Long mno);
}
