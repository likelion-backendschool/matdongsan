package com.matdongsan.domain.member;

import com.matdongsan.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByAccount(Account account);

}
