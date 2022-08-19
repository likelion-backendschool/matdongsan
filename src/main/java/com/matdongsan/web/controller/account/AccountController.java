package com.matdongsan.web.controller.account;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberAge;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (bindingResult.hasErrors() || accountService.existMemberCheck(accountSignUpDto)) {
            // DTO에 작성한 Valid에 맞지 않거나, 이미 존재하는 username 혹은 email일 경우
            // 해당 내용을 다시 dto에 담아서 회원가입 폼으로 돌려줌
            model.addAttribute("accountSignUpDto", accountSignUpDto);
            return "account/account-signup";
        }
        // 아무 이상 없다면 로그인을 진행하고, 유저 프로필 작성 폼으로 넘어간다.
        log.info("accountSignUpDto={}", accountSignUpDto);
        Account account = accountService.saveNewAccount(accountSignUpDto);
        accountService.login(account);
        return "redirect:/info-init";
    }

    @ResponseBody
    @RequestMapping(value = "/account/idCheck")
    public boolean overlappedID(@RequestParam(value = "username") String username){
        boolean flag = false;
        if (accountService.checkDuplicatedAccount(username)) {
            flag = true;
        }
        return flag;
    }

    @GetMapping("/info-init")
    public String memberInformationInit(Principal principal, Model model) {
        Account account = accountService.findAccountByUsername(principal.getName());
        if (account == null) {
            return "redirect:/";
        }
        model.addAttribute("ages", MemberAge.values());
        model.addAttribute("memberInfoDto", new MemberInfoDto());
        return "account/member-info-init";
    }

    @PostMapping("/info-init")
    public String createNewMember(MemberInfoDto memberInfoDto,
                                  Principal principal, Model model) {
        Account account = accountService.findAccountByUsername(principal.getName());
        Member currentMember = account.getMember();
        memberService.updateCurrentMember(currentMember, memberInfoDto);
        return "redirect:/";
    }
}
