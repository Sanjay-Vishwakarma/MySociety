package com.pz.mysociety.repository.amenityRepository;


import com.pz.mysociety.entity.amenityEntity.AmenityMasterEntity;
import com.pz.mysociety.model.VO.AmenityListVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface AmenityMasterRepository extends JpaRepository<AmenityMasterEntity ,Integer >, JpaSpecificationExecutor<AmenityMasterEntity> {

    @Query("SELECT a FROM Amenity a WHERE a.id = ?1")
    AmenityMasterEntity getAmenityById(int amenityId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE Amenity amt SET amt.isRead = ?1 WHERE amt.id = ?2")
    void changeAmenityFlag(boolean isRead, int amenityId);

    @Query(nativeQuery = true ,value = "SELECT count(*) FROM amenity_booking_master a WHERE a.society_id = ?1 and a.amenity_status = ?2  and a.start_date BETWEEN ?3 AND ?4")
    int countBasedOnWEEK(int societyId,String status,LocalDate toDate, LocalDate fromDate);

    @Query(nativeQuery = true ,value = "SELECT count(*) FROM amenity_booking_master a WHERE a.society_id = ? and a.amenity_status = ? and  MONTH(a.start_date)=? and YEAR(a.start_date)=?")
    int countBasedOnMonth(int societyId, String status,int month, int year);

    @Query(nativeQuery = true ,value = "SELECT count(*) FROM amenity_booking_master a WHERE a.society_id = ? and a.amenity_status = ?  and DATE(a.start_date)=?")
    int countBySocietyIdAndStatus(int societyId, String status, LocalDate date);

    @Query(value = "SELECT count(amt) FROM Amenity amt WHERE amt.amenityStatus = ?1 AND amt.amenityTypeId = ?2 AND ((amt.startDate <= ?3 AND amt.endDate >= ?4)OR(amt.startDate <= ?3 AND amt.endDate >= ?3) OR (amt.startDate >= ?3 AND amt.endDate <= ?4) OR (amt.startDate <= ?4 AND amt.endDate >= ?4))")
    int checkAmenityBook(String approved, int amenityTypeId, Date formatDate, Date formatDate1);

    @Query(value = "SELECT a FROM Amenity a WHERE a.unitId = ?1 AND a.startDate = ?2 AND a.endDate = ?3")
    AmenityMasterEntity getRepeatAmenity(int unitId, Date startDate, Date endDate);
}

