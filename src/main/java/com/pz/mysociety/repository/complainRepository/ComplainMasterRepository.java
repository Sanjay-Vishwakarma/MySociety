package com.pz.mysociety.repository.complainRepository;

import com.pz.mysociety.entity.complainEntity.ComplainMasterEntity;
import com.pz.mysociety.model.VO.ComplainListVO;
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
public interface ComplainMasterRepository extends JpaRepository<ComplainMasterEntity, Integer> , JpaSpecificationExecutor<ComplainMasterEntity> {

    @Query(value = "SELECT DISTINCT new com.pz.mysociety.model.VO.ComplainListVO( c.id, c.userId, c.unitId, c.complainTitle, c.complainContent, c.attachment, c.complainStatus, c.isPriority, c.timestamp, c.isRead) FROM Complain c  WHERE c.societyId =?1 ORDER BY c.id DESC" )
    List<ComplainListVO> getSocietyId(int societyId, Pageable pageable);

    ComplainMasterEntity findByIdAndUserId(int complainId, int userId);

    @Query("SELECT c FROM Complain c WHERE c.id = ?1")
    ComplainMasterEntity getComplainById(int complainId);

    @Query( value = "SELECT DISTINCT new com.pz.mysociety.model.VO.ComplainListVO( c.id, c.userId, c.unitId, c.complainTitle, c.complainContent, c.attachment, c.complainStatus, c.isPriority, c.timestamp, c.isRead) FROM Complain c  WHERE c.userId =?1 AND c.unitId = ?2 ORDER BY c.id DESC ")
    List<ComplainListVO> findByUserIdAndUnitId(int userId, int unitId , Pageable pageable);

    @Query(value = "SELECT DISTINCT new com.pz.mysociety.model.VO.ComplainListVO( c.id, c.userId, c.unitId, c.complainTitle, c.complainContent, c.attachment, c.complainStatus, c.isPriority, c.timestamp, c.isRead) FROM Complain c  WHERE c.societyId =?1 AND c.complainStatus = ?2 ORDER BY c.id DESC" )
    List<ComplainListVO> findBySocietyIdAndComplainStatus(int societyId, String complainStatus, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE Complain c SET c.isRead = ?1 WHERE c.id = ?2")
    void changeComplainFlag(boolean isRead, int complainId);

    int countByUserIdAndUnitId(int userId, int unitId);
    int countBySocietyIdAndComplainStatus(int societyId, String status);
    int countBySocietyId(int societyId);

    int countBySocietyIdAndComplainStatusAndComplainDateBetween(int societyId,String status,LocalDate toDate, LocalDate fromDate );

    ComplainMasterEntity findBySocietyIdAndAttachment(int SocietyId,String fileName);


    @Query(nativeQuery = true ,value = "SELECT count(*) FROM complain_master c WHERE c.society_id = ? and c.complain_status = ? and  MONTH(c.complain_date)=? and YEAR(c.complain_date)=?")
    int countBasedOnMonth(int societyId, String status,int month, int year);

    @Query(nativeQuery = true ,value = "SELECT count(*) FROM complain_master c WHERE c.society_id = ? and c.complain_status = ?  and DATE(c.complain_date)=?")
    int countBySocietyIdAndStatus(int societyId, String status, LocalDate date);
}
