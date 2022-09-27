package com.matdongsan.web.controller.account;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
import com.matdongsan.domain.favorite.Favorite;
import com.matdongsan.domain.member.Member;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.FavoriteService;
import com.matdongsan.service.MemberService;
import com.matdongsan.service.ProfileService;
import com.matdongsan.web.dto.profile.ProfileIntroduceDto;
import com.matdongsan.web.dto.profile.ProfilePasswordDto;
import com.matdongsan.web.dto.profile.ProfileWithdrawalDto;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;
    private final AccountService accountService;
    private final MemberService memberService;
    private final FavoriteService favoriteService;

    @GetMapping("/profile/{username}")
    public String showProfilePage(@PathVariable String username, Model model, @AuthUser Account account) {
        MemberVo member = profileService.findByUsernameOrNickname(username);
        if (member == null) {
            MemberVo principalMember = accountService.getReadOnlyMember(account.getUsername());
            model.addAttribute("member", principalMember);
            return "profile/profile-main";
        }
        model.addAttribute("member", member);

        return "profile/profile-main";
    }

    @GetMapping("/settings/profile")
    public String showProfileSettingPage(@AuthUser Account account, Model model) {
        log.info("account.getUsername()={}", account.getUsername());

        MemberVo member = accountService.getReadOnlyMember(account.getUsername());
        model.addAttribute("member", member);
        model.addAttribute("profilePasswordDto", new ProfilePasswordDto());
        model.addAttribute("profileWithdrawalDto", new ProfileWithdrawalDto());
        model.addAttribute("profileIntroduceDto", new ProfileIntroduceDto());

        return "profile/profile-setting";
    }

    @ResponseBody
    @RequestMapping(value = "/settings/profile/nicknameCheck")
    public boolean overlappedID(@RequestParam(value = "nickname") String nickname) {
        boolean flag = false;

        if (accountService.checkDuplicatedAccount(nickname) && memberService.existMemberNickname(nickname)) {
            flag = true;
        }

        return flag;
    }

    @PostMapping("/settings/profile/change/nickname")
    public String changeNickname(@ModelAttribute(value = "nickname") String nickname, @AuthUser Account account, RedirectAttributes redirectAttributes) {
        memberService.changeMemberNickname(nickname, account);
        redirectAttributes.addFlashAttribute("settingMessageSuccess", "닉네임이 변경되었습니다.");
        return "redirect:/settings/profile";
    }

    @PostMapping("/settings/profile/change/introduce")
    public String changeIntroduce(@Valid ProfileIntroduceDto profileIntroduceDto, BindingResult bindingResult, @AuthUser Account account, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("settingMessageError", "소개글은 4글자 이상 25글자 이내로 작성해주세요.");
            redirectAttributes.addFlashAttribute("profileIntroduceDto", profileIntroduceDto);
            return "redirect:/settings/profile";
        }
        memberService.changeMemberIntroduce(account.getMember(), profileIntroduceDto.getIntroduce());
        redirectAttributes.addFlashAttribute("settingMessageSuccess", "소개글이 변경되었습니다.");
        return "redirect:/settings/profile";
    }

    @PostMapping("/settings/profile/change/password")
    public String changePassword(@Valid ProfilePasswordDto profilePasswordDto, BindingResult bindingResult, @AuthUser Account account, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !accountService.checkAccountPassword(profilePasswordDto.getOriginalPassword(), account)) {
            redirectAttributes.addFlashAttribute("profilePasswordDto", profilePasswordDto);
            redirectAttributes.addFlashAttribute("settingMessageError", "비밀번호를 확인해주세요.");
            return "redirect:/settings/profile";
        }
        redirectAttributes.addFlashAttribute("settingMessageSuccess", "비밀번호가 변경되었습니다.");
        accountService.changeAccountPassword(profilePasswordDto.getNewPassword(), account);

        return "redirect:/settings/profile";
    }

    /**
     * 프로필의 북마크 관리 페이지
     * @param model
     * @param account
     * @param principal
     * @return
     */
    @GetMapping("/profile/bookmark/view")
    public String showBookmark(Model model, @AuthUser Account account, Principal principal) {
        Optional<Favorite> optionalFavorite = Optional.ofNullable(favoriteService.findTopByMember(account.getMember()));
        if (optionalFavorite.isPresent()) {
            List<Favorite> favoriteList = favoriteService.findAllByMember(account.getMember());
            model.addAttribute("favorites", favoriteList);
        } else {
            Favorite favorite = new Favorite(account.getMember(), "나만의 맛집");
            favoriteService.save(favorite);
            model.addAttribute("favorites", favorite);
        }
        MemberVo memberVo = accountService.getReadOnlyMember(principal.getName());
        model.addAttribute("member", memberVo);

        return "/profile/profile-bookmark";
    }

    @GetMapping("/profile/map/view")
    public String showMyMap(@AuthUser Account account, Model model, Principal principal) {
        MemberVo memberVo = accountService.getReadOnlyMember(principal.getName());
        model.addAttribute("member", memberVo);
        return "profile/profile-map";
    }

    @PostMapping("/profile/bookmark/favorite/subjectCheck")
    @ResponseBody
    public boolean subjectCheck(@AuthUser Account account, @RequestBody String bookmarkSubject) {
        Member member = account.getMember();
        boolean check = false;
        List<Favorite> favoriteList = favoriteService.findAllByMember(member);
        for (Favorite favorite : favoriteList) {
            log.info("favorite.subject->{}", favorite.getSubject());
            log.info("bookmark.subject->{}", bookmarkSubject);
            log.info("true->{}", bookmarkSubject.equals(favorite.getSubject()));
            if (bookmarkSubject.equals(favorite.getSubject())){
                check = true;
            }
        }
        return check;
    }
}
