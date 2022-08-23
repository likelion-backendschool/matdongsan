package com.matdongsan.service;

import com.matdongsan.domain.favorite.Favorite;
import com.matdongsan.domain.favorite.FavoriteRepository;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public boolean doFavorite(Member member, Place place) {
        Optional<Favorite> findFavorite = favoriteRepository.findByMemberAndPlace(member, place);
        if (findFavorite.isPresent()) { //이미 좋아요가 존재한다면 좋아요 삭제
            favoriteRepository.delete(findFavorite.get());
            return false;
        }else{ // 좋아요가 없다면 새로운 좋아요 저장
            Favorite favorite = new Favorite(member, place);
            return true;
        }
    }

    public boolean existFavorite(Member member, Place place) {
        return favoriteRepository.existsByMemberAndPlace(member, place);
    }

    public int countByPlace(Place place) {
        return favoriteRepository.countByPlace(place);
    }

    public List<Favorite> findAllByMember(Member member) {
        return favoriteRepository.findAllByMember(member);
    }
}
