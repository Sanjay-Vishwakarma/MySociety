package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.AreaMasterEntity;
import com.pz.mysociety.model.Request.TypeVO;
import com.pz.mysociety.model.VO.UnitListVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaMasterRepository extends JpaRepository<AreaMasterEntity, Integer> {
    AreaMasterEntity findBySocietyIdAndAreaTypeIdAndAreaName(int societyId, int areaTypeId, String areaName);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UnitListVO(a.id, a.areaName, a.areaTypeId, a.floor, a.isActive) FROM Area a WHERE a.societyId = ?1 AND a.areaTypeId = ?2")
    List<UnitListVO> getSocietyIdAndAreaTypeId(int societyId, int areaTypeId, Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UnitListVO(a.id, a.areaName, art.type, a.floor, a.isActive) FROM Area a JOIN AreaType art ON a.areaTypeId = art.id WHERE a.societyId = ?1 AND a.isActive = ?2 AND art.isResidential = ?3")
    List<UnitListVO> getAreaDetail(int societyId, boolean isActive, boolean isResidential, Pageable pageable);

    int countBySocietyIdAndIsActive(int societyId, boolean isActive);

    int countBySocietyIdAndAreaTypeIdAndIsActive(int societyId, int areaTypeEntityId, boolean isActive);

    @Query(value = "SELECT COUNT(a) FROM Area a JOIN AreaType art ON a.areaTypeId = art.id WHERE a.societyId = ?1 AND a.isActive = ?2 AND art.isResidential = ?3")
    int getResidentialCount(int societyId, boolean isActive, boolean isResidential);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UnitListVO(a.id, a.areaName, a.floor) FROM Area a WHERE a.societyId = ?1 AND a.areaTypeId = ?2 AND a.areaName LIKE ?3% AND a.isActive = ?4")
    List<UnitListVO> getSearchAreaList(int societyId, int areaTypeId, String areaName, boolean isActive, Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UnitListVO(a.id, a.areaName, a.floor) FROM Area a WHERE a.societyId = ?1 AND a.areaTypeId = ?2 AND a.isActive = ?3 ORDER BY a.areaName")
    List<UnitListVO> getSearchList(int societyId, int areaTypeId, boolean isActive, Pageable pageable);

    int countBySocietyIdAndAreaTypeId(int societyId, int id);

    int countBySocietyIdAndAreaTypeIdAndAreaNameLikeAndIsActive(int societyId, int areaTypeId, String areaName, boolean isActive);
}
