package com.matdongsan.domain.favorite;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "favorite")
    private List<Place> placeList;

    public Favorite(Member member, String subject) {
        this.member = member;
        setSubject(subject);
    }

    public Favorite(Member member, Place place) {
        this.member = member;
        setPlace(place);
    }
    private void setPlace(Place place) {
        placeList.add(place);
    }
}
