package com.matdongsan.domain.place;

import com.matdongsan.domain.post.Post;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.matdongsan.domain.place.QPlace.place;

@RequiredArgsConstructor
public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Place> findByTop5Place() {
        JPAQuery<Place> placeQuery = jpaQueryFactory
                .selectFrom(place)
                .orderBy(place.posts.size().desc())
                .limit(5);

        return placeQuery.fetch();
    }
}
