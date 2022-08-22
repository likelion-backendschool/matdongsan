package com.matdongsan.domain.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByPostId(Long id);


}
