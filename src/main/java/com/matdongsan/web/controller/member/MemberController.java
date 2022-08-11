package com.matdongsan.web.controller.member;

import com.matdongsan.domain.member.Member;
import com.matdongsan.service.MemberService;
import com.matdongsan.web.dto.member.MemberLoginDto;
import com.matdongsan.web.dto.member.MemberSignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        // 로그인할 때 사용할 Dto 전달
        model.addAttribute("memberLoginDto", new MemberLoginDto());
        return "member/member-login";
    }

    @GetMapping("/signup")
    public String showSignUpPage(Model model) {
        // 회원가입 시 사용할 Dto 전달
        model.addAttribute("memberSignUpDto", new MemberSignUpDto());
        return "member/member-signup";
    }

    @PostMapping("/signup")
    public String createNewMember(@Valid MemberSignUpDto memberSignUpDto, Errors errors, Model model) {
        // 주석 추가2
        if (errors.hasErrors() || memberService.existMemberCheck(memberSignUpDto)) {
            // DTO에 작성한 Valid에 맞지 않거나, 이미 존재하는 username 혹은 email일 경우
            // 해당 내용을 다시 dto에 담아서 회원가입 폼으로 돌려줌
            model.addAttribute("memberSignUpDto", memberSignUpDto);
            return "member/member-signup";
        }
        // 아무 이상 없다면 로그인을 진행하고, 메인 페이지로 보내준다.
        log.info("memberSignUpDto={}", memberSignUpDto);
        Member member = memberService.saveNewMember(memberSignUpDto);
        memberService.login(member);
        return "redirect:/";
    }
}
