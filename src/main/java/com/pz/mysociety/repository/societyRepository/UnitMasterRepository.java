package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.UnitMasterEntity;
import com.pz.mysociety.model.Request.TypeVO;
import com.pz.mysociety.model.Request.UnitMasterVO;
import com.pz.mysociety.model.VO.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface UnitMasterRepository extends JpaRepository<UnitMasterEntity, Integer> {

    @Query(value = "SELECT DISTINCT new com.pz.mysociety.model.VO.UnitListVO(a.id, u.areaTypeId, t.type, u.id, a.areaName, u.floor, u.unit, u.isParking, u.isActive) FROM Unit u JOIN Area a ON u.areaId = a.id JOIN AreaType t ON a.areaTypeId = t.id  WHERE u.societyId = ?1 AND u.areaTypeId =?2")
    List<UnitListVO> getSocietyIdAndAreaTypeId(int societyId, int areaTypeId, Pageable pageable);

    UnitMasterEntity findByAreaIdAndAreaTypeIdAndUnitAndFloor(int areaId, int areaTypeId, String unit, int floor);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.UnitMasterVO(u.id, u.unit, u.floor, u.isParking, u.isActive) FROM Unit u WHERE u.societyId = ?1 AND u.areaId = ?2 AND u.areaTypeId = ?3 AND u.unit != null")
    List<UnitMasterVO> getSocietyIdAreaId(int societyId, int areaId, int id, Pageable pageable);

    @Query("SELECT new com.pz.mysociety.model.VO.ComplainListVO(a.areaName, u.unit ) FROM Unit u join Area a ON u.areaId = a.id WHERE u.id = ?1")
    ComplainListVO getUnitDetails(int id);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.HelperListVO(a.areaName, t.type, u.unit) FROM Unit u JOIN Area a ON u.areaId = a.id JOIN AreaType t ON u.areaTypeId = t.id WHERE u.id = ?1")
    HelperListVO getUnitIdDetail(int unitId);

    UnitMasterEntity findByAreaId(int areaId);

    int countBySocietyIdAndAreaTypeIdAndIsActive(int societyId, int areaTypeEntityId, boolean isActive);

    int countBySocietyIdAndAreaIdAndAreaTypeIdAndIsActive(int societyId, int areaId, int areaTypeId, boolean isActive);

    int countBySocietyId(int societyId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UnitListVO(u.id, u.unit) FROM Unit u WHERE u.unit LIKE ?1% AND u.isActive = ?2 AND u.areaId = ?3 AND u.areaTypeId = ?4")
    List<UnitMasterVO> getSearchUnit(String unit, boolean isActive, int areaId, int areaTypeId, Pageable searchPageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UnitListVO(u.id, u.unit) FROM Unit u WHERE u.isActive = ?1 AND u.areaId = ?2 AND u.areaTypeId = ?3")
    List<UnitMasterVO> getActiveUnit(boolean isActive, int areaId, int areaTypeId, Pageable searchPageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UnitListVO(u.areaId, u.id, a.areaName, u.unit, u.floor) FROM Unit u JOIN Area a ON u.areaId = a.id WHERE u.societyId = ?1 AND u.areaTypeId = ?2 AND u.unit LIKE ?3% AND u.isActive = ?4")
    List<UnitListVO> getBuildingUnit(int societyId, int areaTypeId, String unit, boolean isActive, Pageable searchPageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UnitListVO(u.areaId, u.id, a.areaName, u.unit, u.floor) FROM Unit u JOIN Area a ON u.areaId = a.id WHERE u.societyId = ?1 AND u.areaTypeId = ?2 AND u.unit != null AND u.isActive = ?3")
    List<UnitListVO> getAreaTypeUnit(int societyId, int areaTypeId, boolean isActive, Pageable searchPageable);

    UnitMasterEntity findAreaIdBySocietyIdAndId(int SocietyId,int unitId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UserListVO(s.societyName, a.areaName, u.unit) FROM Unit u JOIN Area a ON u.areaId = a.id JOIN Society s ON u.societyId = s.id WHERE u.id = ?1")
    UserListVO getSocietyAndAreaName(int unitId);

    int countBySocietyIdAndAreaTypeId(int societyId, int id);

    int countBySocietyIdAndAreaIdAndAreaTypeId(int societyId, int areaId, int areaTypeId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UnitListVO(a.areaName, u.unit) FROM Unit u JOIN Area a ON u.areaId = a.id WHERE u.id = ?1")
    UnitListVO getAreaByUnitId(Integer unit);

    int countBySocietyIdAndAreaTypeIdAndUnitLikeAndIsActive(int societyId, int areaTypeId, String areaName, boolean isActive);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UnitListVO(u.areaId, u.id) FROM Unit u JOIN Area a ON u.areaId = a.id WHERE u.societyId = ?1 AND a.areaName = ?2 AND u.unit = ?3")
    UnitListVO getByAreaAndUnit(int societyId, String areaName, String unit);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UnitListVO(u.areaId, u.id) FROM Unit u JOIN Area a ON u.areaId = a.id WHERE u.societyId = ?1 AND a.areaName = ?2 AND u.areaTypeId != ?3")
    UnitListVO getByAreaAndType(int societyId, String areaName, int areaTypeId);

    UnitMasterEntity findByAreaIdAndUnit(int areaId, String unit);
}
