package com.matdongsan.web.controller.favorite;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
import com.matdongsan.domain.favorite.Favorite;
import com.matdongsan.domain.member.Member;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.FavoriteService;
import com.matdongsan.service.PlaceService;
import com.matdongsan.web.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    /**
     *
     * @param account
     * @param subject
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/favorite/addFavorite")
    public String addFavorite(@AuthUser Account account, @PathParam("subject") String subject) {
        Member member = account.getMember();

        Favorite favorite = new Favorite(member, subject);
        favoriteService.save(favorite);
        return "redirect:/profile/bookmark";
    }

    /**
     *
     * @param account
     * @param placeId
     * @return
     */
   /* @PreAuthorize("isAuthenticated()")
    @GetMapping("/favorite/{placeId}/delete")
    public String deletePlace(@AuthUser Account account, @PathVariable("placeId") Long placeId) {
        Member member = account.getMember();

        Place place = placeService.findPlace(placeId);

        favoriteService.doFavorite(member,place);

        return "redirect:/favorite/list";
    }*/
}
