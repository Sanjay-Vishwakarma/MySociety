package com.pz.mysociety.repository.helperRepository;

import com.pz.mysociety.entity.helperEntity.HelperEntity;
import com.pz.mysociety.model.VO.HelperListVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HelperRepository extends JpaRepository<HelperEntity, Integer >,JpaSpecificationExecutor<HelperEntity> {

    List<HelperEntity> findByIsActiveAndSocietyIdAndType(boolean isActive,int societyId, String type, Pageable paging);

    List<HelperEntity> findByIsActiveAndSocietyIdAndTypeAndHelperTypeId(boolean isActive,int societyId, String type,int helperTypeId, Pageable paging);

    HelperEntity findBySocietyIdAndHelperMobileAndIsActive(int societyId, String helperMobile, boolean isActive );


    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(h.id, h.helperName, h.helperMobile, h.type, h.addedBy, h.photo, h.freeTimeSlot, h.timestamp) FROM Helper h WHERE h.societyId = ?1 AND h.helperTypeId=?2")
    List<HelperListVO> helperDetailsBYType(int societyId, int helperTypeId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(h.id,h.passCode, h.helperName, h.helperMobile, h.type, h.addedBy, h.photo, h.freeTimeSlot,h.isActive,h.helperTypeId,h.loginStatus,h.isFrequentVisitor,h.companyId,h.companyName,ht.type,st.type,ht.iconImage,st.iconImage, h.timestamp) FROM Helper h join HelperType ht on h.helperTypeId = ht.id join ServiceType st on st.id = ht.serviceTypeId where  h.societyId=?1 and h.type=?2 and h.helperTypeId=?3")
    List<HelperListVO> helperDetails(int societyId, String type,int helperTypeId, Pageable paging);

   @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(h.id,h.passCode, h.helperName, h.helperMobile, h.type, h.addedBy, h.photo, h.freeTimeSlot,h.isActive,h.helperTypeId,h.loginStatus,h.isFrequentVisitor,h.companyId,h.companyName,ht.type,st.type,ht.iconImage,st.iconImage, h.timestamp) FROM Helper h join HelperType ht on h.helperTypeId = ht.id join ServiceType st on st.id = ht.serviceTypeId where  h.societyId=?1 and h.type=?2")
    List<HelperListVO> helperDetails1(int societyId, String type, Pageable paging);

    HelperEntity findById(int id);

    List<HelperEntity> findBySocietyIdAndIsActiveAndType(int societyId, boolean isActive, String type, Pageable pageable);

    List<HelperEntity> findBySocietyIdAndIsActiveAndLoginStatusAndType(int societyId ,boolean isActive, String loginStatus, String type,Pageable pageable);

    int countBySocietyIdAndType(int societyId, String type);

    int countBySocietyIdAndLoginStatusAndType(int societyId, String loginStatus, String type);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE Helper h SET h.loginStatus=?1 WHERE h.id=?2")
    void updateHelperLoginStatus(String loginStatus, int helperId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE Helper h SET h.loginStatus=?1 , h.loginId=?2 WHERE h.id = ?3")
    void updateHelperLoginStatusAndId(String loginStatus, String loginId, int helperLogId);

    int countByIsActiveAndSocietyIdAndType(boolean isActive, int societyId, String type);

    int countBySocietyIdAndTypeAndHelperTypeId( int societyId, String type,int helperTypeId);

    HelperEntity findByPassCodeAndSocietyId(int passcode, int societyId);

    @Query(value = "SELECT DISTINCT new com.pz.mysociety.model.VO.HelperListVO( h.id) FROM HelperType h JOIN ServiceType s ON s.id=h.serviceTypeId WHERE  s.isAttendance =?1")
    List<HelperListVO> attendaceCheck(boolean isAttendance);

    List<HelperEntity> findByLoginStatusAndSocietyId(String loginStatus, int societyId);

    List<HelperEntity> findByIsActiveAndSocietyIdAndIsFrequentVisitor(boolean isActive,int societyId,boolean isFrequentVisitor);

    @Query(value = "SELECT DISTINCT new com.pz.mysociety.model.VO.HelperListVO(h.id, h.helperName, h.helperMobile, h.type, h.addedBy, h.photo, h.freeTimeSlot, h.timestamp,h.companyId,h.companyName,h.isFrequentVisitor, h.loginStatus,h.helperTypeId, hh.inTime ) FROM Helper h join  HelperHistoryLog hh on hh.helperId=h.id WHERE h.loginStatus=?1 and h.societyId=?2")
    List<HelperListVO> getInsideList( String loginStatus, int societyId);

    @Query(value = "select new com.pz.mysociety.model.VO.HelperListVO(vm.societyId,vm.loginId, vm.id,vm.helperName, vm.helperMobile, vm.helperTypeId, vm.type,vm.loginStatus,v.companyId, v.companyName, vm.isFrequentVisitor,vm.photo,v.unitId,v.inTime, ht.type) from HelperType ht join Helper vm on ht.id=vm.helperTypeId join HelperHistoryLog v on vm.id = v.helperId  where vm.societyId=?1 and v.status = ?2 ")
    List<HelperListVO> getWaitingVisitorList(int societyId, String Status);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(hh.id, h.helperName, h.helperMobile, h.type, h.addedBy, h.photo, h.timestamp, h.helperTypeId,hh.unitId,h.companyId, h.companyName, hh.inTime, hh.outTime, hh.approvedBy, hh.bodyTemperature, hh.isWearingMask, hh.status) FROM Helper h join  HelperHistoryLog hh on hh.helperId=h.id WHERE hh.unitId= ?1 order by hh.id DESC")
    List<HelperListVO> getRecentActivity(int unitId,Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(hh.id, h.helperName, h.helperMobile, h.type, h.addedBy, h.photo, h.timestamp, h.helperTypeId,hh.unitId,hh.companyId, hh.companyName, hh.inTime, hh.outTime, hh.approvedBy, hh.bodyTemperature, hh.isWearingMask, hh.status) FROM Helper h join  HelperHistoryLog hh on hh.helperId=h.id WHERE h.societyId = ?1 and hh.areaId= ?2 and hh.inTime >= ?3 and hh.inTime <= ?4 order by hh.id DESC")
    List<HelperListVO> getInOutHistoryByAreaId(int societyId,int areaId, String fromDate, String toDate ,Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(hh.id, h.helperName, h.helperMobile, h.type, h.addedBy, h.photo, h.timestamp, h.helperTypeId,hh.unitId,hh.companyId, hh.companyName, hh.inTime, hh.outTime, hh.approvedBy, hh.bodyTemperature, hh.isWearingMask, hh.status) FROM Helper h join  HelperHistoryLog hh on hh.helperId=h.id WHERE h.societyId = ?1 and hh.areaId= ?2 and hh.unitId= ?3 and hh.inTime >= ?4 and hh.inTime <= ?5 order by hh.id DESC")
    List<HelperListVO> getInOutHistoryByAreaIdAndUnitId(int societyId,int areaId,int unitId, String fromDate, String toDate ,Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(hh.id, h.helperName, h.helperMobile, h.type, h.addedBy, h.photo, h.timestamp, h.helperTypeId,hh.unitId,h.companyId, h.companyName, hh.inTime, hh.outTime, hh.approvedBy, hh.bodyTemperature, hh.isWearingMask, hh.status) FROM Helper h join  HelperHistoryLog hh on hh.helperId=h.id WHERE h.type=?1 and h.societyId = ?2 and hh.areaId= ?3 and hh.inTime >= ?4 and hh.inTime <= ?5 order by hh.id DESC")
    List<HelperListVO> getVisitorDashBoardByAreaId(String type,int societyId,int areaId, String fromDate, String toDate ,Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(hh.id, h.helperName, h.helperMobile, h.type, h.addedBy, h.photo, h.timestamp, h.helperTypeId,hh.unitId,h.companyId, h.companyName, hh.inTime, hh.outTime, hh.approvedBy, hh.bodyTemperature, hh.isWearingMask, hh.status) FROM Helper h join  HelperHistoryLog hh on hh.helperId=h.id WHERE h.type=?1 and h.societyId = ?2  and hh.inTime >= ?3 and hh.inTime <= ?4 order by hh.id DESC")
    List<HelperListVO> getVisitorDashBoard(String type,int societyId,String fromDate, String toDate ,Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(h.id,h.passCode, h.helperName, h.helperMobile, h.type, h.addedBy, h.photo, h.freeTimeSlot,h.isActive,h.helperTypeId,h.loginStatus,h.isFrequentVisitor,h.companyId,h.companyName,ht.type,st.type,ht.iconImage,st.iconImage, h.timestamp) FROM Helper h join HelperType ht on h.helperTypeId = ht.id join ServiceType st on st.id = ht.serviceTypeId ")
    List<HelperListVO> helperDetails(Specification specification, Pageable paging);

    HelperEntity findBySocietyIdAndHelperMobile(int societyId, String helperMobile);
}
