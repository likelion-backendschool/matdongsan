package com.matdongsan.web.controller.place;

import com.matdongsan.domain.place.PlaceDto;
import com.matdongsan.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/place")
@RequiredArgsConstructor
@Slf4j
public class PlaceApiController {

    private final PlaceService placeService;
    
    @PostMapping
    public ResponseEntity<String> savePlace(@RequestBody PlaceDto placeDto){
        log.info("placeDto = {}", placeDto);
        placeService.savePlace(placeDto);
        return ResponseEntity.ok().body("ok");
    }
}
