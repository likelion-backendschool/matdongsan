package com.matdongsan.web.controller.account;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.MemberService;
import com.matdongsan.service.ProfileService;
import com.matdongsan.web.dto.profile.ProfilePasswordDto;
import com.matdongsan.web.vo.MemberVo;
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
public class ProfileController {

    private final ProfileService profileService;
    private final AccountService accountService;
    private final MemberService memberService;

    @GetMapping("/profile")
    public String showMyProfile(Principal principal, Model model) {
        log.info("principal.getName()={}", principal.getName());
        MemberVo member = accountService.getReadOnlyMember(principal.getName());
        model.addAttribute("member", member);

        return "profile/profile-main";
    }

    @GetMapping("/profile/setting")
    public String showProfilePage(@AuthUser Account account, Model model) {
        log.info("account.getUsername()={}", account.getUsername());

        MemberVo member = accountService.getReadOnlyMember(account.getUsername());
        model.addAttribute("member", member);
        model.addAttribute("profilePasswordDto", new ProfilePasswordDto());

        return "profile/profile-setting";
    }

    @ResponseBody
    @RequestMapping(value = "/profile/nicknameCheck")
    public boolean overlappedID(@RequestParam(value = "nickname") String nickname){
        boolean flag = false;

        if (accountService.checkDuplicatedAccount(nickname) && memberService.existMemberNickname(nickname)) {
            flag = true;
        }

        return flag;
    }

    @PostMapping("/profile/change/nickname")
    public String changeNickname(@ModelAttribute(value = "nickname") String nickname, @AuthUser Account account, RedirectAttributes redirectAttributes) {
        memberService.changeMemberNickname(nickname, account);
        redirectAttributes.addFlashAttribute("settingMessageSuccess", "닉네임이 변경되었습니다.");
        return "redirect:/profile/setting";
    }

    @PostMapping("/profile/change/password")
    public String changePassword(@Valid ProfilePasswordDto profilePasswordDto, BindingResult bindingResult, @AuthUser Account account, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !accountService.checkAccountPassword(profilePasswordDto.getOriginalPassword(), account)) {
            redirectAttributes.addFlashAttribute("profilePasswordDto", profilePasswordDto);
            redirectAttributes.addFlashAttribute("settingMessageError", "비밀번호를 확인해주세요.");
            return "redirect:/profile/setting";
        }
        redirectAttributes.addFlashAttribute("settingMessageSuccess", "비밀번호가 변경되었습니다.");
        accountService.changeAccountPassword(profilePasswordDto.getNewPassword(), account);

        return "redirect:/profile/setting";
    }

}
