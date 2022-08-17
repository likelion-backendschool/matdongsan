package com.matdongsan.web.controller.account;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.member.Member;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.MemberService;
import com.matdongsan.web.dto.account.AccountLoginDto;
import com.matdongsan.web.dto.account.AccountSignUpDto;
import com.matdongsan.web.dto.member.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final MemberService memberService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        // 로그인할 때 사용할 Dto 전달
        model.addAttribute("accountLoginDto", new AccountLoginDto());
        return "account/account-login";
    }

    @GetMapping("/signup")
    public String showSignUpPage(Model model) {
        // 회원가입 시 사용할 Dto 전달
        model.addAttribute("accountSignUpDto", new AccountSignUpDto());
        return "account/account-signup";
    }

    @PostMapping("/signup")
    public String createNewMember(@Valid AccountSignUpDto accountSignUpDto,
                                  BindingResult bindingResult, Model model) {

        // 주석 추가2
        if (bindingResult.hasErrors() || accountService.existMemberCheck(accountSignUpDto)) {
            // DTO에 작성한 Valid에 맞지 않거나, 이미 존재하는 username 혹은 email일 경우
            // 해당 내용을 다시 dto에 담아서 회원가입 폼으로 돌려줌
            model.addAttribute("memberSignUpDto", accountSignUpDto);
            return "account/account-signup";
        }
        // 아무 이상 없다면 로그인을 진행하고, 메인 페이지로 보내준다.
        log.info("accountSignUpDto={}", accountSignUpDto);
        Account account = accountService.saveNewMember(accountSignUpDto);
        accountService.login(account);
        return "redirect:/info-init";
    }

    @GetMapping("/info-init")
    public String memberInformationInit(Principal principal, Model model) {
        Account account = accountService.findMemberByUsername(principal.getName());
        if (account == null) {
            return "redirect:/";
        }
        model.addAttribute("memberInfoDto", new MemberInfoDto());
        return "account/member-info-init";
    }

    @PostMapping("/info-init")
    public String createNewMember(MemberInfoDto memberInfoDto,
                                  Principal principal, Model model) {
        Account account = accountService.findMemberByUsername(principal.getName());
        Member newMember = memberService.createNewMember(account, memberInfoDto);
        model.addAttribute("memberInfoDto", new MemberInfoDto());
        return "redirect:/";
    }
}
