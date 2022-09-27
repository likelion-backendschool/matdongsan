package com.matdongsan.web.controller.account;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
import com.matdongsan.domain.account.LoginType;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberAge;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.MemberService;
import com.matdongsan.service.SocialLoginApiService;
import com.matdongsan.web.dto.account.AccountLoginDto;
import com.matdongsan.web.dto.account.AccountSignUpDto;
import com.matdongsan.web.dto.member.MemberInfoDto;
import com.matdongsan.web.dto.profile.ProfileWithdrawalDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final MemberService memberService;

    private final SocialLoginApiService loginApiService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        log.info("=========== check login ================");
        // 로그인할 때 사용할 Dto 전달
        model.addAttribute("accountLoginDto", new AccountLoginDto());
        return "account/account-login";
    }

    @GetMapping("/account/kakao")
    public String kakaoLogin(@RequestParam String code) {
        log.info("kakaoLoginCode={}", code);
        String access_Token = loginApiService.getKakaoAccessToken(code);
        log.info("access_Token={}", access_Token);
        Account currentAccount = accountService.createKakaoUser(access_Token);
        accountService.login(currentAccount);
        if (currentAccount.getMember().getIntroduce() == null) {
            return "redirect:/info-init";
        }
        return "redirect:/";
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
        Account account = accountService.saveNewAccount(accountSignUpDto, LoginType.LOCAL);
        accountService.login(account);
        return "redirect:/info-init";
    }

    @ResponseBody
    @RequestMapping(value = "/account/idCheck")
    public boolean overlappedID(@RequestParam(value = "username") String username) {
        boolean flag = false;
        if (accountService.checkDuplicatedAccount(username)) {
            flag = true;
        }
        return flag;
    }

    @GetMapping("/info-init")
    public String memberInformationInit(Principal principal, Model model) {
        Account principalAccount = accountService.findAccountByUsername(principal.getName());
        if (principalAccount == null) {
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

    @PostMapping("/withdrawal")
    public String accountWithdrawal(@AuthUser Account account, ProfileWithdrawalDto dto, Model model) {
        if (!accountService.checkAccountPassword(dto.getPassword(), account)) {
            model.addAttribute("profileWithdrawalDto", dto);
            return "profile/profile-setting";
        }
        accountService.withdrawalAccount(account);
        return "redirect:/logout";
    }
}
