package com.matdongsan.domain.place;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PlaceRepository extends JpaRepository<Place,Long> {
    boolean existsById(long id);
}
