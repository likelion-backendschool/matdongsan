package com.matdongsan.domain.place;

import com.matdongsan.domain.favorite.Favorite;
import com.matdongsan.domain.posts.Posts;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Place {
    @Id
    private long id;

    private String placeName;
    private String placeUrl;
    private String categoryName;
    private String addressName;
    private String roadAddressName;
    private String phone;
    private String x;
    private String y;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "place")
    private List<Posts> posts = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String menus;
    private String facilityInfo;
    private String mainPhotoUrl;

    public Place(long id, String placeName, String placeUrl, String categoryName, String addressName, String roadAddressName, String phone, String x, String y) {
        this.id = id;
        this.placeName = placeName;
        this.placeUrl = placeUrl;
        this.categoryName = categoryName;
        this.addressName = addressName;
        this.roadAddressName = roadAddressName;
        this.phone = phone;
        this.x = x;
        this.y = y;
    }
    public void setAdditionalInfo(String menu,String facility,String mainPhotoUrl){
        this.menus = menu;
        this.facilityInfo = facility;
        this.mainPhotoUrl = mainPhotoUrl;
    }

}
