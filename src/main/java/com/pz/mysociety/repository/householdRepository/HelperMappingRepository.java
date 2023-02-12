package com.pz.mysociety.repository.householdRepository;

import com.pz.mysociety.entity.householdEntity.HelperMappingEntity;
import com.pz.mysociety.model.VO.HelperListVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelperMappingRepository extends JpaRepository<HelperMappingEntity, Integer> {
    HelperMappingEntity findByUnitIdAndHelperId(int unitId, int helperId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(hm.unitId, hm.rating, hm.review, hm.isActive) FROM HelperMapping hm WHERE hm.societyId = ?1 AND hm.helperId = ?2")
    List<HelperListVO> getHelperDetail(int societyId, int helperId);

    @Query(value = "SELECT AVG(hm.rating) FROM HelperMapping hm WHERE hm.societyId = ?1 AND hm.helperId = ?2 AND hm.rating != 0")
    String getAverageRating(int societyId, int helperId);

    List<HelperMappingEntity> findByUnitIdAndIsActive(int unitId, boolean b);

    HelperMappingEntity findBySocietyIdAndUnitIdAndHelperId(int societyId, int flatId, int helperId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(hm.helperId,hm.unitId, hm.review, hm.rating,hm.timestamp) FROM HelperMapping hm WHERE hm.societyId = ?1 ")
    List<HelperListVO> findBySocietyId(int societyId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(hm.helperId,hm.unitId, hm.review, hm.rating,hm.timestamp) FROM HelperMapping hm WHERE hm.societyId = ?1 AND hm.isActive=?2")
    List<HelperListVO> getActiveHelper(int societyId,  boolean b);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(hm.helperId,hm.unitId, hm.review, hm.rating,hm.timestamp) FROM HelperMapping hm WHERE hm.societyId = ?1 AND hm.isActive=?2 ")
    List<HelperListVO> getNotActiveHelper(int societyId, boolean b);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(hm.id, hm.helperId) FROM HelperMapping hm WHERE hm.unitId = ?1 AND hm.isActive = ?2 ")
    List<HelperListVO> getHelperMapping(int unitId, boolean isActive);

/*    @Query(value = "SELECT DISTINCT new com.pz.mysociety.model.VO.HelperListVO(dh.id, dh.helperName, dh.helperMobile, dh.type, dh.photo ,dh.addedBy) FROM Helper dh  WHERE hm.societyId = ?1 AND dh.type =?2")
    List<HelperListVO> getSocietyHelper(int societyId, String type);

    @Query(value = "SELECT DISTINCT new com.pz.mysociety.model.VO.HelperListVO(dh.id,dh.helperName,dh.helperMobile,dh.type,dh.photo,dh.addedBy,hm.isActive,dh.timestamp) FROM HelperMapping hm JOIN Helper dh ON dh.id=hm.helperId WHERE hm.societyId=?1 and hm.isActive=true")
    List<HelperListVO> getActiveHelper(int societyId);

    @Query(value = "SELECT DISTINCT new com.pz.mysociety.model.VO.HelperListVO(dh.id,dh.helperName,dh.helperMobile,dh.type,dh.photo,dh.addedBy,hm.isActive,dh.timestamp) FROM HelperMapping hm JOIN Helper dh ON dh.id=hm.helperId WHERE hm.societyId=?1 and hm.isActive=false" )
    List<HelperListVO> getNotActiveHelper(int societyId);*/

    @Query(value = "SELECT  hm.unitId FROM HelperMapping hm WHERE hm.societyId = ?1 AND hm.helperId = ?2 AND hm.isActive=?3")
    List<Integer> getHelperMappingDetails(int societyId, int helperId,  boolean b);

}
