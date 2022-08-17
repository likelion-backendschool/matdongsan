package com.matdongsan.service;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.web.dto.member.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    public Member createNewMember(Account account, MemberInfoDto memberInfoDto) {

        Member newMember = Member.builder()
                        .account(account)
                        .signUpDate(LocalDateTime.now())
                        .introduce(memberInfoDto.getIntroduce())
                        .gender(memberInfoDto.getGender())
                        .birth(memberInfoDto.getBirth())
                .build();
        account.addMember(newMember);
        log.info("newMember={}", newMember);
        return memberRepository.save(newMember);
    }
}
