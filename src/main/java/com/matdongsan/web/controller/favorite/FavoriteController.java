package com.matdongsan.web.controller.favorite;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
import com.matdongsan.domain.favorite.Favorite;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import com.matdongsan.service.FavoriteService;
import com.matdongsan.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final PlaceService placeService;

    /**
     *
     * @param account
     * @param subject
     * @return
     */
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
     * @param favoriteId
     * @param placeId
     * @return
     */
    @GetMapping("/favorite/changeFavorite/{favoriteId}/{placeId}")
    public String changeFavorite(@AuthUser Account account,@PathVariable("favoriteId") Long favoriteId, @PathVariable("placeId") long placeId) {
        Member member = account.getMember();
        Place place = placeService.findPlace(placeId);
        Favorite favorite = favoriteService.findById(favoriteId);
        favoriteService.replaceExistPlace(member, place, favorite);

        return "redirect:/profile/bookmark";
    }

    /**
     *
     * @param account
     * @param favoriteId
     * @param subject
     * @return
     */
    @PostMapping("/favorite/modifyFavorite/{favoriteId}")
    public String modifyFavorite(@AuthUser Account account, @PathVariable Long favoriteId, @PathParam("subject") String subject) {
        Member member = account.getMember();
        Favorite favorite = favoriteService.findByIdAndMember(favoriteId, member);
        favorite.setSubject(subject);
        favoriteService.save(favorite);
        return "redirect:/profile/bookmark";
    }

    /**
     *
     * @param account
     * @param favoriteId
     * @return
     */
    @GetMapping("/favorite/deleteFavorite/{favoriteId}")
    public String deleteFavorite(@AuthUser Account account, @PathVariable Long favoriteId) {
        Member member = account.getMember();
        Favorite favorite = favoriteService.findByIdAndMember(favoriteId, member);
        favoriteService.delete(favorite);
        return "redirect:/profile/bookmark";
    }

    @GetMapping("/favorite/{favoriteId}/deletePlace/{placeId}")
    public String deletePlace(@AuthUser Account account, @PathVariable Long favoriteId, @PathVariable Long placeId) {
        Member member = account.getMember();
        Favorite favorite = favoriteService.findByIdAndMember(favoriteId, member);
        favorite.getPlaceList().remove(placeService.findPlace(placeId));
        favoriteService.save(favorite);
        return "redirect:/profile/bookmark";
    }
}
