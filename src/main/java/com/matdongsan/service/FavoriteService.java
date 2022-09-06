package com.matdongsan.service;

import com.matdongsan.domain.favorite.Favorite;
import com.matdongsan.domain.favorite.FavoriteRepository;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public List<Favorite> findAllByMember(Member member) {
        return favoriteRepository.findAllByMember(member);
    }

    public void save(Favorite favorite) {
        favoriteRepository.save(favorite);
        log.info("save 실행");
    }

    public Favorite findTopByMember(Member member) {
        return favoriteRepository.findTopByMember(member);
    }

    public Favorite findById(Long favoriteId) {
        return favoriteRepository.findById(favoriteId).get();
    }
    public void replaceExistPlace(Member member, Place place, Favorite currentFavorite) {
        // Member -> FavoriteList 속에서 해당 Place가 있는지 확인
        List<Favorite> favoriteList = findAllByMember(member);
        log.info("favoriteList={}", favoriteList.size());
        for (Favorite favorite : favoriteList) {
            if (favorite.getPlaceList().contains(place)){
                log.info("favoriteInnerIter={}", favorite.getPlaceList().size());
                deletePlace(favorite, place);
            }
        }
        log.info("FirstCurrentFavorite={}", currentFavorite.getPlaceList().size());
        currentFavorite.addPlace(place);
        save(currentFavorite);
        for (Place place1 : currentFavorite.getPlaceList()) {
            log.info("showPlaceName={}", place1.getPlaceName());
        }
    }

    public void deletePlace(Favorite currentFavorite, Place place) {
        currentFavorite.getPlaceList().remove(place);
    }
}
