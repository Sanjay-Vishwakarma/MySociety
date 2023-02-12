package com.pz.mysociety.repository.amenityRepository;


import com.pz.mysociety.entity.amenityEntity.AmenityTypeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenityTypeRepository extends JpaRepository<AmenityTypeEntity, Integer> {

    AmenityTypeEntity findBySocietyIdAndType(int societyId, String type);

    List<AmenityTypeEntity> findBySocietyIdAndIsActive(int societyId, boolean isActive, Pageable paging);

    @Query("SELECT a FROM amenityType a WHERE a.id = ?1")
    AmenityTypeEntity getAmenityTypeById(int amenityId);

    int countByIsActive(boolean isActive);
}

