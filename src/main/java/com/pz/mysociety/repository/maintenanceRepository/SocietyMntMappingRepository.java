package com.pz.mysociety.repository.maintenanceRepository;

import com.pz.mysociety.entity.maintenanceEntity.SocietyMntMappingEntity;
import com.pz.mysociety.model.Request.maintenanceRequestVO.SocietyMntMappingVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SocietyMntMappingRepository extends JpaRepository<SocietyMntMappingEntity, Integer>, JpaSpecificationExecutor<SocietyMntMappingEntity> {
//    SocietyMntMappingEntity findByNameAndMntTypeId(String name, int mntTypeId);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.maintenanceRequestVO.SocietyMntMappingVO(m.name) FROM SocietyMntMapping sm JOIN MntMaster m ON sm.mntTypeId = m.id WHERE sm.societyId = ?1 AND sm.name = ?2")
    SocietyMntMappingVO getSocietyIdAndName(int societyId, String name);

    SocietyMntMappingEntity findBySocietyIdAndMntTypeId(int societyId, int mntTypeId);

    SocietyMntMappingEntity findBySocietyIdAndName(int societyId, String name);

    @Query(value = "SELECT sm.name FROM SocietyMntMapping sm JOIN MntMaster m ON sm.mntTypeId = m.id WHERE sm.societyId = ?1 AND m.name = ?2")
    String getMntName(int societyId, String unit);
}
