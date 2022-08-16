package com.matdongsan.web.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.matdongsan.domain.place.Place;
import com.matdongsan.web.dto.PlaceDto;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/place")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {

    private final RestTemplate restTemplate;

    @GetMapping
    public String showPlaces(Model model) {
        List<PlaceDto> placeDtoList = getPlace();
        model.addAttribute("places", placeDtoList);
        return "place/place-list";
    }

    private ResponseEntity<String> sendApi(){
        String url = "https://dapi.kakao.com/v2/local/search/category.json?y={y}&x={x}&category_group_code={category_group_code}&radius={radius}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK b12ed310737d8c0fe214f6b5e4532252");

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(headers);

        Map<String, String> param = new HashMap<>();
        param.put("y", "37.514322572335935");
        param.put("x", "127.06283102249932");
        param.put("category_group_code", "FD6,CE7");
        param.put("radius", "2000");

        return restTemplate.exchange(url,
                HttpMethod.GET, httpEntity, String.class, param);
    }

    private List<PlaceDto> getPlace() {
        try {
            List<PlaceDto> list = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();
            JSONParser parser = new JSONParser();
            ResponseEntity<String> responseEntity = sendApi();
            JSONObject body = (JSONObject) parser.parse(responseEntity.getBody());
            log.info("body = {}", body.toString());
            JSONArray documentsBody = (JSONArray) body.get("documents");
            for (Object o : documentsBody) list.add(mapper.readValue(o.toString(), PlaceDto.class));
            return list;
        } catch (ParseException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }




}
