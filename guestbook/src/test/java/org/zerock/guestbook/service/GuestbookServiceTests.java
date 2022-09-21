package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.repository.GuestbookRepository;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService guestbookService;

    @Test
    void testRegister() {

        GuestbookDTO dto = GuestbookDTO.builder()
                .title("regist test title")
                .content("regist test content just do it")
                .writer("user123")
                .build();

        System.out.println(guestbookService.register(dto));
    }
}
