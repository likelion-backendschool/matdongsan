package com.matdongsan.service;

import com.matdongsan.domain.place.Place;
import com.matdongsan.domain.place.PlaceDto;
import com.matdongsan.domain.place.PlaceRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public Place findPlace(long placeId) {
        Optional<Place> place = placeRepository.findById(placeId);
        return place.orElseThrow(() -> new EntityNotFoundException("해당 음식점이 없음"));
    }

}
