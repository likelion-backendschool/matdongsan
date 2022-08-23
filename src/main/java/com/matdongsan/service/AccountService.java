package com.matdongsan.service;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AccountRepository;
import com.matdongsan.domain.account.AccountRole;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.infra.SecurityUser;
import com.matdongsan.web.dto.account.AccountSignUpDto;
import com.matdongsan.web.dto.member.MemberInfoDto;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 새로운 Account 객체를 생성하여 DB에 저장하는 메소드
     * @param accountSignUpDto 회원가입 폼에서 받아온 DTO가 들어옴
     * @returns 들어온 DTO를 바탕으로 새로운 Account 객체를 생성하여 반환
     */
    public Account saveNewAccount(AccountSignUpDto accountSignUpDto) {
        // 회원가입한 Member를 저장하는 로직
        Account newAccount = Account.builder()
                .username(accountSignUpDto.getUsername())
                .password(passwordEncoder.encode(accountSignUpDto.getPassword()))
                .email(accountSignUpDto.getEmail())
                .accountRole(AccountRole.ROLE_USER)
                .build();
        MemberInfoDto emptyDto = memberService.createEmptyMemberInfoDto();
        memberService.createNewMember(newAccount, emptyDto);
        return accountRepository.save(newAccount);
    }

    /**
     * Account 객체를 받아 로그인을 시켜주는 메소드
     * @param account 회원가입 시 생긴 Account 객체가 들어옴
     * @returns 들어온 Account 객체를 Security를 이용하여 로그인 시켜줌
     */
    public void login(Account account) {
        // Security를 이용하여 member를 로그인 시켜줌(회원가입 시에만 사용)
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new SecurityUser(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    /**
     * username을 통해 Account 객체를 찾는 메소드
     * @param username 로그인 id가 들어옴
     * @returns 해당하는 유저가 있는지 찾고 없다면 null을 반환
     */
    public Account findAccountByUsername(String username){
        Optional<Account> currentMember = accountRepository.findByUsername(username);
        return currentMember.orElse(null);
    }

    public boolean checkDuplicatedAccount(String username) {
        return accountRepository.existsByUsername(username);
    }

    /**
     * 회원가입한 정보를 받아서 username 혹은 email이 중복으로 존재하는지 확인하는 메소드
     * @param accountSignUpDto 회원가입 시 폼을 받아 전달하는 DTO
     * @returns username 혹은 email 둘 중에 하나라도 존재하는지 반환
     */
    public boolean existMemberCheck(AccountSignUpDto accountSignUpDto) {
        // 이미 존재하는 username 혹은 email 인지 확인하는 폼
        boolean existUsername = accountRepository.existsByUsername(accountSignUpDto.getUsername());
        boolean existEmail = accountRepository.existsByEmail(accountSignUpDto.getEmail());
        return existUsername || existEmail;
    }

    /**
     * username을 받아 MemberVO 객체로 반환하는 메소드
     * @param username 로그인 id가 들어옴
     * @returns Member가 아닌 VO 객체를 반환함
     */
    public MemberVo getReadOnlyMember(String username) {
        Member currentMember = findAccountByUsername(username).getMember();
        log.info("currentUser.introduce={}", currentMember.getIntroduce());
        return MemberVo.builder()
                .introduce(currentMember.getIntroduce())
                .birth(currentMember.getBirth())
                .gender(currentMember.getGender())
                .postsList(currentMember.getPostsList())
                .build();
    }

    /**
     * Spring Security에서만 사용하는 메소드
     * @param username 로그인 id가 들어옴
     * @returns 새로운 SecurityUser를 생성해줌
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인을 하기 위해 가입된 member 정보를 조회
        // UserDetailsService 구현

        Optional<Account> account = accountRepository.findByUsername(username);

        if (account.isEmpty()) {
            throw new UsernameNotFoundException(username);
        } else {
            return new SecurityUser(account.get());
        }
    }
}
