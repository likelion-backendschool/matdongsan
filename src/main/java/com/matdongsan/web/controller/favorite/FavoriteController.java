package com.matdongsan.web.controller.favorite;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.favorite.Favorite;
import com.matdongsan.domain.member.Member;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final AccountService accountService;


    /**
     * favorite 뷰 이동
     * @param model
     * @param principal
     * @return "favorites/favorite-list"
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("favorite/list")
    public String showFavorite(Model model, Principal principal) {
        Account account = accountService.findAccountByUsername(principal.getName());
        Member member = account.getMember();

        List<Favorite> favorites = favoriteService.findAllByMember(member);
        model.addAttribute("favorites",favorites);

        return "favorites/favorite-list";
    }
}
