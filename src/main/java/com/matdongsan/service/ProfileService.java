package com.matdongsan.service;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AccountRepository;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.web.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @Slf4j
@RequiredArgsConstructor
public class ProfileService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;

    public Account findUserInfoByUserName(String username) {
        Optional<Account> currentMember = accountRepository.findByUsername(username);
        return currentMember.orElse(null);
    }

    public Optional<MemberVo> findByUsernameOrNickname(String nickname) {
        return Optional.of(accountService.getReadOnlyMember(memberRepository.findByNickname(nickname).get().getNickname()));
    }
}
