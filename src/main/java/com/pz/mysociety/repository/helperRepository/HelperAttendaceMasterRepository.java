package com.pz.mysociety.repository.helperRepository;

import com.pz.mysociety.entity.helperEntity.HelperAttendaceMasterEntity;
import com.pz.mysociety.model.VO.HelperListVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface HelperAttendaceMasterRepository extends JpaRepository<HelperAttendaceMasterEntity, Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE HelperAttendanceMaster h SET h.outTime=?1  WHERE h.loginId=?2 and h.helperId=?3 and h.outTime IS NULL")
    void updateOutTime(Date outTime,String loginId, int helperId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE HelperAttendanceMaster h SET h.outTime=?1  WHERE h.loginId=?2 and h.helperId=?3 and h.unitId=?4")
    void updateOutTimeByUnitId(Date outTime,String loginId, int helperId, int unitId);


    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(h.unitId,h.loginId, h.approvedBy, h.loginStatus, h.inTime, h.outTime) FROM HelperAttendanceMaster h WHERE h.societyId = ?1 and h.unitId = ?2 and h.helperId = ?3 and h.inTime >= ?4 and h.inTime <= ?5")
    List<HelperListVO> getAttendance(int societyId, int unitId, int helperId, String fromDate, String toDate);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(h.unitId,h.loginId, h.approvedBy, h.loginStatus, h.inTime, h.outTime)FROM HelperAttendanceMaster h WHERE h.societyId = ?1 and h.helperId = ?2 and h.inTime >= ?3 and h.inTime <= ?4")
    List<HelperListVO> getAttendanceBySocietyId(int societyId, int helperId, String fromDate, String toDate);
}
