package com.matdongsan.domain.member;

import com.matdongsan.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByAccount(Account account);

    boolean existsByNickname(String nickname);

    Optional<Member> findByNickname(String nickname);

}
