package com.matdongsan.domain.place;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceWebInfo {
    private String menu;
    private String mainPhotoUrl;
    private String photoUrls;
}