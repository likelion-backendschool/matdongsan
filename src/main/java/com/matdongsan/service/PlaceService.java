package com.matdongsan.service;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import com.matdongsan.domain.place.PlaceDto;
import com.matdongsan.domain.place.PlaceRepository;
import com.matdongsan.domain.place.PlaceWebInfo;
import com.matdongsan.util.webdriver.WebDriverUtil;
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
    private final WebDriverUtil driverUtil;

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


    private void pullInfo(Place place) {

//        String menus = "";
//        String mainPhotoUrl = "";
//        String photoUrls = "";

        PlaceWebInfo placeWebInfo = null;
        try {
            placeWebInfo = driverUtil.useDriver("https://place.map.kakao.com/" + place.getId());
        } catch (InterruptedException e) {
            throw new RuntimeException("파싱 중 에러 발생"+e);
        }
        log.info("info = {}",placeWebInfo.toString());


//            String url = "http://82a2-114-203-164-124.jp.ngrok.io:8080/" + place.getId();
//            HttpHeaders headers = new HttpHeaders();
//
//            HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(headers);
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
//            String body = response.getBody();
//
//            JSONObject bodyJson = (JSONObject) parser.parse(body);
//
//            if(bodyJson.containsKey("menuInfo")){
//                JSONObject menuInfo = (JSONObject) bodyJson.get("menuInfo");
//                JSONArray menuList = (JSONArray) menuInfo.get("menuList");
//                menus = menuList.stream().map(i -> {
//                    JSONObject menuJson = (JSONObject) i;
//                    String menu = menuJson.get("menu").toString();
//                    if(menu.contains("|")) return "";
//                    String price = menuJson.get("price").toString();
//                    return (menu + "=" + price);
//                }).collect(Collectors.joining("|")).toString();
//            }
//
//            if(bodyJson.containsKey("photo")){
//                JSONObject photo = (JSONObject) bodyJson.get("photo");
//                JSONArray photoList = (JSONArray) photo.get("photoList");
//                JSONObject first = (JSONObject) photoList.get(0);
//                JSONArray array = (JSONArray) first.get("list");
//                photoUrls = (String) array.stream().map(i -> {
//                    JSONObject tmp = (JSONObject) i;
//                    return tmp.get("orgurl").toString();
//                }).limit(4).collect(Collectors.joining(","));
//            }
//
//            JSONObject basicInfo = (JSONObject) bodyJson.get("basicInfo");
//
//            if (basicInfo.containsKey("mainphotourl")) {
//                mainPhotoUrl = basicInfo.get("mainphotourl").toString();
//            }

        place.setAdditionalInfo(placeWebInfo.getMenu(), placeWebInfo.getMainPhotoUrl(), placeWebInfo.getPhotoUrls());

    }

}
