package com.pz.mysociety.repository.societyUtilRepository;

import com.pz.mysociety.entity.societyUtilEntity.LanguageTypeEntity;
import com.pz.mysociety.model.Request.societyUtilRequestVO.LanguageTypeVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageTypeRepository extends JpaRepository<LanguageTypeEntity, Integer>, JpaSpecificationExecutor<LanguageTypeEntity> {
    LanguageTypeEntity findByServiceIdAndType(int serviceId, String type);

    LanguageTypeEntity findByServiceId(int serviceId);
}
