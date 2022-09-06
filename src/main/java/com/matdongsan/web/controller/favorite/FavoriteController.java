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
        log.info("favoriteId={}", favoriteId);
        log.info("placeId={}", placeId);

        Member member = account.getMember();
        /* 동일한 멤버에 favorite들 중에서 place가 있는지*/
//        favoriteService.findAllByMember(member);
        Place place = placeService.findPlace(placeId);
        log.info("placeName={}", place.getPlaceName());
        Favorite favorite = favoriteService.findById(favoriteId);
        log.info("favoriteSubject={}", favorite.getSubject());
        favoriteService.replaceExistPlace(member, place, favorite);

        return "redirect:/profile/bookmark";
    }
}
