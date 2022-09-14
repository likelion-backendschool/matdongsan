package com.matdongsan.domain.reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Page<Reply> findAll(Pageable pageable);

    Page<Reply> findRepliesByPostId(Long id, Pageable pageable);
}
