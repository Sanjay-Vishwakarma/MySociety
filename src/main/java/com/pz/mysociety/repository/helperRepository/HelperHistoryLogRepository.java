package com.pz.mysociety.repository.helperRepository;

import com.pz.mysociety.entity.helperEntity.HelperHistoryLogEntity;
import com.pz.mysociety.model.VO.HelperListVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

@Repository
public interface HelperHistoryLogRepository extends JpaRepository<HelperHistoryLogEntity, Integer> , JpaSpecificationExecutor<HelperHistoryLogEntity> {


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE HelperHistoryLog h SET h.outTime=?1, h.status=?2 WHERE h.loginId=?3 and h.helperId =?4 and h.outTime IS NULL "  )
    void updateOutTime(Date outTime,String loginStatus, String loginId, int helperId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE HelperHistoryLog h SET  h.status=?1 WHERE h.loginId=?2 and h.helperId =?3")
    void updateStatus(String loginStatus, String loginId, int helperId);

    @Query(value = "SELECT distinct h.unitId FROM HelperHistoryLog h WHERE h.helperId = ?1")
    List<Integer> getUnitIdList( int visitorId);

    HelperHistoryLogEntity findByLoginId(String loginId);

    List<HelperHistoryLogEntity> findByLoginIdAndSocietyIdAndStatus(String loginId, int societyId, String loginStatus);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE HelperHistoryLog h SET h.outTime=?1, h.status=?2 WHERE h.loginId=?3 and h.helperId =?4 and h.unitId =?5")
    void updateOutTimeByUnitId(Date outTime,String loginStatus, String loginId, int helperId, int unitId);

    int countByStatusAndLoginId(String status, String loginId);

    int countBySocietyIdAndAreaIdAndInTimeGreaterThanAndInTimeLessThan(int societyId,int areaId,String fromDate, String toDate );

//    int countByTypeAndSocietyIdAndInTimeGreaterThanEqualAndInTimeLessThanEqual(String type,int societyId,String fromDate, String toDate );

    int countBySocietyIdAndAreaIdAndUnitIdAndInTimeGreaterThanAndInTimeLessThan(int societyId,int areaId,int unitId, String fromDate, String toDate );

    @Query(nativeQuery = true ,value = "SELECT count(DISTINCT h.login_id) FROM helper_history_log h WHERE h.type = ? and h.society_id = ?  and  MONTH(h.in_time)=? and YEAR(h.in_time)=?")
    int countBasedOnMonth(String type,int societyId, int month, int year);

//    @Query(nativeQuery = true ,value = "SELECT count(*) FROM helper_history_log h WHERE h.type = ? and h.society_id = ?  and  DATE(h.in_time)=?")
//    int countByTypeAndSocietyIdAndInTime(String type,int societyId, LocalDate date);

    @Query(value = "SELECT new com.pz.mysociety.entity.helperEntity.HelperHistoryLogEntity(h.unitId,h.status) FROM HelperHistoryLog h WHERE h.societyId =?1 AND h.loginId = ?2")
    List<HelperHistoryLogEntity> getUnitStatus(int societyId, String loginId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(h.loginId, hm.helperName, hm.helperMobile, hm.photo, h.approvedBy, h.companyId) FROM HelperHistoryLog h JOIN Helper hm ON h.helperId = hm.id WHERE h.societyId = ?1 AND h.type = ?2 ORDER BY h.id DESC")
    List<HelperListVO> getRecentVisitor(int societyId, String  visitor, Pageable pageable);


//    @Query(value = "SELECT COUNT(DISTINCT hl.loginId) FROM HelperHistoryLog hl WHERE hl.type = ?1 AND hl.societyId =?2 AND hl.inTime >= ?3 ")
//    int getVisitorByDay(String visitor, int societyId, String result);

    @Query(value = "SELECT COUNT(DISTINCT hl.loginId) FROM HelperHistoryLog hl WHERE hl.type = ?1 AND hl.societyId =?2 AND hl.inTime >= ?3 AND hl.inTime <= ?4")
    int getVisitorByWeek(String type,int societyId,String fromDate, String toDate );
}
