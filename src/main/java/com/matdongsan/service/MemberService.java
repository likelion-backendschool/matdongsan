package com.matdongsan.service;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AccountRepository;
import com.matdongsan.domain.account.AccountRole;
import com.matdongsan.infra.SecurityUser;
import com.matdongsan.web.dto.member.MemberSignUpDto;
import com.matdongsan.web.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account saveNewMember(MemberSignUpDto memberSignUpDto) {
        // 회원가입한 Member를 저장하는 로직
        Account newAccount = Account.builder()
                .username(memberSignUpDto.getUsername())
                .password(passwordEncoder.encode(memberSignUpDto.getPassword()))
                .email(memberSignUpDto.getEmail())
                .birth(memberSignUpDto.getBirth())
                .gender(memberSignUpDto.getGender())
                .signUpDate(LocalDateTime.now())
                .accountRole(AccountRole.ROLE_USER)
                .build();
        return accountRepository.save(newAccount);
    }

    public void login(Account account) {
        // Security를 이용하여 member를 로그인 시켜줌(회원가입 시에만 사용)
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new SecurityUser(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public Account findMemberByUsername(String username){
        Optional<Account> currentMember = accountRepository.findByUsername(username);
        return currentMember.orElseThrow(NoSuchElementException::new);
    }

    public boolean existMemberCheck(MemberSignUpDto memberSignUpDto) {
        // 이미 존재하는 username 혹은 email 인지 확인하는 폼
        boolean existUsername = accountRepository.existsByUsername(memberSignUpDto.getUsername());
        boolean existEmail = accountRepository.existsByEmail(memberSignUpDto.getEmail());
        return existUsername || existEmail;
    }

    public MemberVo getReadOnlyMember(String username) {
        Account account = findMemberByUsername(username);
        return MemberVo.builder()
                .username(account.getUsername())
                .email(account.getEmail())
                .introduce(account.getIntroduce())
                .birth(account.getBirth())
                .signUpDate(account.getSignUpDate())
                .gender(account.getGender())
                .postsList(account.getPostsList())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인을 하기 위해 가입된 member 정보를 조회
        // UserDetailsService 구현

        Optional<Account> member = accountRepository.findByUsername(username);

        if (member.isEmpty()) {
            throw new UsernameNotFoundException(username);
        } else {
            return new SecurityUser(member.get());
        }
    }

}
