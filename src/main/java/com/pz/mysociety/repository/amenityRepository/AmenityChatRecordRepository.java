package com.pz.mysociety.repository.amenityRepository;

import com.pz.mysociety.entity.amenityEntity.AmenityChatRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AmenityChatRecordRepository extends JpaRepository<AmenityChatRecordEntity, Integer> {

    List<AmenityChatRecordEntity> findByAmenityId(int amenityId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE AmenityHistory ah SET ah.isRead = ?1 WHERE ah.amenityId = ?2 AND ah.role = ?3")
    void changeAdminFlag(boolean flag, int amenityId, String role);

    AmenityChatRecordEntity findByAttachment(String fileName);

}
