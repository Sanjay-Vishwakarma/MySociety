package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.SocietyDocumentEntity;
import com.pz.mysociety.model.Request.SocietyDocumentVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocietyDocumentRepository extends JpaRepository<SocietyDocumentEntity, Integer> {


//    List<SocietyDocumentEntity> findBySocietyId(int societyId);

//    SocietyDocumentEntity findBySocietyIdAndDocumentType(int societyId, String documentType);

//    List<SocietyDocumentEntity> findBySocietyUserId(int societyUserId);
//
//    @Query(value = "SELECT sd FROM SocietyDocument sd WHERE sd.societyId = ?1 AND sd.documentType = ?2 AND sd.documentStatus != ?3")
//    SocietyDocumentEntity getSocietyDocumentDetail(int societyId, String documentType, String documentStatus);

//    SocietyDocumentEntity findBySocietyIdAndDocumentTypeAndDocumentStatus(int societyId, String documentType, String documentStatus);

    SocietyDocumentEntity findBySocietyIdAndDocumentTypeIdAndDocumentStatus(int societyId, int documentTypeId, String documentStatus);

    @Query(value = "SELECT new com.pz.mysociety.entity.societyEntity.SocietyDocumentEntity(sd.societyId, sd.documentTypeId) FROM SocietyDocument sd WHERE sd.societyId = ?1 AND sd.documentTypeId = ?2")
    SocietyDocumentEntity findBySocietyIdAndDocumentTypeId(int societyId, int documentTypeId);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.SocietyDocumentVO(sd.id, dm.type, sd.documentStatus, sd.remark, sd.document) FROM SocietyDocument sd JOIN SocietyDocumentType st ON sd.documentTypeId = st.id JOIN DocumentMaster dm ON st.documentTypeId = dm.id WHERE sd.societyId = ?1")
    List<SocietyDocumentVO> getSocietyDocument(int societyId);
}
