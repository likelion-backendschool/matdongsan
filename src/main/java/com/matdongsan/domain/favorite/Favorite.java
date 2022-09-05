package com.matdongsan.domain.favorite;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import lombok.*;

import javax.persistence.*;

@Setter
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Favorite {
    // 고유 Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    public Favorite(Member member, Place place) {
        this.member = member;
        setPlace(place);
    }

    public Favorite(Member member, String subject) {
        this.member = member;
        setSubject(subject);
    }

    private void setPlace(Place place) {
        place.getFavorites().add(this);
        this.place = place;
    }
}
