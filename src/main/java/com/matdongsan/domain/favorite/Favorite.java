package com.matdongsan.domain.favorite;

import com.matdongsan.domain.place.Place;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Entity
@Getter
public class Favorite {
    // 고유 Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 북마크 제목
    @Column(length = 200)
    private String subject;
    // 북마크 장소 1대다 매치
    @OneToMany(mappedBy = "place")
    private List<Place> placeList = new ArrayList<>();

    public void addPlace(Place place) {
        // place.setFavorite(this);
        getPlaceList().add(place);
    }
}
