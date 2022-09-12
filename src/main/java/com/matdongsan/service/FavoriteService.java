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
        for (Favorite favorite : favoriteList) {
            if (favorite.getPlaceList().contains(place)){
                deletePlace(favorite, place);
                save(favorite);
            }
        }
        log.info("favorite -> {}", currentFavorite.getSubject());
        currentFavorite.addPlace(place);
        log.info("place -> {}", place.getPlaceName());
        save(currentFavorite);
        log.info("save success");
    }

    public void deletePlace(Favorite currentFavorite, Place place) {
        currentFavorite.getPlaceList().remove(place);
    }

    public Favorite findByIdAndMember(Long favoriteId, Member member) {
        return favoriteRepository.findByIdAndMember(favoriteId, member);
    }

    public void delete(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }
}
