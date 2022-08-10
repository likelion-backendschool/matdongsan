package com.matdongsan;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.domain.member.MemberRole;
import com.matdongsan.domain.posts.PostsRepository;
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
            memberRepository.save(Member.builder()
                    .username("member1")
                    .password(passwordEncoder.encode("member1!"))
                    .email("member1@gmail.com")
                    .gender("male")
                    .signUpDate(LocalDateTime.now())
                    .memberRole(MemberRole.ROLE_USER)
                    .build());
        }
    }
}
