package com.matdongsan.domain.favorite;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {
    // 고유 Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;


    public Favorite(Member member, Place place) {
        this.member = member;
        setPlace(place);
    }

    private void setPlace(Place place) {
        place.getFavorites().add(this);
        this.place = place;
    }
}
