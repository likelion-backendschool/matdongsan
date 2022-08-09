package com.matdongsan;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.domain.member.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void dataInit() {
        if (memberRepository.findByUsername("member1").isEmpty()) {
            memberRepository.save(new Member(null, "member1", passwordEncoder.encode("member1"), "member1.gmail.com", "male", LocalDateTime.now(), MemberRole.ROLE_USER));
        }
    }
}
