package com.matdongsan.service;

import com.matdongsan.domain.place.Place;
import com.matdongsan.domain.place.PlaceRepository;
import com.matdongsan.web.dto.PlaceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public void savePlace(PlaceDto placeDto) {
        if (placeRepository.existsById(placeDto.getId())) {
            placeRepository.save(placeDto.toPlace());
        }
    }

    public Place findPlace(PlaceDto placeDto) {
        savePlace(placeDto);
        return placeRepository.findById(placeDto.getId()).get();
    }

}
