package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1, 300).forEach(i -> {

            Guestbook guestbook = Guestbook.builder()
                    .title("Title..." + i)
                    .content("Content Con to the tent" + i)
                    .writer("Writer[" + i % 10 + "]")
                    .build();

            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest() {
        //BaseEntity의 modDate 업데이트 확인
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if(result.isPresent()) {

            Guestbook guestbook = result.get();

            guestbook.changeTitle("타이틀 변경 테스트");
            guestbook.changeContent("컨텐츠 변경 테스트");

            guestbookRepository.save(guestbook);
        }
    }

    @Test
    void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook; //Q도메인 클래스 활용 -> 기존 엔티티 클래스의 필드 사용 가능
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder(); //BooleanBuilder -> where문에 들어가는 조건 설정하는 컨테이너

        BooleanExpression expression = qGuestbook.title.contains(keyword); //원하는 조건을 필드 값과 결합하여 생성(com.querydsl.core.types.Predicate)

        builder.and(expression); //where절의 and, or 등 적용

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable); //BooleanBuilder는 GuestbookRepository에 추가된 QuerydslPredicateExcutor 인터페이스의 메소드 사용 가능

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });

    }

    @Test
    void testQuery2() {

        // or 조건으로 연결되는 예제

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expTitle = qGuestbook.title.contains(keyword);
        BooleanExpression expContent = qGuestbook.content.contains(keyword);
        BooleanExpression expAll = expTitle.or(expContent);

        builder.and(expAll);
        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}
