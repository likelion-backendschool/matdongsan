package com.matdongsan.domain.place;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PlaceRepository extends JpaRepository<Place,Long>, PlaceRepositoryCustom {
    boolean existsById(long id);
}
