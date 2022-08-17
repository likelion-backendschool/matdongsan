package com.matdongsan;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AccountRepository;
import com.matdongsan.domain.account.AccountRole;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TestDataInit {
// 처음 프로젝트를 실행할 때만 작성되는 class

    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void memberDataInit() {
        if (accountRepository.findByUsername("member1").isEmpty()) {
            // 등록된 username 중 member1이 없다면 새로운 member 등록
            // 빌드 이용
            Account newAccount = accountRepository.save(Account.builder()
                    .username("member1")
                    .password(passwordEncoder.encode("member1!"))
                    .email("member1@gmail.com")
                    .build());
            memberRepository.save(Member.builder()
                    .introduce("hello world")
                    .gender("male")
                    .account(newAccount)
                    .birth(Date.from(LocalDateTime.now().minusDays(10).atZone(ZoneId.systemDefault()).toInstant()))
                    .signUpDate(LocalDateTime.now())
                    .build());
        }
    }
}
