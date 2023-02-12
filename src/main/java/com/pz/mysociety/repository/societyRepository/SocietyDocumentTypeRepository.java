package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.SocietyDocumentTypeEntity;
import com.pz.mysociety.model.Request.SocietyDocumentTypeVO;
import com.pz.mysociety.model.Request.TypeVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface SocietyDocumentTypeRepository extends JpaRepository<SocietyDocumentTypeEntity, Integer>, JpaSpecificationExecutor<SocietyDocumentTypeEntity> {

    List<SocietyDocumentTypeEntity> findByIsActive(boolean isActive, Pageable pageable);

    List<SocietyDocumentTypeEntity> findByIsMandatoryAndIsActive(boolean isMandatory, boolean isActive);

    int countByIsActive(boolean isActive);

    SocietyDocumentTypeEntity findByDocumentTypeId(int id);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.SocietyDocumentTypeVO(st.id, st.documentTypeId ,dm.type, st.isMandatory, st.isActive) FROM SocietyDocumentType st JOIN DocumentMaster dm ON st.documentTypeId = dm.id")
    List<SocietyDocumentTypeVO> getActiveDocumentType(Pageable pageable);
}
