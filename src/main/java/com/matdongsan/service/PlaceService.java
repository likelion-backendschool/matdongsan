package com.matdongsan.service;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import com.matdongsan.domain.place.PlaceDto;
import com.matdongsan.domain.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {

    private final FavoriteService favoriteService;
    private final MemberService memberService;

    private final PlaceRepository placeRepository;
    private final RestTemplate restTemplate;
    private final JSONParser parser = new JSONParser();

    public void savePlace(PlaceDto placeDto) {
        if (!placeRepository.existsById(placeDto.getId())) {
            Place place = placeDto.toPlace();
            pullInfo(place);
            placeRepository.save(place);
        }
    }

    public Place findPlace(long placeId) {
        Optional<Place> place = placeRepository.findById(placeId);
        return place.orElseThrow(() -> new EntityNotFoundException("해당 음식점이 없음"));
    }

    /*public boolean doFavorite(long memberId,long placeId) {
        Place findPlace = findPlace(placeId);
        Member findMember = memberService.findMember(memberId);

        return favoriteService.doFavorite(findMember, findPlace);
    }*/



    private void pullInfo(Place place) {

        try {
            String menus="";
            String facilityInfo="";
            String mainPhotoUrl = "";
            String photoUrls = "";
            String url = "https://place.map.kakao.com/main/v/" + place.getId();
            HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(new HttpHeaders());
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            String body = response.getBody();

            JSONObject bodyJson = (JSONObject) parser.parse(body);

            if(bodyJson.containsKey("menuInfo")){
                JSONObject menuInfo = (JSONObject) bodyJson.get("menuInfo");
                JSONArray menuList = (JSONArray) menuInfo.get("menuList");
                menus = menuList.stream().map(i -> {
                    JSONObject menuJson = (JSONObject) i;
                    String menu = menuJson.get("menu").toString();
                    if(menu.contains("|")) return "";
                    String price = menuJson.get("price").toString();
                    return (menu + "=" + price);
                }).collect(Collectors.joining("|")).toString();
            }

            if(bodyJson.containsKey("photo")){
                JSONObject photo = (JSONObject) bodyJson.get("photo");
                JSONArray photoList = (JSONArray) photo.get("photoList");
                JSONObject first = (JSONObject) photoList.get(0);
                JSONArray array = (JSONArray) first.get("list");
                photoUrls = (String) array.stream().map(i -> {
                    JSONObject tmp = (JSONObject) i;
                    return tmp.get("orgurl").toString();
                }).limit(4).collect(Collectors.joining(","));
            }

            JSONObject basicInfo = (JSONObject) bodyJson.get("basicInfo");

            if (basicInfo.containsKey("mainphotourl")) {
                mainPhotoUrl = basicInfo.get("mainphotourl").toString();
            }

            if (basicInfo.containsKey("facilityInfo")) {
                JSONObject facility = (JSONObject) basicInfo.get("facilityInfo");
                facilityInfo = facility.entrySet().stream().map(i -> {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) i;
                    return entry.getKey() + "=" + entry.getValue();
                }).collect(Collectors.joining("|")).toString();
            }
            place.setAdditionalInfo(menus, facilityInfo,mainPhotoUrl,photoUrls);
        } catch (ParseException e) {
            log.error("parser error",e);
            throw new RuntimeException(e);
        }
    }

}
