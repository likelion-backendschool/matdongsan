package com.matdongsan.service;

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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final RestTemplate restTemplate;
    private final JSONParser parser = new JSONParser();

    @Transactional
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

        try {
            String menus="";
            String facilityInfo="";
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
                    String price = menuJson.get("price").toString();
                    return (menu + "=" + price);
                }).collect(Collectors.joining("//")).toString();
            }
            JSONObject basicInfo = (JSONObject) bodyJson.get("basicInfo");
            if (basicInfo.containsKey("facilityInfo")) {
                JSONObject facility = (JSONObject) basicInfo.get("facilityInfo");
                for (Object o : facility.entrySet()) {
                    Map.Entry<String,String> t= (Map.Entry<String, String>) o;
                    log.info("key = {}, value = {}", t.getKey(), t.getValue());
                }
            }
            place.setAdditionalInfo(menus, facilityInfo);
        } catch (ParseException e) {
            log.error("parser error",e);
            throw new RuntimeException(e);
        }
    }

}
