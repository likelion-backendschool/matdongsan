package com.matdongsan.web.controller.place;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.FavoriteService;
import com.matdongsan.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/place")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {

    private final PlaceService placeService;
    private final FavoriteService favoriteService;
    private final AccountService accountService;

    @GetMapping("/map")
    public String showMap(){
        return "place/place-map";
    }

    @GetMapping("/{placeId}/detail")
    public String showPlaceDetail(Principal principal,@PathVariable("placeId") long placeId, Model model) {
        Account account = accountService.findAccountByUsername(principal.getName());
        Member member = account.getMember();
        Place place = placeService.findPlace(placeId);
        model.addAttribute("place", place);
        model.addAttribute("isFavorite", favoriteService.existFavorite(member, place));
        model.addAttribute("favoriteCount", favoriteService.countByPlace(place));

        return "place/place-detail";
    }





}
