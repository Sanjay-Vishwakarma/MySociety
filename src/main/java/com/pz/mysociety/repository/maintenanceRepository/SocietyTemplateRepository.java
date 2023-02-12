package com.pz.mysociety.repository.maintenanceRepository;

import com.pz.mysociety.entity.maintenanceEntity.SocietyTemplateEntity;
import com.pz.mysociety.model.Request.SocietyRequestVO.SocietyTemplateVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SocietyTemplateRepository extends JpaRepository<SocietyTemplateEntity, Integer> {

    @Query(value = "SELECT new com.pz.mysociety.model.Request.SocietyRequestVO.SocietyTemplateVO(t.id, t.societyHeader, t.termCondition, t.societyFooter) FROM SocietyTemplate t WHERE t.societyId = ?1")
    SocietyTemplateVO getSocietyTemplate(int societyId);

    SocietyTemplateEntity findBySocietyId(int societyId);
}
