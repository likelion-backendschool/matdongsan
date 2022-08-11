package com.matdongsan;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.domain.member.MemberRole;
import com.matdongsan.domain.posts.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TestDataInit {
// 처음 프로젝트를 실행할 때만 작성되는 class

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void memberDataInit() {
        if (memberRepository.findByUsername("member1").isEmpty()) {
            // 등록된 username 중 member1이 없다면 새로운 member 등록
            // 빌드 이용
            memberRepository.save(Member.builder()
                    .username("member1")
                    .password(passwordEncoder.encode("member1!"))
                    .email("member1@gmail.com")
                    .gender("male")
                    .birth(Date.from(LocalDateTime.now().minusDays(10).atZone(ZoneId.systemDefault()).toInstant()))
                    .signUpDate(LocalDateTime.now())
                    .memberRole(MemberRole.ROLE_USER)
                    .build());
        }
    }
}
