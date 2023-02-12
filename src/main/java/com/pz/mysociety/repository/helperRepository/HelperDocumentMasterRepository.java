package com.pz.mysociety.repository.helperRepository;

import com.pz.mysociety.entity.helperEntity.HelperDocumentMasterEntity;
import com.pz.mysociety.model.Request.HelperDocumentVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelperDocumentMasterRepository extends JpaRepository<HelperDocumentMasterEntity, Integer> {
  /*  @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE HelperDocMaster h SET h.id WHERE ch.complainId = ?2 AND ch.role = ?3")
    void updateDocument(int id, String document);*/

  HelperDocumentMasterEntity findBySocietyIdAndHelperIdAndDocumentTypeId(int societyId, int helperId, int documentTypeId);

//  HelperDocumentMasterEntity findBySocietyIdAndHelperId(int societyId, int helperId);

  List<HelperDocumentMasterEntity> findBySocietyIdAndHelperId(int SocietyId,int helperId);

  @Query(value = "SELECT new com.pz.mysociety.model.Request.HelperDocumentVO(hm.documentType, hm.document, hm.documentNumber) FROM HelperDocMaster hm WHERE hm.societyId = ?1 AND hm.helperId = ?2")
  List<HelperDocumentVO> getBySocietyIdAndHelperId(int societyId, int helperId);
}
