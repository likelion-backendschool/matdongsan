package com.matdongsan.domain.favorite;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByMemberAndPlace(Member member, Place place);

    boolean existsByMemberAndPlace(Member member, Place place);

    int countByPlace(Place place);

    List<Favorite> findAllByMember(Member member);

    void deleteByMemberAndPlace(Member member, Place place);
}
