package com.matdongsan.web.controller.place;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.favorite.Favorite;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.FavoriteService;
import com.matdongsan.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Literal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/place")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {

    private final PlaceService placeService;
    private final FavoriteService favoriteService;
    private final AccountService accountService;

    @GetMapping
    public String showList(){
        return "place/place-list";
    }

    @GetMapping("/map")
    public String showMap(){
        return "place/place-map";
    }


    @GetMapping("/{placeId}/detail")
    public String showPlaceDetail(Principal principal,@PathVariable("placeId") long placeId, Model model) {
        Account account = accountService.findAccountByUsername(principal.getName());
        Member member = account.getMember();
        Place place = placeService.findPlace(placeId);

        Optional<Favorite> optionalFavorite = Optional.ofNullable(favoriteService.findTopByMember(member));
        if (optionalFavorite.isPresent()) {
            List<Favorite> favoriteList = favoriteService.findAllByMember(member);
            model.addAttribute("favorites", favoriteList);
        } else {
            Favorite favorite = new Favorite(member, "나만의 맛집");
            favoriteService.save(favorite);
            model.addAttribute("favorites", favorite);
        }
        model.addAttribute("place", place);

        return "place/place-detail";
    }





}
