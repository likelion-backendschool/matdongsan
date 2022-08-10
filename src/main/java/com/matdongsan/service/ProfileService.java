package com.matdongsan.service;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @Slf4j
@RequiredArgsConstructor
public class ProfileService {

    private final MemberRepository memberRepository;

    public Member findUserInfoByUserName(String username) {
        Optional<Member> currentMember = memberRepository.findByUsername(username);
        return currentMember.orElse(null);
    }
}
