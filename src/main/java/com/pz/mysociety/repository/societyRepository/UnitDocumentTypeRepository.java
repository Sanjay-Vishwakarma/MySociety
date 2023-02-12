package com.pz.mysociety.repository.societyRepository;


import com.pz.mysociety.entity.societyEntity.UnitDocumentTypeEntity;
import com.pz.mysociety.model.Request.UnitDocumentTypeVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UnitDocumentTypeRepository extends JpaRepository<UnitDocumentTypeEntity, Integer>, JpaSpecificationExecutor<UnitDocumentTypeEntity> {

    @Query(value = "SELECT new com.pz.mysociety.model.Request.UnitDocumentTypeVO(dm.type) FROM UnitDocumentType ud JOIN DocumentMaster dm ON ud.documentTypeId = dm.id WHERE ud.societyId = ?1 AND ud.residentType = ?2 AND ud.isMandatory = ?3 AND ud.isActive =  ?4")
    List<UnitDocumentTypeVO> getMandatoryList(int societyId, String residentType, boolean isMandatory, boolean isActive);

//    UnitDocumentTypeEntity findBySocietyIdAndResidentTypeAndDocumentType(int societyId, String residentType, String documentType);
//
//    List<UnitDocumentTypeEntity> findBySocietyIdAndResidentTypeAndIsActive(int societyId, String residentType, boolean isActive, Pageable pageable);
//
//    List<UnitDocumentTypeEntity> findBySocietyIdAndIsActive(int societyId, boolean isActive, Pageable pageable);

    int countBySocietyIdAndResidentTypeAndIsActive(int societyId, String residentType, boolean isActive);

    int countBySocietyIdAndIsActive(int societyId, boolean isActive);

    UnitDocumentTypeEntity findBySocietyIdAndResidentTypeAndDocumentTypeId(int societyId, String residentType, int documentTypeId);

//    @Query(value = "SELECT new com.pz.mysociety.model.Request.UnitDocumentTypeVO(ud.id, ud.residentType, dm.id, dm.type, ud.isMandatory, ud.isActive) FROM UnitDocumentType ud JOIN DocumentMaster dm ON ud.documentTypeId = dm.id WHERE ud.societyId = ?1 AND ud.residentType = ?2 AND ud.isActive = ?3")
//    List<UnitDocumentTypeVO> getResidentUnitDocumentType(int societyId, String residentType, boolean isActive, Pageable pageable);
//
//    @Query(value = "SELECT new com.pz.mysociety.model.Request.UnitDocumentTypeVO(ud.id, ud.residentType, dm.type, ud.isMandatory, ud.isActive) FROM UnitDocumentType ud JOIN DocumentMaster dm ON ud.documentTypeId = dm.id WHERE ud.societyId = ?1 AND ud.isActive = ?2")
//    List<UnitDocumentTypeVO> getSocietyUnitDocumentType(int societyId, boolean isActive, Pageable pageable);
}
