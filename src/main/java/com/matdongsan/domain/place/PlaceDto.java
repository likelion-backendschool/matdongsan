package com.matdongsan.domain.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlaceDto {
    private long id;
    @JsonProperty("place_name") private String placeName;
    @JsonProperty("place_url") private String placeUrl;
    @JsonProperty("category_name") private String categoryName;
    @JsonProperty("address_name") private String addressName;
    @JsonProperty("road_address_name") private String roadAddressName;
    private String phone;
    private String x;
    private String y;

    @JsonProperty("category_group_code") private String categoryGroupCode;
    @JsonProperty("category_group_name") private String categoryGroupName;
    private String distance;

    public Place toPlace() {
        return new Place(
                this.id,
                this.placeName,
                this.placeUrl,
                this.categoryName,
                this.addressName,
                this.roadAddressName,
                this.phone,
                this.x,
                this.y);
    }

}

