package com.matdongsan.web.controller;

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

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("memberLoginDto", new MemberLoginDto());
        return "member/member-login";
    }

    @GetMapping("/signup")
    public String showSignUpPage(Model model) {
        model.addAttribute("memberSignUpDto", new MemberSignUpDto());
        return "member/member-signup";
    }

    @PostMapping("/signup")
    public String createNewMember(@Valid MemberSignUpDto memberSignUpDto, Errors errors) {
        if (errors.hasErrors()) {
            return "member/member-signup";
        }
        log.info("memberSignUpDto={}", memberSignUpDto);
        Member member = memberService.saveNewMember(memberSignUpDto);
        memberService.login(member);
        return "redirect:/";
    }
}
