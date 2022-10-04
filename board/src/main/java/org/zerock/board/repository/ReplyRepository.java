package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying //JPQL을 이용해서 Update, Delete 하려면 Modifying 어노테이션 추가해야 함
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(Long bno);
}
