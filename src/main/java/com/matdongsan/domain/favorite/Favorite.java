package com.matdongsan.domain.favorite;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Place> placeList;

    public Favorite(Member member, String subject) {
        this.member = member;
        this.subject = subject;
    }

    public void addPlace(Place place) {
        this.placeList.add(place);
        place.setFavorite(this);
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
