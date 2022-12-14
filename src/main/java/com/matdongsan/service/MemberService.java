package com.matdongsan.service;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.infra.security.SecurityUser;
import com.matdongsan.web.dto.member.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .nickname(account.getUsername())
                .signUpDate(LocalDateTime.now())
                .introduce(memberInfoDto.getIntroduce())
                .gender(memberInfoDto.getGender())
                .birth(memberInfoDto.getBirth())
                .build();
        account.addMember(newMember);
        log.info("newMember={}", newMember);
        return memberRepository.save(newMember);
    }

    public void updateCurrentMember(Member member, MemberInfoDto memberInfoDto) {
        member.addBasicInfo(memberInfoDto.getBirth(),
                memberInfoDto.getGender(),
                memberInfoDto.getIntroduce(),
                memberInfoDto.getMemberAge());
    }

    public MemberInfoDto createEmptyMemberInfoDto() {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setBirth(null);
        memberInfoDto.setGender(null);
        memberInfoDto.setIntroduce(null);
        return memberInfoDto;
    }

    public void changeMemberIntroduce(Member member, String introduce) {
        member.changeBasicInfo(introduce);
        memberRepository.save(member);
    }

    public boolean existMemberNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public Member findMember(long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    public void changeMemberNickname(String nickname, Account account) {
        Member currentMember = account.getMember();
        currentMember.setNickname(nickname);
        memberRepository.save(currentMember);
        forceAuthentication(account);
    }

    public void forceAuthentication (Account account) {
        SecurityUser securityUser = new SecurityUser(account);

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        securityUser,
                        null,
                        securityUser.getAuthorities()
                );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }
}
