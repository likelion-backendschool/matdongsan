package com.matdongsan.service;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.domain.member.MemberRole;
import com.matdongsan.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void saveNewMember(MemberDto memberDto) {
        Member newMember = Member.builder()
                .username(memberDto.getLoginId())
                .password(memberDto.getPassword())
                .nickname(memberDto.getNickname())
                .signUpDate(LocalDateTime.now())
                .memberRole(MemberRole.ROLE_USER)
                .build();
        memberRepository.save(newMember);
    }

}
