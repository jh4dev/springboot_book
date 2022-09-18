package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.ex2.entity.Memo;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoTxt("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    /**
     * @title select
     * findById -> 메소드 실행 순간 SQL 처리
     * */
    @Test
    public void testSelect() {
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("==================================");
        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    /**
     * getOne() : 객체를 사용하는 순간 SQL처리
     * but, deprecated method
     * */
//    @Test
//    @Transactional
//    public void testSelect2(){
//        Long mno = 100L;
//        Memo memo = memoRepository.getOne(mno);
//        System.out.println("==================================");
//        System.out.println(memo);
//    }

    /**
     * @title 수정
     * JPA는 엔티티 객체들을 메모리상에 보관하려 하기에, 특정한 엔티티 객체가 존재하는지 확인하는 select 이후, update
     * */
    @Test
    public void testUpdate() {
        Memo memo = Memo.builder().mno(100L).memoTxt("Update Test").build();

        System.out.println(memoRepository.save(memo));
    }

    /**
     * @title 삭제
     * 마찬가지로 select -> delete
     * 주의** 데이터가 존재하지 않으면, EmptyResultDataAccessException 발생
     * */
    @Test
    public void testDelete() {
        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    /**
     * @title 페이징
     * */
    @Test
    public void testPageDefault() {
        //1페이지 10개
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("------------------------------------");

        System.out.println("Total Pages : " + result.getTotalPages());  //총 몇 페이지

        System.out.println("Total count : " + result.getTotalElements()); //총 row 수

        System.out.println("Page Number : " + result.getNumber()); //현재 페이지 번호

        System.out.println("Page Size : " + result.getSize()); //페이지 당 row 수

        System.out.println("has next page? : " + result.hasNext()); //다음 페이지 유무

        System.out.println("is first page? : " + result.isFirst()); //현재 페이지가 첫 페이지 여부

        System.out.println("is last page? : " + result.isLast()); //현재 페이지가 마지막 페이지 여부

        System.out.println("------------------------------------");

        for(Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();

        Pageable pageable = PageRequest.of(1, 10, sort1);
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });

        //다중 order by
        Sort sort2 = Sort.by("memoTxt").ascending();
        pageable = PageRequest.of(0, 10, sort1.and(sort2));

    }

    @Test
    public void testQueryMethod(){

        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for(Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(memo -> System.out.println(memo));
    }

    @Test
    @Transactional
    @Commit
    public void testDeleteQueryMethod() {

        //실제로 사용하지 않는 편
        //삭제 대상이 10개의 row라면, 10회 select, delete 진행하기 때문 -> @Query annotation
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

    @Test
    public void testQueryAnnotation() {

        List<Memo> list = memoRepository.getListDesc();

        for(Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryAnnotationWithParams() {
        memoRepository.updateMemoText(1L, "업데이트 테스트");
    }

    @Test
    public void testQueryAnnotationWithObjectParam() {

        Memo memo = new Memo();
        memo.setMno(2L);
        memo.setMemoTxt("memo txt 2");
        memoRepository.updateMemoText(memo);
    }

    @Test
    public void getListWithQuery() {

        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> list = memoRepository.getListWithQuery(10L, pageable);

//        for(Memo memo : list) {
//            System.out.println(memo);
//        }

        list.get().forEach(memo -> System.out.println(memo));
    }

    @Test
    public void getListWithQueryObject() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Object[]> list = memoRepository.getListWithQueryObject(10L, pageable);

        list.get().forEach(objects -> System.out.println(Arrays.toString(objects)));

    }

    @Test
    public void getNativeResult() {

        List<Object[]> list = memoRepository.getNativeResult(97L);

        for(Object[] objs : list) {
            System.out.println(Arrays.toString(objs));
        }
    }
}
